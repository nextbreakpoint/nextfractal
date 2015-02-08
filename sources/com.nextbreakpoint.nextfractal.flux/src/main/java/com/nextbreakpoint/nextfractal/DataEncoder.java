package com.nextbreakpoint.nextfractal;

import java.io.File;

import com.nextbreakpoint.nextfractal.encoder.Encoder;
import com.nextbreakpoint.nextfractal.encoder.EncoderContext;
import com.nextbreakpoint.nextfractal.encoder.EncoderDelegate;
import com.nextbreakpoint.nextfractal.encoder.EncoderException;

public class DataEncoder {
	private Encoder encoder;

	public DataEncoder(Encoder encoder) {
		this.encoder = encoder;
	}

	public void setDelegate(EncoderDelegate delegate) {
		encoder.setDelegate(delegate);
	}

	public void encode(EncoderContext context, File path) throws EncoderException {
		encoder.encode(context, path);
	}
}
