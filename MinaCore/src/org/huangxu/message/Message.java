package org.huangxu.message;

import java.io.Serializable;

import org.apache.mina.core.session.IoSession;

public class Message implements Serializable {

	private static final long serialVersionUID = -2961278733906981416L;
	
	private short msgType = 0;
	
	private Object object ;
	
	private IoSession session ;

	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}

	public short getMsgType() {
		return msgType;
	}

	public void setMsgType(short msgType) {
		this.msgType = msgType;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	
	
}
