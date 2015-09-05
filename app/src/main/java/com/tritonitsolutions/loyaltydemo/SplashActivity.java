package com.tritonitsolutions.loyaltydemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.tritonitsolutions.layaltydemo.R;

/**
 * Created by TritonDev on 8/17/2015.
 */
public class SplashActivity extends Activity {
    private static int SPLASH_TIME=3000;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME);
    }
}
