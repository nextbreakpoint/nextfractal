/*
 * NextFractal 1.1.2
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.runtime.encoder;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.core.encoder.Encoder;
import com.nextbreakpoint.nextfractal.core.encoder.EncoderContext;
import com.nextbreakpoint.nextfractal.core.encoder.EncoderDelegate;
import com.nextbreakpoint.nextfractal.core.encoder.EncoderException;

import net.sf.ffmpeg4java.AVCodec;
import net.sf.ffmpeg4java.AVCodecContext;
import net.sf.ffmpeg4java.AVFormatContext;
import net.sf.ffmpeg4java.AVFrame;
import net.sf.ffmpeg4java.AVMediaType;
import net.sf.ffmpeg4java.AVOutputFormat;
import net.sf.ffmpeg4java.AVPacket;
import net.sf.ffmpeg4java.AVRational;
import net.sf.ffmpeg4java.AVStream;
import net.sf.ffmpeg4java.CodecID;
import net.sf.ffmpeg4java.FFmpeg4Java;
import net.sf.ffmpeg4java.FFmpeg4JavaConstants;
import net.sf.ffmpeg4java.PixelFormat;
import net.sf.ffmpeg4java.SWIGTYPE_p_SwsContext;
import net.sf.ffmpeg4java.SWIGTYPE_p_uint8_t;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractVideoEncoder implements Encoder {
	private static final Logger logger = Logger.getLogger(AbstractVideoEncoder.class.getName());
	private static final int PKT_BIT_BUFFER_SIZE = 200000;
	private EncoderDelegate delegate;

	static {
		FFmpeg4Java.avcodec_register_all();
		FFmpeg4Java.av_register_all();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.encoder.Encoder#setDelegate(com.nextbreakpoint.nextfractal.core.encoder.EncoderDelegate)
	 */
	public void setDelegate(final EncoderDelegate delegate) {
		this.delegate = delegate;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.encoder.Encoder#encode(com.nextbreakpoint.nextfractal.core.encoder.EncoderContext, java.io.File)
	 */
	public void encode(final EncoderContext context, final File path) throws EncoderException {
		if (delegate != null) {
			delegate.didProgressChanged(0f);
		}
		AVFormatContext format_context = null;
		AVOutputFormat output_format = null;
		AVCodecContext codec_context = null;
		AVRational time_base = null;
		AVStream stream = null;
		AVCodec codec = null;
		AVFrame rgb_frame = null;
		AVFrame yuv_frame = null;
		SWIGTYPE_p_uint8_t pkt_bit_buffer = null;
		SWIGTYPE_p_uint8_t rgb_bit_buffer = null;
		SWIGTYPE_p_uint8_t yuv_bit_buffer = null;
		SWIGTYPE_p_SwsContext sws_context = null;
		try {
			if (AbstractVideoEncoder.logger.isLoggable(Level.FINE)) {
				AbstractVideoEncoder.logger.fine("Starting encoding...");
			}
			long time = System.currentTimeMillis();
			int fps = context.getFrameRate();
			int frame_count = context.getFrameCount();
			int frame_width = context.getImageWidth();
			int frame_height = context.getImageHeight();
			format_context = FFmpeg4Java.avformat_alloc_context();
			if (format_context != null) {
				output_format = FFmpeg4Java.av_guess_format(getFormatName(), null, null);
				if (output_format != null && !output_format.getVideo_codec().equals(CodecID.CODEC_ID_NONE)) {
					if (AbstractVideoEncoder.logger.isLoggable(Level.FINE)) {
						AbstractVideoEncoder.logger.fine("Format is " + output_format.getLong_name());
					}
					format_context.setOformat(output_format);
					time_base = new AVRational();
					time_base.setNum(1);
					time_base.setDen(fps);
					stream = FFmpeg4Java.av_new_stream(format_context, 0);
					if (stream != null && format_context.getNb_streams() == 1) {
						codec_context = stream.getCodec();
						FFmpeg4Java.avcodec_get_context_defaults2(codec_context, AVMediaType.AVMEDIA_TYPE_VIDEO);
						codec_context.setCodec_id(output_format.getVideo_codec());
						codec_context.setCodec_type(AVMediaType.AVMEDIA_TYPE_VIDEO);
						codec_context.setPix_fmt(PixelFormat.PIX_FMT_YUV420P);
						codec_context.setTime_base(time_base);
						codec_context.setBit_rate(400000);
						codec_context.setGop_size(12);
						codec_context.setWidth(frame_width);
						codec_context.setHeight(frame_height);
//							codec_context.setCompression_level(-2);
						codec_context.setB_quant_factor(0.1f);
						codec_context.setI_quant_factor(0.1f);
						if (codec_context.getCodec_id() == CodecID.CODEC_ID_MPEG2VIDEO) {
							codec_context.setMax_b_frames(2);
						}
						if (codec_context.getCodec_id() == CodecID.CODEC_ID_MPEG1VIDEO) {
							codec_context.setMb_decision(2);
						}
						if (codec_context.getCodec_id() == CodecID.CODEC_ID_H264) {
							codec_context.setB_frame_strategy(1);
							codec_context.setQmax(69);
							codec_context.setQmin(0);
							codec_context.setQcompress(0.6f);
							codec_context.setMax_qdiff(4);
							codec_context.setRefs(3);
							codec_context.setMe_cmp(FFmpeg4JavaConstants.FF_CMP_CHROMA);
							codec_context.setMe_range(16);
							codec_context.setMe_subpel_quality(7);
//								codec_context.setMe_method();
							codec_context.setTrellis(1);
							codec_context.setKeyint_min(25);
							codec_context.setDirectpred(1);
							codec_context.setWeighted_p_pred(2);
							codec_context.setGop_size(250);
							codec_context.setCoder_type(1);
							codec_context.setScenechange_threshold(40);
							codec_context.setThread_count(0);
							codec_context.setPartitions(FFmpeg4JavaConstants.X264_PART_I4X4 | FFmpeg4JavaConstants.X264_PART_I8X8 | FFmpeg4JavaConstants.X264_PART_P8X8 | FFmpeg4JavaConstants.X264_PART_B8X8);
							codec_context.setFlags2(FFmpeg4JavaConstants.CODEC_FLAG2_8X8DCT | FFmpeg4JavaConstants.CODEC_FLAG2_BPYRAMID | FFmpeg4JavaConstants.CODEC_FLAG2_MIXED_REFS | FFmpeg4JavaConstants.CODEC_FLAG2_WPRED | FFmpeg4JavaConstants.CODEC_FLAG2_FASTPSKIP);
							codec_context.setFlags(FFmpeg4JavaConstants.CODEC_FLAG_LOOP_FILTER);
							codec_context.setProfile(FFmpeg4JavaConstants.FF_PROFILE_H264_BASELINE);
						}
						if (codec_context.getCodec_id() == CodecID.CODEC_ID_MPEG2VIDEO) {
							codec_context.setProfile(FFmpeg4JavaConstants.FF_PROFILE_MPEG2_HIGH);
						}
						if (codec_context.getCodec_id() == CodecID.CODEC_ID_MPEG2VIDEO || codec_context.getCodec_id() == CodecID.CODEC_ID_MPEG1VIDEO) {
							codec_context.setStrict_std_compliance(codec_context.getStrict_std_compliance() | FFmpeg4JavaConstants.FF_COMPLIANCE_UNOFFICIAL);
						}
//							if ((format_context.getFlags() & FFmpeg4Java.AVFMT_GLOBALHEADER) != 0)  {
							codec_context.setFlags(codec_context.getFlags() | FFmpeg4JavaConstants.CODEC_FLAG_GLOBAL_HEADER);
//							}
						codec = FFmpeg4Java.avcodec_find_encoder(codec_context.getCodec_id());
						int pkt_bit_buffer_size = PKT_BIT_BUFFER_SIZE;
						pkt_bit_buffer = SWIGTYPE_p_uint8_t.asTypePointer(FFmpeg4Java.av_malloc(pkt_bit_buffer_size));
						if (pkt_bit_buffer != null) {
							if (codec != null && FFmpeg4Java.avcodec_open(codec_context, codec) == 0) {
								if (AbstractVideoEncoder.logger.isLoggable(Level.FINE)) {
									AbstractVideoEncoder.logger.fine("Codec is " + codec.getName());
								}
								if (FFmpeg4Java.avio_open(format_context.get_aviocontext_p_p(), path.getAbsolutePath(), FFmpeg4JavaConstants.URL_WRONLY) == 0) {
									sws_context = FFmpeg4Java.sws_getCachedContext(null, codec_context.getWidth(), codec_context.getHeight(), PixelFormat.PIX_FMT_RGB24, codec_context.getWidth(), codec_context.getHeight(), PixelFormat.PIX_FMT_YUV420P, FFmpeg4JavaConstants.SWS_BILINEAR, null, null, null);
									if (sws_context != null) {
										FFmpeg4Java.av_write_header(format_context);
										rgb_frame = FFmpeg4Java.avcodec_alloc_frame();
										if (rgb_frame != null) {
											yuv_frame = FFmpeg4Java.avcodec_alloc_frame();
											if (yuv_frame != null) {
												int rgb_bit_buffer_size = FFmpeg4Java.avpicture_get_size(PixelFormat.PIX_FMT_RGB24, codec_context.getWidth(), codec_context.getHeight());
												rgb_bit_buffer = SWIGTYPE_p_uint8_t.asTypePointer(FFmpeg4Java.av_malloc(rgb_bit_buffer_size));
												if (rgb_bit_buffer != null) {
													int yuv_bit_buffer_size = FFmpeg4Java.avpicture_get_size(PixelFormat.PIX_FMT_YUV420P, codec_context.getWidth(), codec_context.getHeight());
													yuv_bit_buffer = SWIGTYPE_p_uint8_t.asTypePointer(FFmpeg4Java.av_malloc(yuv_bit_buffer_size));
													if (yuv_bit_buffer != null) {
														FFmpeg4Java.avpicture_fill(yuv_frame.asPicture(), yuv_bit_buffer, PixelFormat.PIX_FMT_YUV420P, codec_context.getWidth(), codec_context.getHeight());
														FFmpeg4Java.avpicture_fill(rgb_frame.asPicture(), rgb_bit_buffer, PixelFormat.PIX_FMT_RGB24, codec_context.getWidth(), codec_context.getHeight());
														for (int frame = 0; frame < frame_count; frame++) {
															if (AbstractVideoEncoder.logger.isLoggable(Level.FINE)) {
																if (frame % 10 == 0) {
																	AbstractVideoEncoder.logger.fine(frame + " frames encoded...");
																}
															}
															if (delegate != null) {
																delegate.didProgressChanged(frame * 100f / (frame_count - 1));
															}
															byte[] data = context.getPixelsAsByteArray(frame, 0, 0, context.getImageWidth(), context.getImageHeight(), 3);
															FFmpeg4Java.swig_set_bytes(rgb_bit_buffer, data);
															FFmpeg4Java.sws_scale(sws_context, rgb_frame.getData(), rgb_frame.getLinesize(), 0, codec_context.getHeight(), yuv_frame.getData(), yuv_frame.getLinesize());
															int ret = FFmpeg4Java.avcodec_encode_video(codec_context, pkt_bit_buffer, pkt_bit_buffer_size, yuv_frame);
															if (ret > 0) {
																AVPacket packet = new AVPacket();
																FFmpeg4Java.av_init_packet(packet);
																packet.setStream_index(stream.getId());
																packet.setData(pkt_bit_buffer);
																packet.setSize(ret);
																if (codec_context.getCoded_frame().getPts() != 0x8000000000000000L) {
																	packet.setPts(FFmpeg4Java.av_rescale_q(codec_context.getCoded_frame().getPts(), codec_context.getTime_base(), stream.getTime_base()));
																}
																if (codec_context.getCoded_frame().getKey_frame() != 0) {
																	packet.setFlags(packet.getFlags() | FFmpeg4JavaConstants.PKT_FLAG_KEY);
																}
																FFmpeg4Java.av_interleaved_write_frame(format_context, packet);
																FFmpeg4Java.av_free_packet(packet);
																packet.delete();
															}
															if (delegate != null && delegate.isInterrupted()) {
																break;
															}
														}
													}
												}
											}
										}
										FFmpeg4Java.av_write_trailer(format_context);
										FFmpeg4Java.sws_freeContext(sws_context);
									}
									FFmpeg4Java.avio_close(format_context.getPb());
								}
								FFmpeg4Java.avcodec_close(codec_context);
							}
						}
					}
				}
			}
			if (delegate == null || !delegate.isInterrupted()) {
				if (AbstractVideoEncoder.logger.isLoggable(Level.FINE)) {
					AbstractVideoEncoder.logger.fine(frame_count + " frames encoded.");
				}
				time = System.currentTimeMillis() - time;
				if (AbstractVideoEncoder.logger.isLoggable(Level.INFO)) {
					AbstractVideoEncoder.logger.info("Profile exported: elapsed time " + String.format("%3.2f", time / 1000.0d) + "s");
				}
				if (delegate != null) {
					delegate.didProgressChanged(100f);
				}
			}
		}
		catch (final Exception e) {
			throw new EncoderException(e);
		}
		finally {
			if (yuv_frame != null) {
				FFmpeg4Java.avpicture_free(yuv_frame.asPicture());
				yuv_frame.delete();
				yuv_frame = null;
			}
			if (rgb_frame != null) {
				FFmpeg4Java.avpicture_free(rgb_frame.asPicture());
				rgb_frame.delete();
				rgb_frame = null;
			}
			if (pkt_bit_buffer != null) {
				FFmpeg4Java.av_free(SWIGTYPE_p_uint8_t.asVoidPointer(pkt_bit_buffer));
				pkt_bit_buffer = null;
			}
			if (codec != null) {
				codec.delete();
				codec = null;
			}
			if (stream != null) {
				stream.delete();
				stream = null;
			}
			if (codec_context != null) {
				codec_context.delete();
				codec_context = null;
			}
			if (output_format != null) {
				output_format.delete();
				output_format = null;
			}
			if (format_context != null) {
				format_context.delete();
				format_context = null;
			}
		}
	}

	/**
	 * @return
	 */
	protected abstract String getFormatName();
}
