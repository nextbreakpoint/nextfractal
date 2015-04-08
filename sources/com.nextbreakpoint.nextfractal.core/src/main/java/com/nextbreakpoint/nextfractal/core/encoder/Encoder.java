package com.nextbreakpoint.nextfractal.core.encoder;

import java.io.File;

public interface Encoder {
	/**
	 * @return
	 */
	public String getId();
	
	/**
	 * @param delegate
	 */
	public void setDelegate(EncoderDelegate delegate);

	/**
	 * @param context
	 * @param path
	 * @throws EncoderException
	 */
	public void encode(EncoderContext context, File path) throws EncoderException;

	/**
	 * @return
	 */
	public String getSuffix();
}