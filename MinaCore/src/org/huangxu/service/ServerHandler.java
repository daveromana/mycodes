package org.huangxu.service;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.huangxu.message.Message;

public class ServerHandler implements IoHandler {

	Logger logger = Logger.getLogger(ServerHandler.class);
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.info("the session is created !");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.info("the session is opened !");

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		logger.info("the session is closed !");
		session.close(true);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.error("the handler is exception +"+cause.getMessage());
		session.close(true);

	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		Message msg = (Message)message ;
		msg.setSession(session);
		if(msg!=null){
			Server.getInstance().service.processDown(msg);	
			
		}
		
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		
	}

}
