package com.glumes.ipc_binder.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.glumes.ipc_binder.aidl.Book;
import com.glumes.ipc_binder.aidl.IBookManager;

import java.util.ArrayList;
import java.util.List;

public class BookManagerService extends Service {


    private static final String TAG = BookManagerService.class.getSimpleName() ;
    private List<Book> mBookArrayList = new ArrayList<>() ;


    @Override
    public void onCreate() {
        Log.e(TAG,"OnCreate Service Thread Name is" + Thread.currentThread().getName());
        super.onCreate();
    }


    /**
     * 方法的具体实现
     */
    IBookManager.Stub mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.e(TAG,"getBookList Thread Name is" + Thread.currentThread().getName());
            return mBookArrayList ;
        }

        @Override
        public void addBook(Book book) throws RemoteException {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.e(TAG,"add book " + book.getBookName() );
            Log.e(TAG,"addBook Thread Name is" + Thread.currentThread().getName());
            mBookArrayList.add(book);
        }
    };

    public BookManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
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
