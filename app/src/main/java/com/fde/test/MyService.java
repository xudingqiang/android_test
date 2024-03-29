package com.fde.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("bella", "..........onCreate.......");
    }

    private final IBinder binder = new IMyInterface.Stub() {
        @Override
        public void sayHello() {
            Log.i("bella", "Hello from Service");
        }

        @Override
        public String getClientData(String data) {
            Log.i("bella", "Hello from Service "+data);
            return  "fde_"+ data;
        }

    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("bella", "..........onBind.......");
        return binder;
    }
}
