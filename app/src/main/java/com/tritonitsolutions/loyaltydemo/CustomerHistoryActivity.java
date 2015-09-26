package com.tritonitsolutions.loyaltydemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TritonDev on 24/9/2015.
 */
public class CustomerHistoryActivity extends Activity {
    String user_id;
    ProgressDialog dialog;
    JSONArray data_customer_history=null;
    ListView lv;
    ArrayList<HashMap<String,String>> new_ch;
    CustomerHistoryAdapter adapter;
    public static final String CUSTOMER_HISTORY="customer_history";
    public static final String CH_PROD_ID="proid";
    public static final String CH_INVOICE_NO="invoice";
    public static final String CH_SALES_DATE="date";
    public static final String CH_QUANTITY ="qty";
    public static final String CH_TOTAL="total_amt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerhistory_layout);
        SharedPreferences pref = getSharedPreferences("User-id", MODE_PRIVATE);
        user_id = pref.getString("User_id", null);
        System.out.println("KEY---------->" + user_id);
        lv=(ListView)findViewById(R.id.lv_customer_history);
        new_ch= new ArrayList<HashMap<String,String>>();
        new loadCustomerHistory().execute();


    }
    private class loadCustomerHistory extends AsyncTask<String,Void,Void>{
        String serviceValue;

        protected void onPreExecute(){
            dialog=new ProgressDialog(CustomerHistoryActivity.this);
            dialog.setMessage("Loading...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected Void doInBackground(String... params) {
            try {

                ServiceHandler handler=new ServiceHandler();
                serviceValue=handler.makeServiceCall(URL.CUSTOMER_HISTORY_URL + user_id,ServiceHandler.GET);
                System.out.println("URLSSS------->" +serviceValue);

            }catch (Exception e){
                e.printStackTrace();
            }
            if(serviceValue != null){
                try {
                    JSONObject object=new JSONObject(serviceValue);
                    data_customer_history=object.getJSONArray(CUSTOMER_HISTORY);
                    for(int i=0;i<data_customer_history.length();i++){
                        JSONObject jsonObject=data_customer_history.getJSONObject(i);
                        String pro_id=jsonObject.getString(CH_PROD_ID);
                        String iv_no=jsonObject.getString(CH_INVOICE_NO);
                        String sales_date=jsonObject.getString(CH_SALES_DATE);
                        String qty=jsonObject.getString(CH_QUANTITY);
                        String total=jsonObject.getString(CH_TOTAL);

                        HashMap<String,String>ch_data=new HashMap<String,String>();
                        ch_data.put(CH_PROD_ID,pro_id);
                        ch_data.put(CH_INVOICE_NO,iv_no);
                        ch_data.put(CH_SALES_DATE,sales_date);
                        ch_data.put(CH_QUANTITY,qty);
                        ch_data.put(CH_TOTAL,total);
                        new_ch.add(ch_data);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        protected void onPostExecute(Void s){
            super.onPostExecute(s);
            adapter=new CustomerHistoryAdapter(CustomerHistoryActivity.this,new_ch);
            lv.setAdapter(adapter);
            dialog.dismiss();
            }

        }
    }

