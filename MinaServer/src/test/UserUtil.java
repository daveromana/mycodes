package test;

import java.util.concurrent.TimeUnit;

import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.session.IoSession;
import org.huangxu.data.UserData;
import org.huangxu.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.UserDataHandler;
/**
 * 用户操作类
 * @author robert
 *
 */
public class UserUtil {
	static int  i = 0 ;
	public static Logger logger  = LoggerFactory.getLogger(UserUtil.class);
	/**
	 * 用户登陆
	 * @param context
	 * @param username 用户名
	 * @param password 密码
	 */
	public static void login(String username,String password){
	
		Message message = new Message();
		UserData user = new UserData();
		user.setUserName(username);
		user.setPassword(password);
		message.setObject(user);
		message.setMsgType(OpCode.C_LOGIN);
		Message msg = new Message();
		Object obj =null ;
		IoSession session = MinaClient.connectServer(message, "192.168.0.143", 6602, "UTF-8");
		if(session!=null){
			ReadFuture readFuture = session.read();        //因其内部使用BlockingQueue,故Server端用之可能会内存泄漏,但Client端可适当用之
			if(readFuture.awaitUninterruptibly(90, TimeUnit.SECONDS)){ //Wait until the message is received
				msg =   (Message) readFuture.getMessage();                    //Get the received message
			}
				UserData u = (UserData) msg.getObject();
				if(u!=null&&msg.getMsgType()==OpCode.C_LOGIN){
					logger.info("目前成功数："+i);
					i ++ ;
				}else if(msg.getMsgType()==1){
				}	
			
		}else{
			logger.info("服务器连接失败");
		}
	}
}
