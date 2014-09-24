package org.server.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.huangxu.bean.User;
import org.huangxu.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 服务启动类
 * @see 这里并没有配置backlog,那么它会采用操作系统默认的连接请求队列长度50
 * @see 详见org.apache.mina.core.polling.AbstractPollingIoAcceptor类源码的96行
 * @create 2014-08-26 14:28:04 
 * @author huangxu
 */
public class MainApp {

	private static Logger logger = LoggerFactory.getLogger(MainApp.class); 
	public static void main(String[] args) {
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		acceptor.setBacklog(0);
//		acceptor.setReuseAddress(true);
		acceptor.getSessionConfig().setWriterIdleTime(1000);
		acceptor.getSessionConfig().setBothIdleTime(90);
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ServerProtocolCodecFactory()));
		acceptor.getFilterChain().addLast("executor", new ExecutorFilter());
		acceptor.setHandler(new ServerHandler());
		//服务端绑定两个端口,8000用于接收并处理HTTP请求,9901用于接收并处理TCP请求
		List<SocketAddress> socketAddresses = new ArrayList<SocketAddress>();
		socketAddresses.add(new InetSocketAddress(8000));
		socketAddresses.add(new InetSocketAddress(9901));
		try {
			acceptor.bind(socketAddresses);
		} catch (IOException e) {
			logger.error("server start error:"+e.getMessage());
			e.printStackTrace();
		}
		//判断服务端启动与否
		if(acceptor.isActive()){
			logger.info("写 超 时: "+acceptor.getSessionConfig().getWriterIdleTime()+" ms");
			logger.info("发呆配置(Both Idle):"+acceptor.getSessionConfig().getBothIdleTime()+" ms");
//			logger.info("端口重用: true"+acceptor.getSessionConfig().getRe);
			logger.info("服务端初始化完成......");
			logger.info("服务已启动....开始监听...." + acceptor.getLocalAddresses());
		}else{
//			Thread.currentThread().notifyAll();
			logger.error("服务端初始化失败......");
			
		}
	}
}
