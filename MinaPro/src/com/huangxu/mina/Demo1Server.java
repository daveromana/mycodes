package com.huangxu.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.huangxu.messageprotocal.JMessageProtocalCodecFactory;

public class Demo1Server {

	private static Logger logger = Logger.getLogger(Demo1Server.class);
	
	private static int PORT = 3305 ;
	
	public static void main(String[] args) {
		IoAcceptor  acceptor = null ; //create connect 
		try {
			
		
		acceptor = new NioSocketAcceptor();//创建一个非阻塞的Socket端口
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new JMessageProtocalCodecFactory(Charset.forName("UTF-8"))));//添加消息过滤
		//设置读取缓冲区大小
		acceptor.getSessionConfig().setReadBufferSize(2048);
		//读取通道10秒没有反应则进入空闲
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.setHandler(new Demo1Handler());//绑定业务处理
		acceptor.bind(new InetSocketAddress(PORT));
		logger.info("server start success the port is "+PORT);
		} catch (Exception e) {
		logger.error("server start failed !",e);
		e.printStackTrace();
		} 
	}
}
