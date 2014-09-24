package com.huangxu.mina;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

public class ClientKeepAliveMessageFactoryImpl implements KeepAliveMessageFactory {

	/**心跳包内容**/
	private static final String HEARTBEATREQ = "0x11";
	private static final String HEARTBAETRES = "0x12" ;

	private static final Logger logger = Logger.getLogger(ClientKeepAliveMessageFactoryImpl.class);
	@Override
	public Object getRequest(IoSession arg0) {
		return HEARTBEATREQ;
	}

	@Override
	public Object getResponse(IoSession arg0, Object arg1) {
		logger.info("返回预设信息: " + arg1);  
		return HEARTBAETRES;
	}

	@Override
	public boolean isRequest(IoSession arg0, Object message) {
        return false;  
	}

	@Override
	public boolean isResponse(IoSession arg0, Object message) {
		logger.info("返回预设信息: " + message);  
		 if (message.equals(HEARTBAETRES))  
	            return true;  
	        return false;  
	}

}
