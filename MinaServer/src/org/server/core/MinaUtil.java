package org.server.core;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用Mina2.x发送报文的工具类
 * @see 详细说明请参阅我的博客http://blog.csdn.net/jadyer/article/details/8088068
 * @version v1.5
 * @history v1.1-->编码器和解码器中的字符处理,升级为Mina2.x提供的<code>putString()</code>方法来处理
 * @history v1.2-->解码器采用CumulativeProtocolDecoder实现,以适应应答报文被拆分多次后发送Client的情况
 * @history v1.3-->修复BUG:请求报文有误时,Server可能返回非约定报文,此时会抛java.lang.NumberFormatException
 * @history v1.4-->增加全局异常捕获
 * @history v1.5-->由于本工具类的作用是同步的客户端,故取消IoHandler设置,但注意必须setUseReadOperation(true)
 * @update Jul 27, 2013 10:21:01 AM
 * @create Oct 3, 2012 12:42:21 PM
 */
public class MinaUtil {
	private static Logger logger = LoggerFactory.getLogger(MinaUtil.class);
	/**心跳速率**/
	private static final int HEARTBEATRATE = 15 ;
	private MinaUtil(){}
	
	/**
	 * 发送TCP消息
	 * @see 当通信发生异常时,如Fail to get session....返回<code>"MINA_SERVER_ERROR"</code>字符串
	 * @param message   待发送报文的中文字符串形式
	 * @param ipAddress 远程主机的IP地址
	 * @param port      远程主机的端口号
	 * @param charset   该方法与远程主机间通信报文为编码字符集(编码为byte[]发送到Server)
	 * @return 远程主机响应报文的字符串形式
	 */
	public static Object sendTCPMessage(String message, String ipAddress, int port, String charset){
		IoConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(1000);
		connector.getSessionConfig().setUseReadOperation(true); //同步的客户端,必须设置此项,其默认为false
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ClientProtocolEncoder(charset), new ClientProtocolDecode(charset)));
		//connector.setHandler(this); //作为同步的客户端,可以不需要IoHandler,Mina会自动添加一个默认的IoHandler实现,即AbstractIoConnector
		IoSession session = null;
		Object respData = null;
		try{
			ConnectFuture connectFuture = connector.connect(new InetSocketAddress(ipAddress, port));
			connectFuture.awaitUninterruptibly(); //等待连接成功,相当于将异步执行转为同步执行
			session = connectFuture.getSession();          //获取连接成功后的会话对象
			session.write(message).awaitUninterruptibly(); //由于上面已经设置setUseReadOperation(true),故IoSession.read()方法才可用
			ReadFuture readFuture = session.read();        //因其内部使用BlockingQueue,故Server端用之可能会内存泄漏,但Client端可适当用之
			if(readFuture.awaitUninterruptibly(90, TimeUnit.SECONDS)){ //Wait until the message is received
				respData = readFuture.getMessage();                    //Get the received message
			}else{
				logger.info("读取[/" + ipAddress + ":" + port + "]超时");
			}
		}catch(Exception e){
			logger.error("请求通信[/" + ipAddress + ":" + port + "]偶遇异常,堆栈轨迹如下", e);
		}
		return respData==null ? "MINA_SERVER_ERROR" : respData;
	}
	
	
	/**
	 * 客户端编码器
	 * @see 将Client的报文编码后发送到Server
	 */
	private static class ClientProtocolEncoder extends ProtocolEncoderAdapter {
		private final String charset;
		public ClientProtocolEncoder(String charset){
			this.charset = charset;
		}
		@Override
		public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
			IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);
			//二者等效--><code>buffer.put(message.toString().getBytes(charset))</code>
			buffer.putString(message.toString(), Charset.forName(charset).newEncoder());
			buffer.flip();
			out.write(buffer);
		}
	}
	
	
	/**
	 * 客户端解码器
	 * @see 解码Server的响应报文给Client
	 * @see 样例报文[000064100030010000120121101210419100000000000028`18622233125`10`]
	 */
	private static class ClientProtocolDecode extends CumulativeProtocolDecoder {
		private final String charset;
		//注意这里使用了Mina自带的AttributeKey类来定义保存在IoSession中对象的键值,其可有效防止键值重复
		//通过查询AttributeKey类源码发现,它的构造方法采用的是"类名+键名+AttributeKey的hashCode"的方式
		private final AttributeKey CONTEXT = new AttributeKey(getClass(), "context");
		public ClientProtocolDecode(String charset){
			this.charset = charset;
		}
		private Context getContext(IoSession session){
			Context context = (Context)session.getAttribute(CONTEXT);
			if(null == context){
				context = new Context();
				session.setAttribute(CONTEXT, context);
			}
			return context;
		}
		@Override
		protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
			Context ctx = this.getContext(session);
			IoBuffer buffer = ctx.innerBuffer;
			int messageCount = ctx.getMessageCount();
			while(in.hasRemaining()){    //判断position和limit之间是否有元素
				buffer.put(in.get());    //get()读取buffer的position的字节,然后position+1
				if(messageCount++ == 5){ //约定:报文的前6个字符串表示报文总长度,不足6位则左侧补0
					buffer.flip();       //Set limit=position and position=0 and mark=-1
					//当Server的响应报文中含0x00时,Mina2.x的buffer.getString(fieldSize, decoder)方法会break
					//该方法的处理细节,详见org.apache.mina.core.buffer.AbstractIoBuffer类的第1718行源码,其说明如下
					//Reads a NUL-terminated string from this buffer using the specified decoder and returns it
					//ctx.setMessageLength(Integer.parseInt(buffer.getString(6, decoder)));
					byte[] messageLength = new byte[6];
					buffer.get(messageLength);
					try{
						//请求报文有误时,Server可能返回非约定报文,此时会抛java.lang.NumberFormatException
						ctx.setMessageLength(Integer.parseInt(new String(messageLength, charset)));
					}catch(NumberFormatException e){
						ctx.setMessageLength(in.limit());
					}
					buffer.limit(in.limit()); //让两个IoBuffer的limit相等
				}
			}
			ctx.setMessageCount(messageCount);
			if(ctx.getMessageLength() == buffer.position()){
				buffer.flip();
				byte[] message = new byte[buffer.limit()];
				buffer.get(message);
				out.write(message);
				ctx.reset();
				return true;
			}else{
				return false;
			}
		}
		private class Context{
			private final IoBuffer innerBuffer; //用于累积数据的IoBuffer
			private int messageCount;           //记录已读取的报文字节数
			private int messageLength;          //记录已读取的报文头标识的报文长度
			public Context(){
				innerBuffer = IoBuffer.allocate(100).setAutoExpand(true);
			}
			public int getMessageCount() {
				return messageCount;
			}
			public void setMessageCount(int messageCount) {
				this.messageCount = messageCount;
			}
			public int getMessageLength() {
				return messageLength;
			}
			public void setMessageLength(int messageLength) {
				this.messageLength = messageLength;
			}
			public void reset(){
				this.innerBuffer.clear(); //Set limit=capacity and position=0 and mark=-1
				this.messageCount = 0;
				this.messageLength = 0;
			}
		}
	}
}
