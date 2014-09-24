package org.huangxu.messageprotocal;

/**
 * 消息协议—响应
 * 
 * @author robert
 * 
 */
public class JAbsMessageProtocalRes extends JAbsMessageProtocal {

	private short resultCode;// 结果码

	private String content; // 响应内容

	@Override
	public byte getTag() {
		return JConst.RES;
	}

	@Override
	public int getLength() {
		return 2 + (content == null ? 0 : content.getBytes().length);
	}

	public short getResultCode() {
		return resultCode;
	}

	public void setResultCode(short resultCode) {
		this.resultCode = resultCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "JAbsMessageProtocalRes [conent=" + content + ", resultCode="
				+ resultCode + ", getLength=" + getLength() + ", getTag="
				+ getTag() + "]";
	}
}
