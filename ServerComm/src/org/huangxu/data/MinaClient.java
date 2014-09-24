
package org.huangxu.data;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.huangxu.bean.User;
import org.huangxu.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MinaClient {

	private static Logger logger = LoggerFactory.getLogger(MinaClient.class);
	public static IoSession connectServer(Message msg ,String ipAddress, int port, String charset){
		IoConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(1000);
		connector.getSessionConfig().setUseReadOperation(true); //同步的客户端,必须设置此项,其默认为false
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		IoSession session = null;
		try{
			ConnectFuture connectFuture = connector.connect(new InetSocketAddress(ipAddress, port));
			connectFuture.awaitUninterruptibly();          //等待连接成功,相当于将异步执行转为同步执行
			session = connectFuture.getSession(); //获取连接成功后的会话对象
			session.write(msg).awaitUninterruptibly(); //由于上面已经设置setUseReadOperation(true),故IoSession.read()方法才可用
		}catch(Exception e){
			logger.error("请求通信[/" + ipAddress + ":" + port + "]偶遇异常,堆栈轨迹如下", e);
		}
			return session ;	
	}

}
