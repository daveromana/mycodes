package org.huangxu.test;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.huangxu.bean.User;

public class Client01Handler implements IoHandler {

	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {
		System.out.println(arg1);
	}

	@Override
	public void messageReceived(IoSession seesion, Object message) throws Exception {
		Object o = message ;
		
		if(Integer.parseInt(o.toString())==1){
			seesion.close(true);
		}
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
		

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
