package org.huangxu.messageprotocal;

import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
/**
 * JAbsMessageProtocal解码
 * @author robert
 *
 */
public class JMessageProtocalDecoder extends ProtocolDecoderAdapter {

	private Logger logger = Logger.getLogger(JMessageProtocalDecoder.class);
	private Charset charSet ;
	
	public JMessageProtocalDecoder(Charset charSet) {
		this.charSet = charSet;
	}

	@Override
	public void decode(IoSession session, IoBuffer buffer, ProtocolDecoderOutput out)
			throws Exception {
		 JAbsMessageProtocal absMP=null; 
		 //协议tag
		 byte tag = buffer.get();
		 //消息长度
		 int length = buffer.getInt();
		 //取出消息体
		 byte[] bodyData = new byte[length];
		 buffer.get(bodyData);
		 // 检测协议  
		 IoBuffer tempBuf=IoBuffer.allocate(100).setAutoExpand(true);
		 tempBuf.put(tag);
		 tempBuf.putInt(length);
		 tempBuf.put(bodyData);
		 tempBuf.flip();
		 if(!canDecode(tempBuf)){
			 return ;
		 }
		 //协议体
		 IoBuffer bodyBuff = IoBuffer.allocate(100).setAutoExpand(true);
		 bodyBuff.put(bodyData);
		 bodyBuff.flip();
		 
		 //整个协议
		 IoBuffer allBuffer = IoBuffer.allocate(100).setAutoExpand(true);
		 allBuffer.put(tag);
		 allBuffer.putInt(length);
		 allBuffer.put(bodyData);
		 allBuffer.flip();
		 
		 if(tag==JConst.REQ){
			 JAbsMessageProtocalReq req = new JAbsMessageProtocalReq();
			 short functionCode = bodyBuff.getShort();
			 String content = bodyBuff.getString(charSet.newDecoder());
			 req.setFunctionCode(functionCode);
			 req.setContent(content);
			 absMP = req ;
		 }
		 if(tag==JConst.RES){
			 JAbsMessageProtocalRes res = new JAbsMessageProtocalRes();
			 short resultCode = bodyBuff.getShort();
			 String content = bodyBuff.getString(charSet.newDecoder());
			 res.setResultCode(resultCode);
			 res.setContent(content);
			 absMP = res ;
		 }
		 out.write(absMP);
	}

	private boolean canDecode(IoBuffer buf) {
		int protocalHeadLength=5;// 协议头长度  
		int remaining=buf.remaining();  
		if(remaining<protocalHeadLength){
			logger.error("错误，协议不完整，协议头长度小于 "+protocalHeadLength);
			return false ;
		}else{
			logger.debug("协议完整");
			byte tag = buf.get();
			if(tag==JConst.REQ||tag==JConst.RES){
			logger.debug("tag = "+tag);	
			}else{
				logger.error("错误，未定义协议类型");
				return false ;
			}
			int length = buf.getInt();
			if(buf.remaining()<length){
				logger.error("错误，真实协议长度小于协议头取的值");
				return false ;
			}else{
				logger.debug("真实协议长度 "+buf.remaining()+" = 消息头中取的值 "+length);
			}
			return true;
		}
		
	}

}
