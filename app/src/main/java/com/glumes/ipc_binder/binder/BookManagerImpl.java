package com.glumes.ipc_binder.binder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoying on 2016/12/28.
 */

public class BookManagerImpl extends Binder implements IBookManager{


    private static final String TAG = BookManagerImpl.class.getSimpleName() ;

    public BookManagerImpl() {
        this.attachInterface(this,DESCRIPTOR);
    }



    public static IBookManager asInterface(IBinder obj){
        if (obj==null){
            return null ;
        }

        IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if ((iin !=null) && (iin instanceof IBookManager)){
            return (IBookManager) iin;
        }

        return new BookManagerImpl.Proxy(obj);
    }


    @Override
    public List<Book> getBookList() throws RemoteException {
        Log.d(TAG ,"getBookList");
        Log.d(TAG,"current thread name is " + Thread.currentThread().getName());
        return new ArrayList<Book>();
    }

    @Override
    public void addBook(Book book) throws RemoteException {
        Log.d(TAG ,"add Book");
        Log.d(TAG,"current thread name is " + Thread.currentThread().getName());

    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    /**
     * 当 Client 和 Server 处于同一进程时，不会走 onTransact 调用方法，直接调用上方的 getBookList / addBook 方法
     * 而当 Client 与 Server 处于不同进程时，则需要 Binder 驱动来跨进程通信。
     * Client 持有 Binder 的代理对象 Proxy ，调用对应的 getBookList / addBook 方法。
     * 代理对象 Proxy 则调用在 Binder 驱动中的 mRemote 对象来访问位于另一进程中的 Server 。
     * Server 收到 Binder 驱动发来的消息时，则调用 onTransact 方法来执行 Client 想要的服务 。
     * 在 onTransact 的 switch/case 中根据对应的方法 id 执行不同的操作 。
     *
     * @param code
     * @param data
     * @param reply
     * @param flags
     * @return
     * @throws RemoteException
     */
    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code){
            case INTERFACE_TRANSACTION:{
                reply.writeString(DESCRIPTOR);
                return true ;
            }
            case TRANSACTION_getBookList:{
                Log.d(TAG,"current thread name is " + Thread.currentThread().getName());
                data.enforceInterface(DESCRIPTOR);
                List<Book> _result = this.getBookList();
                reply.writeNoException();
                reply.writeTypedList(_result);
                return true;
            }
            case TRANSACTION_addBook:{
                Log.d(TAG,"current thread name is " + Thread.currentThread().getName());
                data.enforceInterface(DESCRIPTOR);
                Book _arg0;
                if ((0 != data.readInt())) {
                    _arg0 = Book.CREATOR.createFromParcel(data);
                } else {
                    _arg0 = null;
                }
                this.addBook(_arg0);
                reply.writeNoException();
                return true;
            }

        }

        return super.onTransact(code,data,reply,flags);
    }


    private static class Proxy implements IBookManager{

        private IBinder mRemote ;

        public Proxy(IBinder remote) {
            mRemote = remote;
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            List<Book> _result;
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getBookList, _data, _reply, 0);
                _reply.readException();
                _result = _reply.createTypedArrayList(Book.CREATOR);
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            return _result;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                if ((book != null)) {
                    _data.writeInt(1);
                    book.writeToParcel(_data, 0);
                } else {
                    _data.writeInt(0);
                }
                mRemote.transact(TRANSACTION_addBook, _data, _reply, 0);
                _reply.readException();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }


        public String getInterfaceDescriptor(){
            return DESCRIPTOR ;
        }

    }
}
