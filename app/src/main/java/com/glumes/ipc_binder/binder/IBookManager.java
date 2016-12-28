package com.glumes.ipc_binder.binder;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import java.util.List;

/**
 * Created by zhaoying on 2016/12/28.
 */

public interface IBookManager extends IInterface {

    static final String DESCRIPTOR = "com.glumes.ipc_binder";


    static final int TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 1;
    static final int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 2;

    List<Book> getBookList() throws RemoteException;

    void addBook(Book book) throws RemoteException;
}
