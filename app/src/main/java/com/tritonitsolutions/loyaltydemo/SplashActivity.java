package com.tritonitsolutions.loyaltydemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
        boolean net = isNetworkAvailable();
        if (net) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_TIME);
        }
        else {
            AlertDialog dialog=new AlertDialog.Builder(SplashActivity.this).create();
            dialog.setTitle("Internet Connection");
            dialog.setMessage("Now Internet connection is not available.Check your Network");
            dialog.setIcon(R.drawable.ic_internet);
            dialog.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dialog.show();
        }
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager manager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        return info !=null && info.isConnected();
    }
}
