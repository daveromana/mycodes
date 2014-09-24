package org.server.core;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHandler extends IoHandlerAdapter {
	private static Logger logger = LoggerFactory.getLogger(ServerHandler.class);
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String respData = null;
		Token token = (Token)message;
		/**
		 * 打印收到的原始报文
		 **/
		logger.info("渠道:" + token.getBusiType() + "  交易码:" + token.getBusiCode() +"  完整报文(HEX):"
						   + NetUtil.buildHexStringWithASCII(NetUtil.getBytes(token.getFullMessage(), "UTF-8")));
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n------------------------------------------------------------------------------------------");
		sb.append("\r\n【通信双方】").append(session);
		sb.append("\r\n【收发标识】Receive");
		sb.append("\r\n【报文内容】").append(token.getFullMessage());
		sb.append("\r\n------------------------------------------------------------------------------------------");
		logger.info(sb.toString());
		/*
		 * TCP请求
		 */
		if(token.getBusiType().equals("TCP")){
			if(token.getBusiCode().equals("0x001")){
				logger.info("收到请求参数=[" + token.getBusiMessage() + "]");
				respData = "0x001";
			}
			if(token.getBusiCode().equals("10005")){
				logger.info("收到请求参数=[" + token.getBusiMessage() + "]");
				respData = "你好o！";
			}
		}
		/*
		 * http请求
		 */
		if(token.getBusiType().equals("HTTP")){
			logger.info("收到请求参数=[" + token.getBusiMessage() + "]");
			respData = this.buildHTTPResponseMessage("登录成功");
		}
		/*
		 * 打印应答报文
		 */
		sb.setLength(0);
		sb.append("\r\n------------------------------------------------------------------------------------------");
		sb.append("\r\n【通信双方】").append(session);
		sb.append("\r\n【收发标识】Response");
		sb.append("\r\n【报文内容】").append(respData);
		sb.append("\r\n------------------------------------------------------------------------------------------");
		logger.info(sb.toString());
		session.write(respData);
		SocketSessionConfig ss = (SocketSessionConfig) session.getConfig();
		ss.setKeepAlive(true);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		logger.info("已回应给Client");
		
	}
	@Override
	public void sessionIdle(IoSession session, IdleStatus status){
		logger.info("请求进入闲置状态....回路即将关闭....");
		session.close(true);
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause){
		logger.info("请求处理遇到异常....回路即将关闭....");
		cause.printStackTrace();
		session.close(true);
	}
	
	/**
	 * 构建HTTP响应报文
	 * @see 该方法默认构建的是HTTP响应码为200的响应报文
	 * @param httpResponseMessageBody HTTP响应报文体
	 * @return 包含了HTTP响应报文头和报文体的完整报文
	 */
	private String buildHTTPResponseMessage(String httpResponseMessageBody){
		return buildHTTPResponseMessage(HttpURLConnection.HTTP_OK, httpResponseMessageBody);
	}
	
	/**
	 * 构建HTTP响应报文
	 * @see 200--请求已成功,请求所希望的响应头或数据体将随此响应返回..即服务器已成功处理了请求
	 * @see 400--由于包含语法错误,当前请求无法被服务器理解..除非进行修改,否则客户端不应该重复提交这个请求..即错误请求
	 * @see 500--服务器遇到了一个未曾预料的状况,导致其无法完成对请求的处理..一般来说,该问题都会在服务器的程序码出错时出现..即服务器内部错误
	 * @see 501--服务器不支持当前请求所需要的某个功能..当服务器无法识别请求的方法,且无法支持其对任何资源的请求时,可能返回此代码..即尚未实施
	 * @param httpResponseCode        HTTP响应码
	 * @param httpResponseMessageBody HTTP响应报文体
	 * @return 包含了HTTP响应报文头和报文体的完整报文
	 */
	private String buildHTTPResponseMessage(int httpResponseCode, String httpResponseMessageBody){
		if(httpResponseCode == HttpURLConnection.HTTP_OK){
			StringBuilder sb = new StringBuilder();
			sb.append("HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\nContent-Length: ");
			sb.append(NetUtil.getBytes(httpResponseMessageBody, "UTF-8").length);
			sb.append("\r\n\r\n");
			sb.append(httpResponseMessageBody);
			return sb.toString();
		}
		if(httpResponseCode == HttpURLConnection.HTTP_BAD_REQUEST){
			return "HTTP/1.1 400 Bad Request";
		}
		if(httpResponseCode == HttpURLConnection.HTTP_INTERNAL_ERROR){
			return "HTTP/1.1 500 Internal Server Error";
		}
		return "HTTP/1.1 501 Not Implemented";
	}
}
