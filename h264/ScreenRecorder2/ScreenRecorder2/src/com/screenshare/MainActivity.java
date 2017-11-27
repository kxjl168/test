package com.screenshare;

import com.webs.SocketS;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.projection.MediaProjectionManager;
import android.os.IBinder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    MediaProjectionManager mMediaProjectionManager;
    private int mResultCode;
    private Intent mResultData;
    private String mReceiverIp = "";
    private Context mContext;
    private static final int REQUEST_MEDIA_PROJECTION = 100;
    
    private EditText mEditTextIP;
    
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditTextIP = (EditText) findViewById(R.id.serverIp);
        mMediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        
        findViewById(R.id.btn_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "==== start ====");
                if (mReceiverIp != null) {
                    startCaptureScreen();
                } else {
                    Toast.makeText(mContext, R.string.no_receiver, Toast.LENGTH_SHORT).show();
                }
            }
        });
        
      
        
        startService();
    }
    

    
    private void startService() {
        if (mResultCode != 0 && mResultData != null && mReceiverIp != null) {
            Intent intent = new Intent(this, RecordScreenService.class);
            intent.putExtra(Common.EXTRA_RESULT_CODE, mResultCode);
            intent.putExtra(Common.EXTRA_RESULT_DATA, mResultData);
            intent.putExtra(Common.EXTRA_RECEIVER_IP, mEditTextIP.getText().toString());
//            intent.putExtra(Common.EXTRA_VIDEO_FORMAT, mSelectedFormat);
//            intent.putExtra(Common.EXTRA_SCREEN_WIDTH, mSelectedWidth);
//            intent.putExtra(Common.EXTRA_SCREEN_HEIGHT, mSelectedHeight);
//            intent.putExtra(Common.EXTRA_SCREEN_DPI, mSelectedDpi);
//            intent.putExtra(Common.EXTRA_VIDEO_BITRATE, mSelectedBitrate);
            startService(intent);
            bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        } else {
            Intent intent = new Intent(this, RecordScreenService.class);
            startService(intent);
            bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    private void startCaptureScreen() {
        if (mResultCode != 0 && mResultData != null) {
            startService();
        } else {
            Log.i(TAG, "Requesting confirmation");
            // This initiates a prompt dialog for the user to confirm screen projection.
            startActivityForResult(
                    mMediaProjectionManager.createScreenCaptureIntent(),
                    REQUEST_MEDIA_PROJECTION);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MEDIA_PROJECTION) {
            if (resultCode != Activity.RESULT_OK) {
                Log.e(TAG, "User cancelled");
                Toast.makeText(mContext, R.string.user_cancelled, Toast.LENGTH_SHORT).show();
                return;
            }
            Log.e(TAG, "Starting screen capture");
            mResultCode = resultCode;
            mResultData = data;
            startCaptureScreen();
        }
    }
}
