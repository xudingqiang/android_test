package com.fde.test.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fde.test.R;
import com.fde.test.common.MyLooperThread;

public class TestJniActivity extends AppCompatActivity {
    TextView txtTest;
//
//    static {
//        System.loadLibrary("native_egl");
//    }

    MyLooperThread myLooperThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_jni);



        myLooperThread = new MyLooperThread();
        myLooperThread.start();

        txtTest = findViewById(R.id.txtTest);
        txtTest.setOnClickListener(view ->{
            Message message = new Message();
            myLooperThread.handler.sendMessage(message);
        });





    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myLooperThread.handler.getLooper().quit();
    }
}