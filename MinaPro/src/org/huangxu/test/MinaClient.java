package org.huangxu.test;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.huangxu.bean.User;
import org.huangxu.message.Message;



public class MinaClient {

	private static String clientAddr = "192.168.0.143" ;
	
	private static int port = 9999 ;
	
	public boolean connectServer(Message user){
		IoConnector conn = new NioSocketConnector();//建立连接
		conn.setConnectTimeout(30000);//设置连接超时
		conn.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		conn.setHandler(new Client01Handler());
		try{
		IoSession session = conn.connect(new InetSocketAddress(clientAddr, port)).awaitUninterruptibly().getSession(); 
			session.write(user).awaitUninterruptibly();  
		} catch (Exception e) {
			e.printStackTrace();
		}
//		conn.dispose();
		return false ;
	}
	public static void main(String[] args) {
		Message msg= new Message();
		User user = new User();
		user.setUid(123);
		user.setUsername("huangxu");
		user.setPassword("123456");
//		msg.setData(null);
//		msg.setUser(user);
		byte[] b = new byte[10];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte)i ;
		}
//		msg.setData(b);
		msg.setMsgType((byte)0x0000);
		new MinaClient().connectServer(msg);
	}
}
