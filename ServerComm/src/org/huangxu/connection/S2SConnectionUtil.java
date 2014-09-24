package org.huangxu.connection;

import org.huangxu.message.Message;
/**
 * 内部服务器通信
 * @author robert
 *
 */
public class S2SConnectionUtil {

	/**
	 * Login到db的连接
	 * @return Message
	 */
	public Message L2DConnection(){
		return null ;
	}
	/**
	 * db到Login的连接
	 * @return Message
	 */
	public Message D2LConnection(){
		return null ;
	}
	/**
	 * game到DB的连接
	 * @return Message
	 */
	public Message G2DConnection(){
		return null ;
	}
	/**
	 * db到game的连接
	 * @return Message
	 */
	public Message D2GConnection(){
		return null ;
	}
	/**
	 * login到game的连接
	 * @return Message
	 */
	public Message L2GConnection(){
		return null ;
	}
	/**
	 * game到Login的连接
	 * @return Message
	 */
	public Message G2LConnection(){
		return null ;
	}
}
