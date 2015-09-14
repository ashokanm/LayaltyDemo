package com.tritonitsolutions.loyaltydemo;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TritonDev on 14/9/2015.
 */
public class PurchaseHistoryActivity extends ActionBarActivity {
    Toolbar toolbar;
    public static final String PURCHASE_HISTORY="sales_history";
    public static final String SALES_AMOUNT="sales_amount";
    public static final String SALE_DATE="sales_date";
    public static final String INVOICE_NUMBER="invoice_number";
    ArrayList history1=null;
    ArrayList history2=null;
    ArrayList history3=null;
    JSONArray data_history=null;
    ProgressDialog dialog;
    TextView tv_history;
    String user_id;
    LinearLayout ll_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchasehistory_layout);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        SharedPreferences pref = getSharedPreferences("User-id", MODE_PRIVATE);
        user_id = pref.getString("User_id", null);
        System.out.println("KEY---------->"+user_id);
        new loadPurchaseHistoryData().execute();
        ll_history=(LinearLayout)findViewById(R.id.ll_purchase_history);
        history1=new ArrayList();
        history2=new ArrayList();
        history3=new ArrayList();


    }
    private class loadPurchaseHistoryData extends AsyncTask<String,Void,Void>{
        protected void onPreExecute(){

            dialog=new ProgressDialog(PurchaseHistoryActivity.this);
            dialog.setMessage("Loading...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();


        }

        @Override
        protected Void doInBackground(String... params) {
            ServiceHandler serviceHandler=new ServiceHandler();
            String s=serviceHandler.makeServiceCall(URL.PURCHASE_HISTORY_URL+user_id,ServiceHandler.GET);
            if(s != null){
                try {
                    JSONObject jsonObject=new JSONObject(s);
                     data_history=jsonObject.getJSONArray(PURCHASE_HISTORY);
                    for (int i=0;i<data_history.length();i++){
                        JSONObject object=data_history.getJSONObject(i);
                        String ph_sale_date=object.getString(SALE_DATE);
                        String ph_invoice_number=object.getString(INVOICE_NUMBER);
                        String ph_sales_amount=object.getString(SALES_AMOUNT);

                        history1.add(ph_sales_amount);
                        history2.add(ph_sale_date);
                        history3.add(ph_invoice_number);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        }
        protected void onPostExecute(Void s){
            super.onPostExecute(s);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
          try {
              for (int j=0;j<= data_history.length()-1;j++){
                  tv_history=new TextView(PurchaseHistoryActivity.this);
                  params.gravity= Gravity.CENTER;
                  tv_history.setWidth(150);
                  tv_history.setBackgroundColor(getResources().getColor(R.color.notification_background));
                  tv_history.setTextColor(getResources().getColor(R.color.notification_text));
                  tv_history.setPadding(Gravity.CENTER, 10, Gravity.CENTER, 10);
                  tv_history.setTextSize(15);
                  params.setMargins(Gravity.CENTER, 30, 30, 30);
                  tv_history.setText(String.valueOf(history2.get(j)) + "\n" + history3.get(j) + "\n" + history1.get(j));
                  tv_history.setTypeface(Typeface.SERIF, Typeface.BOLD);
                  tv_history.setLayoutParams(params);
                  ll_history.addView(tv_history);
              }

          }catch (Exception e){
              e.printStackTrace();
          }
            dialog.dismiss();
        }
    }

}
