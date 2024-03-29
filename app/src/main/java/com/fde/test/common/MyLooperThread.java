package com.fde.test.common;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;


public class MyLooperThread extends  Thread{
    public Handler handler;

    @Override
    public void run() {
        super.run();
        Looper.prepare();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // 在这里处理消息
                Log.i("bella","------handleMessage----------");
            }
        };
        Looper.loop();
    }
}
