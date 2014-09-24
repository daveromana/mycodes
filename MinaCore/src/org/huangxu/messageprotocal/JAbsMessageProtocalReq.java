package org.huangxu.messageprotocal;

/**
 * 消息协议-请求
 * 
 * @author robert
 * 
 */
public class JAbsMessageProtocalReq extends JAbsMessageProtocal {

	private short functionCode;// 功能码

	private String content; // 请求内容

	@Override
	public byte getTag() {

		return JConst.REQ;
	}

	@Override
	public int getLength() {
		return 2 + (content == null ? 0 : content.getBytes().length);
	}

	public short getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(short functionCode) {
		this.functionCode = functionCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "JAbsMessageProtocalReq[content=" + content + ", functionCode="
				+ functionCode + ", getLength=" + getLength() + ", getTag="
				+ getTag() + "]";
	}
}
