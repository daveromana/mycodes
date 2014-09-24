package org.huangxu.handler;

import org.apache.mina.core.session.IoSession;
import org.huangxu.dao.UserDAO;
import org.huangxu.data.UserData;
import org.huangxu.message.Message;
import org.huangxu.opcode.OpCode;
import org.huangxu.utils.MD5Util;
import org.huangxu.utils.NetUtil;

public class RegistHandler implements Handler {

	@Override
	public void execute(IoSession session, Object obj) {
		Message msg = (Message) obj;
		UserData user = (UserData) msg.getObject();
		UserData data = UserDAO.getInstance().selectUserByName(user.getUserName());
//		
		if(data!=null){
			Message nMsg = new Message();
			nMsg.setMsgType((short) 1);
			nMsg.setObject(user);
			session.write(nMsg);	
		}
		if(data==null){
			String pass = MD5Util.string2MD5(user.getPassword());
			int m = UserDAO.getInstance().registUser(user.getUid(), user.getUserName(), pass);
			if(m>0){
				Message nMsg = new Message();
				nMsg.setMsgType(OpCode.S_REGIST);
				nMsg.setObject(user);
				session.write(nMsg);	
			}else{
				Message nMsg = new Message();
				nMsg.setMsgType((short) 2);
				nMsg.setObject(user);
				session.write(nMsg);	
			}
		}
	}

}
