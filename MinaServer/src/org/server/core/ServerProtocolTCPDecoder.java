package org.server.core;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
/**
 * Server端TCP协议解码器
 * @see 用于解码接收到的TCP请求报文(报文编码一律采用UTF-8)
 * @create 2014-08-26
 * @author huangxu
 */
public class ServerProtocolTCPDecoder implements MessageDecoder {

	/**
	 * 该方法相当于预读取,用于判断是否是可用的解码器,这里对IoBuffer读取不会影响数据包的大小
	 * 该方法结束后IoBuffer会复原,所以不必担心调用该方法时,position已经不在缓冲区起始位置
	 */
	@Override
	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		//TCP报文格式约定为前6个字节表示报文整体长度,长度不足6位时左补零,第7位开始代表业务编码,业务编码固定长度为5,第12位开始是业务数据
		if(in.remaining()<6){
			return MessageDecoderResult.NEED_DATA;
		}
		//服务端启动时已绑定9901端口,专门用来处理TCP请求的
		if(session.getLocalAddress().toString().contains(":9901")){
			byte[] messageLength = new byte[6];
			in.get(messageLength);
			if(in.limit()>=Integer.parseInt(NetUtil.getString(messageLength, "UTF-8"))){
				return MessageDecoderResult.OK ;
			}else{
				return MessageDecoderResult.NEED_DATA;
			}
		}else{
			return MessageDecoderResult.NOT_OK;
		}
	}

	@Override
	public MessageDecoderResult decode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		byte[] message = new byte[in.limit()];
		in.get(message);
		Token token = new Token();
		String fullMessage = NetUtil.getString(message, "UTF-8");
		token.setBusiCharset("UTF-8");
		token.setBusiType(Token.BUSI_TYPE_TCP);
		token.setBusiCode(fullMessage.substring(6, 11));
		token.setFullMessage(fullMessage);
		token.setBusiMessage(fullMessage);
		out.write(token);
		return MessageDecoderResult.OK;
	}

	@Override
	public void finishDecode(IoSession session, ProtocolDecoderOutput out)
			throws Exception {

	}

}
