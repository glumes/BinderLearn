package com.glumes.ipc_binder;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.glumes.ipc_binder.service.LocalService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocalServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LocalServiceActivity.class.getSimpleName();

    @BindView(R.id.startService)
    Button mStartService;
    @BindView(R.id.stopService)
    Button mStopService;

    private static final String LOCAL_SERVICE_ACTION = "com.ipc.localservice";
    @BindView(R.id.bindService)
    Button mBindService;
    @BindView(R.id.unbindService)
    Button mUnbindService;
    @BindView(R.id.bindServiceThread)
    Button mBindServiceThread;

    private ServiceConnection mServiceConnection;
    // 通过 Binder 与 Service 通信
    LocalService.LocalBinder mLocalBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_service);
        ButterKnife.bind(this);

        mStartService.setOnClickListener(this);
        mStopService.setOnClickListener(this);
        mBindService.setOnClickListener(this);
        mUnbindService.setOnClickListener(this);
        mBindServiceThread.setOnClickListener(this);

        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.d(TAG, "Service Connected");
                mLocalBinder = (LocalService.LocalBinder) iBinder;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.d(TAG, "Service Disconnected");
            }
        };
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.startService:

                startService(new Intent(LocalServiceActivity.this, LocalService.class));
                break;
            case R.id.stopService:
                stopService(new Intent(LocalServiceActivity.this, LocalService.class));
                break;
            case R.id.bindService:
                bindService(new Intent(LocalServiceActivity.this, LocalService.class), mServiceConnection,
                        Service.BIND_AUTO_CREATE);
                break;
            case R.id.unbindService:
                unbindService(mServiceConnection);
                break;
            case R.id.bindServiceThread:
                if (mLocalBinder!=null){
                    mLocalBinder.printMsg();
                }
            default:
                break;
        }
    }
}
