package com.glumes.ipc_binder.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.glumes.ipc_binder.binder.Book;
import com.glumes.ipc_binder.binder.BookManagerImpl;

import java.util.ArrayList;
import java.util.List;

public class BookManagerServiceBinder extends Service {


    private static final String TAG = BookManagerServiceBinder.class.getSimpleName() ;
    private List<Book> mBookArrayList = new ArrayList<>() ;

    @Override
    public void onCreate() {
        Log.e(TAG,"OnCreate Service Thread Name is" + Thread.currentThread().getName());
        super.onCreate();
    }

    BookManagerImpl mBinder = new BookManagerImpl();

    public BookManagerServiceBinder() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"return Binder") ;
        return mBinder ;
    }


    @Override
    public void onDestroy() {
        Log.e(TAG, "Service is destroyed" );
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "Service is unBind" );
        return super.onUnbind(intent);
    }
}
