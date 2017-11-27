package com.screenshare;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
//import android.support.annotation.Nullable;
//import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Surface;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import com.webs.SocketS;

public class RecordScreenService extends Service {
	private static final String TAG = "RecordScreenService";
	MediaProjectionManager mMediaProjectionManager;
	private MediaProjection mMediaProjection;
	private VirtualDisplay mVirtualDisplay;
	private Surface mInputSurface;
	private String mReceiverIp;
	private int mResultCode;
	private Intent mResultData;
	private MediaCodec mVideoEncoder;
	private MediaCodec.BufferInfo mVideoBufferInfo;
	private Socket mSocket;
	private OutputStream mSocketOutputStream;
	private String mSelectedFormat;
	private int mSelectedWidth;
	private int mSelectedHeight;
	private Handler mDrainHandler = new Handler();

	private static final String HTTP_MESSAGE_TEMPLATE = "POST /api/v1/h264 HTTP/1.1\r\n"
			+ "Connection: close\r\n"
			+ "X-WIDTH: %1$d\r\n"
			+ "X-HEIGHT: %2$d\r\n" + "\r\n";

	private Runnable mStartEncodingRunnable = new Runnable() {
		// @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
		@Override
		public void run() {
			if (!startScreenCapture()) {
				Log.i(TAG, "Failed to start capturing screen");
			}
		}
	};

	// 1280x720@25
	private static final byte[] H264_PREDEFINED_HEADER_1280x720 = {
			(byte) 0x21, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x01, (byte) 0x67, (byte) 0x42, (byte) 0x80, (byte) 0x20,
			(byte) 0xda, (byte) 0x01, (byte) 0x40, (byte) 0x16, (byte) 0xe8,
			(byte) 0x06, (byte) 0xd0, (byte) 0xa1, (byte) 0x35, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x68, (byte) 0xce,
			(byte) 0x06, (byte) 0xe2, (byte) 0x32, (byte) 0x24, (byte) 0x00,
			(byte) 0x00, (byte) 0x7a, (byte) 0x83, (byte) 0x3d, (byte) 0xae,
			(byte) 0x37, (byte) 0x00, (byte) 0x00 };

	// 800x480@25
	private static final byte[] H264_PREDEFINED_HEADER_800x480 = { 
			(byte) 0x21, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
			(byte) 0x67, (byte) 0x42, (byte) 0x80, (byte) 0x20, (byte) 0xda,
			(byte) 0x03, (byte) 0x20, (byte) 0xf6, (byte) 0x80, (byte) 0x6d,
			(byte) 0x0a, (byte) 0x13, (byte) 0x50, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x01, (byte) 0x68, (byte) 0xce, (byte) 0x06,
			(byte) 0xe2, (byte) 0x32, (byte) 0x24, (byte) 0x00, (byte) 0x00,
			(byte) 0x7a, (byte) 0x83, (byte) 0x3d, (byte) 0xae, (byte) 0x37,
			(byte) 0x00, (byte) 0x00 };

	private IvfWriter mIvfWriter;
	private Runnable mDrainEncoderRunnable = new Runnable() {
		// @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
		@Override
		public void run() {
			drainEncoder();
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "=====================onCreate====================");
		mMediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);

