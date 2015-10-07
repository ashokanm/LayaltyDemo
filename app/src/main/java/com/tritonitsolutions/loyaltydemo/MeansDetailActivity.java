package com.tritonitsolutions.loyaltydemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;

/**
 * Created by TritonDev on 6/10/2015.
 */
public class MeansDetailActivity extends ActionBarActivity {
    ProgressDialog dialog;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensdetail_layout);
        value=getIntent().getStringExtra("mens");
        System.out.println("Data----->"+value);

    }
    private class loadMensDetailData extends AsyncTask<String,Void,Void>{
        String jsonValue;

      protected void onPreExecute(){
          dialog=new ProgressDialog(MeansDetailActivity.this);
          dialog.setMessage("Loading...");
          dialog.setIndeterminate(true);
          dialog.setCancelable(false);
          dialog.show();

      }
        @Override
        protected Void doInBackground(String... params) {
            try {
                ServiceHandler handler=new ServiceHandler();
                jsonValue=handler.makeServiceCall(URL.MENS_DETAIL_URL + value,ServiceHandler.GET);


            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }
}
