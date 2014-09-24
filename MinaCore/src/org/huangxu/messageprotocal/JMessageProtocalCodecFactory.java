package org.huangxu.messageprotocal;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
/**
 * 解码编码工厂
 * @author robert
 *
 */
public class JMessageProtocalCodecFactory implements ProtocolCodecFactory {

	private JMessageProtocalDecoder messageDecoder ;
	private JMessageProtocalEncoder messageEnCoder ;
	public JMessageProtocalCodecFactory(Charset charSet) {
		this.messageDecoder = new JMessageProtocalDecoder(charSet);
		this.messageEnCoder = new JMessageProtocalEncoder(charSet);;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return messageEnCoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return messageDecoder;
	}

}
