package com.huangxu.mina;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.huangxu.bean.User;
import org.huangxu.message.Message;

public class Client01Handler implements IoHandler {
	
	Logger logger = Logger.getLogger(Client01Handler.class);
	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {
		logger.info("客户端异常");

	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String msg = message.toString();
		logger.info("客户端收到消息为:"+msg);
	}

	@Override
	public void messageSent(IoSession session, Object msg) throws Exception {
		logger.info("客户端发送消息为:"+msg.toString());
	}

	@Override
	public void sessionClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionCreated(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionOpened(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
