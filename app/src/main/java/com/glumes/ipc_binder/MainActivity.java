package com.glumes.ipc_binder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.bookmanagerservice)
    CardView mBookmanagerservice;
    @BindView(R.id.localservice)
    CardView mLocalservice;
    @BindView(R.id.bookmanagerservice_aidl)
    CardView mBookmanagerserviceAidl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mBookmanagerservice.setOnClickListener(this);
        mLocalservice.setOnClickListener(this);
        mBookmanagerserviceAidl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bookmanagerservice:
                startActivity(new Intent(MainActivity.this, BookManagerActivityBinder.class));
                break;
            case R.id.localservice:
                startActivity(new Intent(MainActivity.this, LocalServiceActivity.class));
                break;
            case R.id.bookmanagerservice_aidl:
                startActivity(new Intent(MainActivity.this,BookManagerActivityAidl.class));
                break;
            default:
                break;
        }
    }
}
