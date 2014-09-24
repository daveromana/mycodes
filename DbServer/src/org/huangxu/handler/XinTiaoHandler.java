package org.huangxu.handler;

import org.apache.mina.core.session.IoSession;
import org.huangxu.message.Message;
import org.huangxu.opcode.OpCode;
import org.huangxu.opcode.UserTools;

public class XinTiaoHandler implements Handler {

	@Override
	public void execute(IoSession session, Object obj) {

		Message msg = new Message();
		msg.setObject(UserTools.userMap.get(session.getId()));
		msg.setMsgType(OpCode.S_XINTIAO);
		session.write(msg);
	}

}
