package com.fde.test.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fde.test.R;
import com.fde.test.view.MyGLRenderer;
import com.fde.test.view.MyGLSurfaceView;

public class EglTestActivity extends AppCompatActivity {
    MyGLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egl_test);

        glSurfaceView = findViewById(R.id.gl_surface_view);
//        glSurfaceView.setRenderer(new MyGLRenderer());

    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();

    }
}