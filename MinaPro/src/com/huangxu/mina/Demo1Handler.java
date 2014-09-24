package com.huangxu.mina;

import java.net.SocketAddress;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.huangxu.bean.User;

public class Demo1Handler implements IoHandler {

	Logger logger = Logger.getLogger(Demo1Handler.class);
	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {
		logger.info("server is exception");
	}

	@Override
	public void messageReceived(IoSession seesion, Object message) throws Exception {
		logger.info("来自客户端的消息："+message.toString());
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
		logger.info("server send message is success");

	}

	@Override
	public void sessionClosed(IoSession arg0) throws Exception {
		logger.info("server connect is closed");

	}

	@Override
	public void sessionCreated(IoSession arg0) throws Exception {
		logger.info("server connecting  the client");
	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
		logger.info("server start nosometings");

	}

	@Override
	public void sessionOpened(IoSession arg0) throws Exception {
		logger.info("server to  the client is open ！");

	}

}
