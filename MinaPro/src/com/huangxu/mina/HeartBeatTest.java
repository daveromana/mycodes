package com.huangxu.mina;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;


public class HeartBeatTest {

	private static final int PORT = 9999;
	
	/**30s later timeout**/
	private static final int IDELETIMEOUT = 30 ; 
	
	/**心跳速率**/
	private static final int HEARTBEATRATE = 15 ;
	
	public static void main(String[] args) throws Exception {
		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getSessionConfig().setReadBufferSize(1024);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, IDELETIMEOUT);
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
		
//		KeepAliveMessageFactory keepAlive = new KeepAliveMessageFactoryImpl();
//		KeepAliveFilter heartBeat = new KeepAliveFilter(keepAlive,IdleStatus.BOTH_IDLE);
//		//设置是否forward到下一个filte
//		heartBeat.setForwardEvent(true);
//		//设置心跳速率
//		heartBeat.setRequestInterval(HEARTBEATRATE);
//		acceptor.getFilterChain().addLast("heartbeat", heartBeat);
		acceptor.setHandler(new HeartTestHandle());
		acceptor.bind(new InetSocketAddress(PORT));
		System.out.println("Server started on port： " + PORT);  
	}

}
