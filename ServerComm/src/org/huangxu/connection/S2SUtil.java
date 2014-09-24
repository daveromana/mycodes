
package org.huangxu.connection;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.huangxu.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class S2SUtil {

	private static Logger logger = LoggerFactory.getLogger(S2SUtil.class);
	
	public static Message connectServer(Message msg ,String ipAddress, int port, String charset){
		IoConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(1000);
		connector.getSessionConfig().setUseReadOperation(true); //同步的客户端,必须设置此项,其默认为false
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
//		conn.setHandler(new ClientHandler());
		IoSession session = null;
		Object respData = null;
		try{
			ConnectFuture connectFuture = connector.connect(new InetSocketAddress(ipAddress, port));
			connectFuture.awaitUninterruptibly();          //等待连接成功,相当于将异步执行转为同步执行
			session = connectFuture.getSession(); //获取连接成功后的会话对象
			session.write(msg).awaitUninterruptibly(); //由于上面已经设置setUseReadOperation(true),故IoSession.read()方法才可用
			ReadFuture readFuture = session.read();        //因其内部使用BlockingQueue,故Server端用之可能会内存泄漏,但Client端可适当用之
			if(readFuture.awaitUninterruptibly(90, TimeUnit.SECONDS)){ //Wait until the message is received
				respData = readFuture.getMessage();                    //Get the received message
			}else{
				logger.info("读取[/" + ipAddress + ":" + port + "]超时");
			}
		}catch(Exception e){
			logger.error("请求通信[/" + ipAddress + ":" + port + "]偶遇异常,堆栈轨迹如下", e);
		}finally{
			if(session != null){
				//关闭IoSession,该操作是异步的,true为立即关闭,false为所有写操作都flush后关闭
				//这里仅仅是关闭了TCP的连接通道,并未关闭Client端程序
				session.close(true);
				//客户端发起连接时,会请求系统分配相关的文件句柄,而在连接失败时记得释放资源,否则会造成文件句柄泄露
				//当总的文件句柄数超过系统设置值时[ulimit -n],则抛异常"java.io.IOException: Too many open files",导致新连接无法创建,服务器挂掉
				//所以,若不关闭的话,其运行一段时间后可能抛出too many open files异常,导致无法连接
				session.getService().dispose();
			}
		}
		return (Message)respData ;
	}
	
}
