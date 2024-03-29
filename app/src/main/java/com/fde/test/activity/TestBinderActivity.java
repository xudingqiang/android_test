package com.fde.test.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.appcompat.app.AppCompatActivity;

import com.fde.test.IMyInterface;
import com.fde.test.R;

public class TestBinderActivity extends AppCompatActivity {
    private IMyInterface mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_binder);
    }



    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IMyInterface.Stub.asInterface(service);
            try {
//                mService.sayHello(); // 调用服务端方法
                mService.getClientData("welcome from client bella ");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.fde.test", "com.fde.test.MyService"));
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
    }
}