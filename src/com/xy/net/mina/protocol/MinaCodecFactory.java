package com.xy.net.mina.protocol;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class MinaCodecFactory implements ProtocolCodecFactory{
	private MinaEncoder encoder; 
	private MinaDecoder decoder; 

	MinaCodecFactory() { 
		this(Charset.defaultCharset()); 
	} 
	
	public MinaCodecFactory(Charset charSet) { 
		this.encoder= new MinaEncoder(charSet); 
		this.decoder= new MinaDecoder(charSet); 
	} 
	
	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws	Exception { 
		return decoder; 
	} 
	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws	Exception { 
		return encoder; 
	} 
}
