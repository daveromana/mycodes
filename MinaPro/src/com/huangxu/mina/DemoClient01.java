package com.huangxu.mina;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.huangxu.bean.User;
import org.huangxu.message.Message;
import org.huangxu.messageprotocal.JAbsMessageProtocal;
import org.huangxu.messageprotocal.JAbsMessageProtocalReq;
import org.huangxu.messageprotocal.JMessageProtocalCodecFactory;

public class DemoClient01 {
	
	private static Logger logger = Logger.getLogger(DemoClient01.class);
	
	private static String clientAddr = "127.0.0.1" ;
	
	private static int port = 3305 ;
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		IoConnector conn = new NioSocketConnector();//建立连接
		conn.setConnectTimeout(30000);//设置连接超时
		conn.getFilterChain().addLast("codec", new ProtocolCodecFilter(new JMessageProtocalCodecFactory(Charset.forName("UTF-8"))));
//		KeepAliveMessageFactory keepAlive = new ClientKeepAliveMessageFactoryImpl();
//		KeepAliveFilter heartBeat = new KeepAliveFilter(keepAlive,IdleStatus.BOTH_IDLE);
//		//设置是否forward到下一个filte
//		heartBeat.setForwardEvent(true);
//		//设置心跳速率
//		heartBeat.setRequestInterval(15);
//		conn.getFilterChain().addLast("heartbeat", heartBeat);
		conn.setHandler(new Client01Handler());
		IoSession session = null ;
		try {
			ConnectFuture future = conn.connect(new InetSocketAddress(clientAddr,port));
			future.awaitUninterruptibly();//wait connect
			JAbsMessageProtocalReq req=new JAbsMessageProtocalReq();
			req.setFunctionCode((short)0);
			req.setContent("hahahahhaha");
			session = future.getSession();//get session
			session.write(req);
		} catch (Exception e) {
			logger.error("客户端异常");
			e.printStackTrace();
		}
		session.getCloseFuture().awaitUninterruptibly();//等待连接断开
		conn.dispose();
	}
}
