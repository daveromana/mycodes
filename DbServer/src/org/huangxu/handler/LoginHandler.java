package org.huangxu.handler;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.huangxu.bean.User;
import org.huangxu.dao.UserDAO;
import org.huangxu.data.UserData;
import org.huangxu.message.Message;
import org.huangxu.opcode.OpCode;
import org.huangxu.opcode.UserTools;
import org.huangxu.utils.MD5Util;
/**
 * login
 * @author robert
 *
 */
public class LoginHandler implements Handler {

	@Override
	public void execute(IoSession session, Object obj){
		Message msg = (Message) obj;
		UserData user = (UserData) msg.getObject();
		String pass = MD5Util.string2MD5(user.getPassword());
		UserData data = UserDAO.getInstance().selectUserByNameAndPass(user.getUserName(),pass);
		if(data!=null){
			Message newMsg = new Message();
			newMsg.setMsgType(OpCode.S_LOGIN);
			newMsg.setObject(data);
			session.write(newMsg);
			UserTools.addUser(session.getId(), user);
		}else{
			Message nMsg = new Message();
			nMsg.setMsgType((short) 1);
			nMsg.setObject(user);
			session.write(nMsg);
		}
		}

}
