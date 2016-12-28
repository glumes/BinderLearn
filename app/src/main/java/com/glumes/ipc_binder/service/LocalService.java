package com.glumes.ipc_binder.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class LocalService extends Service {


    private static final String TAG = LocalService.class.getSimpleName() ;

    public LocalService() {
    }

    private LocalBinder binder = new LocalBinder();

    @Override
    public void onCreate() {
        Log.d(TAG,"Create Local Service") ;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder ;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"Service is Unbind");
        return true ;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG,"Start Local Service") ;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"Stop Local Service");
        super.onDestroy();
    }

    public class LocalBinder extends Binder{
        public LocalBinder() {
            super();
        }

        public void printMsg(){
            Log.d(TAG,"LocalBinder Thread Name is " + Thread.currentThread().getName());
        }
    }


}
