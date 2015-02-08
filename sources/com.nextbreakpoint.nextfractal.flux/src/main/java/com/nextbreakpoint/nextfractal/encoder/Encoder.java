package com.nextbreakpoint.nextfractal.encoder;

import java.io.File;

public interface Encoder {
	public void setDelegate(EncoderDelegate delegate);

	public void encode(EncoderContext context, File path) throws EncoderException;
}