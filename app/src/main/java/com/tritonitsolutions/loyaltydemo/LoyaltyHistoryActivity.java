package com.tritonitsolutions.loyaltydemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by TritonDev on 14/9/2015.
 */
public class LoyaltyHistoryActivity extends Activity {
    public static final String LOYALTY_HISTORY = "loyalty_history";
    public static final String LOYALTY_ID = "loyalty_id";
    public static final String LOYALTY_POINT = "loyalty_point";
    ArrayList<HashMap<String,String>> lh_data;
    ListView lv;
    JSONArray data_history = null;
    ProgressDialog dialog;
    String user_id;
    LoyaltyHistoryAdapter adapter;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loyaltyhistory_layout);
        lv=(ListView)findViewById(R.id.lv_point_history);


        SharedPreferences pref = getSharedPreferences("User-id", MODE_PRIVATE);
        user_id = pref.getString("User_id", null);
        System.out.println("KEY---------->" + user_id);
        lh_data=new ArrayList<HashMap<String, String>>();
        new loadPurchaseHistoryData().execute();

    }

    private class loadPurchaseHistoryData extends AsyncTask<String, Void, Void> {
        String s;

        protected void onPreExecute() {

            dialog = new ProgressDialog(LoyaltyHistoryActivity.this);
            dialog.setMessage("Loading...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();


        }

        @Override
        protected Void doInBackground(String... params) {
            try {

                ServiceHandler serviceHandler = new ServiceHandler();
                s = serviceHandler.makeServiceCall(URL.LOYALTY_HISTORY_URL + user_id, ServiceHandler.GET);
                System.out.println("URLS------->" + s);

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    data_history = jsonObject.getJSONArray(LOYALTY_HISTORY);
                    if (LOYALTY_HISTORY != null) {
                        for (int i = 0; i < data_history.length(); i++) {
                            JSONObject object = data_history.getJSONObject(i);
                            String lh_loyalty_id = object.getString(LOYALTY_ID);
                            String lh_loyalty_points = object.getString(LOYALTY_POINT);

                            HashMap<String,String> lh=new HashMap<String,String>();
                            lh.put(LOYALTY_ID,lh_loyalty_id);
                            lh.put(LOYALTY_POINT, lh_loyalty_points);
                            lh_data.add(lh);

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "There is no purchase history from this login!", Toast.LENGTH_LONG);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            return null;

        }

        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            adapter=new LoyaltyHistoryAdapter(LoyaltyHistoryActivity.this,lh_data);
            lv.setAdapter(adapter);
            dialog.dismiss();
        }
    }

}
