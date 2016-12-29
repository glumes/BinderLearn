package com.glumes.ipc_binder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.glumes.ipc_binder.aidl.Book;
import com.glumes.ipc_binder.aidl.IBookManager;
import com.glumes.ipc_binder.service.BookManagerServiceAidl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookManagerActivityAidl extends AppCompatActivity {

    private static final String TAG = BookManagerActivityAidl.class.getSimpleName();
    @BindView(R.id.stopService)
    Button mStopService;

    private List<Book> mBookArrayList = new ArrayList<>();

    IBookManager mService;

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // ActivityManagerService 在 bindService 的过程中会回调该方法，将 Service 的 Binder 对象传回来
            // 再调用 asInterface 方法根据是否在同一进程进行转换。
            mService = IBookManager.Stub.asInterface(iBinder);
            Log.e(TAG, "current thread name is" + Thread.currentThread().getName());
            Log.e(TAG, "service is connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(TAG, "service is disconnected");
        }
    };

    @BindView(R.id.addBook)
    Button mAddBook;
    @BindView(R.id.getBookList)
    Button mGetBookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        ButterKnife.bind(this);
        Intent intent = new Intent(BookManagerActivityAidl.this, BookManagerServiceAidl.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }


    @OnClick({R.id.addBook, R.id.getBookList,R.id.stopService})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addBook:
                if (mService != null) {
                    try {
                        mService.addBook(new Book(1, "aidl"));
                        Log.e(TAG, "current thread name is " + Thread.currentThread().getName());
                        Log.e(TAG, "current thread state is " + Thread.currentThread().getState());
                        Log.e(TAG, "current thread id is " + Thread.currentThread().getId());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.getBookList:
                if (mService != null) {
                    try {
                        mBookArrayList = mService.getBookList();
                        for (Book book : mBookArrayList) {
                            Log.e(TAG, "book name is " + book.getBookName());
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.stopService:
                Log.e(TAG,"stop service");
                if (mService != null){
                    unbindService(mConnection);
                }

        }
    }

}
