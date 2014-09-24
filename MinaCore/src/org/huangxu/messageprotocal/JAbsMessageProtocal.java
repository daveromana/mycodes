package org.huangxu.messageprotocal;
/**
 * 消息协议
 * @author robert
 *
 */
public abstract class JAbsMessageProtocal {

	public abstract byte getTag();// 消息协议类型  请求/响应  
	
	public abstract int getLength();// 消息协议数据长度  

}
/** 
 * 报头： 
 *    short tag：请求/响应 
 *    int   length：数据长度 
 * 报体： 
 *    short   methodCode：功能函数 
 *    byte    resultCode：结果码 
 *    String  content：数据内容 
 */  

