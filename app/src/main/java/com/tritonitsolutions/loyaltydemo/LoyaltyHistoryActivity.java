package com.tritonitsolutions.loyaltydemo;

import android.app.Activity;
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
import android.widget.Toast;

import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TritonDev on 14/9/2015.
 */
public class LoyaltyHistoryActivity extends Activity {
    public static final String LOYALTY_HISTORY = "loyalty_history";
    public static final String LOYALTY_POINT = "loyalty_point";
    public static final String REEDEM_POINT = "reedem_point";
    ArrayList history1 = null;
    ArrayList history2 = null;
    JSONArray data_history = null;
    ProgressDialog dialog;
    TextView tv_history;
    String user_id;
    LinearLayout ll_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loyaltyhistory_layout);
        SharedPreferences pref = getSharedPreferences("User-id", MODE_PRIVATE);
        user_id = pref.getString("User_id", null);
        System.out.println("KEY---------->" + user_id);
        new loadPurchaseHistoryData().execute();
        ll_history = (LinearLayout) findViewById(R.id.ll_purchase_history);
        history1 = new ArrayList();
        history2 = new ArrayList();

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
                            String lh_loyalty_points = object.getString(LOYALTY_POINT);
                            String lh_reedem_points = object.getString(REEDEM_POINT);

                            history1.add(lh_loyalty_points);
                            history2.add(lh_reedem_points);


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
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            try {
                for (int j = 0; j <= data_history.length() - 1; j++) {
                    tv_history = new TextView(LoyaltyHistoryActivity.this);
                    params.gravity = Gravity.CENTER;
                    tv_history.setWidth(150);
                    tv_history.setBackgroundColor(getResources().getColor(R.color.notification_background));
                    tv_history.setTextColor(getResources().getColor(R.color.notification_text));
                    tv_history.setPadding(Gravity.CENTER, 10, Gravity.CENTER, 10);
                    tv_history.setTextSize(15);
                    params.setMargins(Gravity.CENTER, 30, 30, 30);
                    tv_history.setTypeface(Typeface.SERIF, Typeface.BOLD);
                    tv_history.setText(String.valueOf("\n" + "Loyalty Point: " + history1.get(j)) + "\n\n" + "Reedem Point: " + history2.get(j) + "\n");
                    tv_history.setLayoutParams(params);
                    ll_history.addView(tv_history);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        }
    }

}