		createServerSocket();
	}

	private void createServerSocket() {
		new Thread(new Runnable() {
			@Override
			public void run() {

				SocketS.start(8888);

			}
		}).start();

	}

	// @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "=====================onStartCommand====================");
		if (intent == null) {
			return START_NOT_STICKY;
		}
		mSelectedFormat = Common.DEFAULT_VIDEO_MIME_TYPE;
		mSelectedWidth = Common.DEFAULT_SCREEN_WIDTH;
		mSelectedHeight = Common.DEFAULT_SCREEN_HEIGHT;
		mReceiverIp = intent.getStringExtra(Common.EXTRA_RECEIVER_IP);
		mResultCode = intent.getIntExtra(Common.EXTRA_RESULT_CODE, -1);
		mResultData = intent.getParcelableExtra(Common.EXTRA_RESULT_DATA);
		if (mReceiverIp == null) {
			return START_NOT_STICKY;
		}
		/*
		 * if (!createSocket()) { Log.i(TAG,
		 * "Failed to create socket to receiver, ip: " + mReceiverIp); return
		 * START_NOT_STICKY; }
		 */
		if (!startScreenCapture()) {
			Log.i(TAG, "Failed to start capture screen");
			return START_NOT_STICKY;
		}
		return START_STICKY;
	}

	// @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
	private void prepareVideoEncoder() {

		mVideoBufferInfo = new MediaCodec.BufferInfo();
		MediaFormat format = MediaFormat.createVideoFormat(
				Common.DEFAULT_VIDEO_MIME_TYPE, Common.DEFAULT_SCREEN_WIDTH,
				Common.DEFAULT_SCREEN_HEIGHT);
		int frameRate = Common.DEFAULT_VIDEO_FPS;

		format.setInteger(MediaFormat.KEY_COLOR_FORMAT,
				MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
		format.setInteger(MediaFormat.KEY_BIT_RATE,
				Common.DEFAULT_VIDEO_BITRATE);
		format.setInteger(MediaFormat.KEY_FRAME_RATE, frameRate);
		format.setInteger(MediaFormat.KEY_CAPTURE_RATE, frameRate);
		format.setInteger(MediaFormat.KEY_REPEAT_PREVIOUS_FRAME_AFTER,
				1000000 / frameRate);
		format.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 1);
		format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1); // 1 seconds
																// between
																// I-frames

		try {
			mVideoEncoder = MediaCodec
					.createEncoderByType(Common.DEFAULT_VIDEO_MIME_TYPE);
			mVideoEncoder.configure(format, null, null,
					MediaCodec.CONFIGURE_FLAG_ENCODE);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
				mInputSurface = mVideoEncoder.createInputSurface();
			}
			mVideoEncoder.start();
		} catch (IOException e) {
			Log.i(TAG, "Failed to initial encoder, e: " + e);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				releaseEncoders();
			}
		}
	}

	// @RequiresApi(api = Build.VERSION_CODES.KITKAT)
	private void stopScreenCapture() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			releaseEncoders();
		}
		if (mVirtualDisplay == null) {
			return;
		}
		mVirtualDisplay.release();
		mVirtualDisplay = null;
	}

	// @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	private void releaseEncoders() {
		mDrainHandler.removeCallbacks(mDrainEncoderRunnable);
		if (mVideoEncoder != null) {
			mVideoEncoder.stop();
			mVideoEncoder.release();
			mVideoEncoder = null;
		}
		if (mInputSurface != null) {
			mInputSurface.release();
			mInputSurface = null;
		}
		if (mMediaProjection != null) {
			mMediaProjection.stop();
			mMediaProjection = null;
		}
		mVideoBufferInfo = null;
	}

	// @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	private boolean startScreenCapture() {
		Log.i(TAG, "mResultCode: " + mResultCode + ", mResultData: "
				+ mResultData);
		if (mResultCode != 0 && mResultData != null) {
			setUpMediaProjection();
			startRecording();
			return true;
		}
		return false;
	}

	// @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	private void setUpMediaProjection() {
		mMediaProjection = mMediaProjectionManager.getMediaProjection(
				mResultCode, mResultData);
	}

	// @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	private void startRecording() {
		Log.i(TAG, "startRecording.............");
		prepareVideoEncoder();
		mVirtualDisplay = mMediaProjection
				.createVirtualDisplay("Recording Display",
						Common.DEFAULT_SCREEN_WIDTH,
						Common.DEFAULT_SCREEN_HEIGHT,
						Common.DEFAULT_SCREEN_DPI, 0 /* flags */, mInputSurface,
						null /* callback */, null /* handler */);
		drainEncoder();
	}

	private boolean isfirst = true;

	// @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
	private boolean drainEncoder() {
		mDrainHandler.removeCallbacks(mDrainEncoderRunnable);
		while (true) {

			int bufferIndex = mVideoEncoder.dequeueOutputBuffer(
					mVideoBufferInfo, 0);
			if (bufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER) {
				break;
			} else if (bufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
			} else if (bufferIndex < 0) {
				// not sure what's going on, ignore it
			} else {
				ByteBuffer encodedData = null;
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					encodedData = mVideoEncoder.getOutputBuffer(bufferIndex);
				}
				if (encodedData == null) {
					throw new RuntimeException(
							"couldn't fetch buffer at index " + bufferIndex);
				}
				if (mVideoBufferInfo.size != 0) {
					encodedData.position(mVideoBufferInfo.offset);
					encodedData.limit(mVideoBufferInfo.offset
							+ mVideoBufferInfo.size);

					byte[] b = new byte[encodedData.remaining()];

					Log.i(TAG, String.valueOf(b.length));
					encodedData.get(b);

					try {
						/*
						 * if (mIvfWriter != null) mIvfWriter.writeFrame(b,
						 * mVideoBufferInfo.presentationTimeUs);
						 */
						/*
						 * if (mIvfWriter != null) mIvfWriter.writeFrame(b,
						 * mVideoBufferInfo.presentationTimeUs); else
						 */
						SocketS.sendDataToAllClinet(b);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					/*
					 * if (mSocketOutputStream != null) { try { byte[] b = new
					 * byte[encodedData.remaining()]; encodedData.get(b); if
					 * (mIvfWriter != null) { mIvfWriter.writeFrame(b,
					 * mVideoBufferInfo.presentationTimeUs); } else {
					 * 
					 * SocketS.sendData(b);
					 * 
					 * // mSocketOutputStream.write(b); } } catch (IOException
					 * e) { Log.i(TAG,
					 * "Failed to write data to socket, stop casting");
					 * e.printStackTrace(); if (Build.VERSION.SDK_INT >=
					 * Build.VERSION_CODES.KITKAT) { stopScreenCapture(); }
					 * return false; } }
					 */
				}

				mVideoEncoder.releaseOutputBuffer(bufferIndex, false);

				if ((mVideoBufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
					break;
				}
			}
		}

		mDrainHandler.postDelayed(mDrainEncoderRunnable, 10);
		return true;
	}

	private boolean createSocket() {
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					InetAddress serverAddr = InetAddress.getByName(mReceiverIp);
					mSocket = new Socket(serverAddr, Common.VIEWER_PORT);
					mSocketOutputStream = mSocket.getOutputStream();
					OutputStreamWriter osw = new OutputStreamWriter(
							mSocketOutputStream);
					osw.write(String.format(HTTP_MESSAGE_TEMPLATE,
							mSelectedWidth, mSelectedHeight));
					osw.flush();
					mSocketOutputStream.flush();
					if (mSelectedFormat.equals(MediaFormat.MIMETYPE_VIDEO_AVC)) {
						if (mSelectedWidth == 1280 && mSelectedHeight == 720) {
							mSocketOutputStream
									.write(H264_PREDEFINED_HEADER_1280x720);
						} else if (mSelectedWidth == 800
								&& mSelectedHeight == 480) {
							mSocketOutputStream
									.write(H264_PREDEFINED_HEADER_800x480);
						} else {
							Log.i(TAG, "Unknown width: " + mSelectedWidth
									+ ", height: " + mSelectedHeight);
							mSocketOutputStream.close();
							mSocket.close();
							mSocket = null;
							mSocketOutputStream = null;
						}
					} else if (mSelectedFormat
							.equals(MediaFormat.MIMETYPE_VIDEO_VP8)) {
						mIvfWriter = new IvfWriter(mSocketOutputStream,
								mSelectedWidth, mSelectedHeight);
						mIvfWriter.writeHeader();
					} else {
						Log.i(TAG, "Unknown format: " + mSelectedFormat);
						mSocketOutputStream.close();
						mSocket.close();
						mSocket = null;
						mSocketOutputStream = null;
					}
					return;
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				mSocket = null;
				mSocketOutputStream = null;
			}
		});
		th.start();
		try {
			th.join();
			if (mSocket != null && mSocketOutputStream != null) {
				return true;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void closeSocket() {
		closeSocket(false);
	}

	private void closeSocket(boolean closeServerSocket) {
		if (mSocket != null) {
			try {
				mSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		mSocket = null;
		mSocketOutputStream = null;
	}

	// @Nullable
	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "=====================onBind====================");
		return null;
	}

	@Override
	public void onRebind(Intent intent) {
		Log.i(TAG, "=====================onRebind====================");
		super.onRebind(intent);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.i(TAG, "=====================onUnbind====================");
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "=====================onDestroy====================");
		super.onDestroy();
	}

}
