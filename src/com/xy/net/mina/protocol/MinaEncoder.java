package com.xy.net.mina.protocol;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.textline.LineDelimiter;

import com.xy.common.Tools;


public class MinaEncoder implements ProtocolEncoder{
	
	private final AttributeKey ENCODER = new AttributeKey(getClass(), "encoder");

	private final Charset charset;

	private int maxLineLength = Integer.MAX_VALUE;

	/**
	 * Creates a new instance with the spcified <tt>charset</tt>
	 * and {@link LineDelimiter#UNIX} delimiter.
	 */
	public MinaEncoder(Charset charset) {
		if (charset == null) {
			throw new IllegalArgumentException("charset");
		}
		this.charset = charset;
	}

	/**
	 * Creates a new instance with the spcified <tt>charset</tt>
	 * and the specified <tt>delimiter</tt>.
	 */
	public MinaEncoder(Charset charset, LineDelimiter delimiter) {
		if (charset == null) {
			throw new IllegalArgumentException("charset");
		}
		if (delimiter == null) {
			throw new IllegalArgumentException("delimiter");
		}
		if (LineDelimiter.AUTO.equals(delimiter)) {
			throw new IllegalArgumentException(
					"AUTO delimiter is not allowed for encoder.");
		}

		this.charset = charset;
	}

	/**
	 * Returns the allowed maximum size of the encoded line.
	 * If the size of the encoded line exceeds this value, the encoder
	 * will throw a {@link IllegalArgumentException}.  The default value
	 * is {@link Integer#MAX_VALUE}.
	 */
	public int getMaxLineLength() {
		return maxLineLength;
	}

	/**
	 * Sets the allowed maximum size of the encoded line.
	 * If the size of the encoded line exceeds this value, the encoder
	 * will throw a {@link IllegalArgumentException}.  The default value
	 * is {@link Integer#MAX_VALUE}.
	 */
	public void setMaxLineLength(int maxLineLength) {
		if (maxLineLength <= 0) {
			throw new IllegalArgumentException("maxLineLength: "
					+ maxLineLength);
		}

		this.maxLineLength = maxLineLength;
	}

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		CharsetEncoder encoder = (CharsetEncoder) session.getAttribute(ENCODER);

		if (encoder == null) {
			encoder = charset.newEncoder();
			session.setAttribute(ENCODER, encoder);
		}

		String value = (message == null ? "" : message.toString());
		IoBuffer buf = IoBuffer.allocate(value.length() + 2)
				.setAutoExpand(true);
		buf.putInt(Tools.getWordCount(message.toString()));
		System.out.println("ÏûÏ¢³¤-------------£º" + Tools.getWordCount(message.toString()));
		buf.putString(value, encoder);

		if (buf.position() > maxLineLength) {
			throw new IllegalArgumentException("Line length: " + buf.position());
		}

		//buf.putString(delimiter.getValue(), encoder);
		buf.flip();
		out.write(buf);
	}

	@Override
	public void dispose(IoSession arg0) throws Exception {
		
	}
}
