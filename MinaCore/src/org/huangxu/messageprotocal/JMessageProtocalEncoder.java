package org.huangxu.messageprotocal;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
/**
 * JAbsMessageProtocal编码
 * @author robert
 *
 */
public class JMessageProtocalEncoder extends ProtocolEncoderAdapter {

	private Charset charSet ;
	
	public JMessageProtocalEncoder(Charset charSet) {
		this.charSet = charSet;
	}


	@Override
	public void encode(IoSession session, Object object,
			ProtocolEncoderOutput out) throws Exception {
		IoBuffer buff = IoBuffer.allocate(2048).setAutoExpand(true);
		// object --> AbsMP  
		JAbsMessageProtocal absMp=(JAbsMessageProtocal)object;
		buff.put(absMp.getTag());
		buff.putInt(absMp.getLength());
		if(object instanceof JAbsMessageProtocalReq){//请求
			JAbsMessageProtocalReq req =(JAbsMessageProtocalReq) object;
			buff.putShort(req.getFunctionCode());
			buff.putString(req.getContent(), charSet.newEncoder());
		}else if(object instanceof JAbsMessageProtocalRes){//响应
			JAbsMessageProtocalRes res = (JAbsMessageProtocalRes) object;
			buff.putShort(res.getResultCode());
			buff.putString(res.getContent(), charSet.newEncoder());
			}
		buff.flip();
		out.write(buff);
	}
}
