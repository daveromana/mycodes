package org.huangxu.handler;

import org.apache.mina.core.session.IoSession;

public abstract interface Handler {

	 public abstract void execute(IoSession session,Object obj);
}
