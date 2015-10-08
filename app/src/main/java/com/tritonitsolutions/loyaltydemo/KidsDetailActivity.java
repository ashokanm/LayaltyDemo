package com.tritonitsolutions.loyaltydemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TritonDev on 8/10/2015.
 */
public class KidsDetailActivity extends ActionBarActivity {
    ProgressDialog dialog;
    String value;
    JSONArray kids_detail = null;
    ArrayList<HashMap<String, String>> store_kidsdetail;
    DetailAdapter adapter;
    ListView lv;
    public static final String TAG_KIDS_DETAILS = "";
    public static final String TAG_KIDS_DETAILS_IMAGE = "";
    public static final String TAG_KIDS_DETAILS_NAME = "";
    public static final String TAG_KIDS_DETAILS_SIZE = "";
    public static final String TAG_KIDS_DETAILS_PRICE = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kidsdetail_layout);
        lv = (ListView) findViewById(R.id.lv_kids_detail);
        value = getIntent().getStringExtra("mens");
        System.out.println("Data----->" + value);
        store_kidsdetail = new ArrayList<HashMap<String, String>>();
        new loadMensDetailData().execute();

    }

    private class loadMensDetailData extends AsyncTask<String, Void, Void> {
        String jsonValue;

        protected void onPreExecute() {
            dialog = new ProgressDialog(KidsDetailActivity.this);
            dialog.setMessage("Loading...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                ServiceHandler handler = new ServiceHandler();
                jsonValue = handler.makeServiceCall(URL.KIDS_DETAIL_URL + value, ServiceHandler.GET);

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (jsonValue != null) {
                try {
                    JSONObject object = new JSONObject(jsonValue);
                    kids_detail = object.getJSONArray(TAG_KIDS_DETAILS);
                    for (int i = 0; i < kids_detail.length(); i++) {
                        JSONObject jsonObject = kids_detail.getJSONObject(i);
                        String pro_image = jsonObject.getString(TAG_KIDS_DETAILS_IMAGE);
                        String pro_name = jsonObject.getString(TAG_KIDS_DETAILS_NAME);
                        String pro_size = jsonObject.getString(TAG_KIDS_DETAILS_SIZE);
                        String pro_price = jsonObject.getString(TAG_KIDS_DETAILS_PRICE);

                        HashMap<String, String> men_product = new HashMap<String, String>();
                        men_product.put(TAG_KIDS_DETAILS_IMAGE, pro_image);
                        men_product.put(TAG_KIDS_DETAILS_NAME, pro_name);
                        men_product.put(TAG_KIDS_DETAILS_SIZE, pro_size);
                        men_product.put(TAG_KIDS_DETAILS_PRICE, pro_price);
                        store_kidsdetail.add(men_product);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter = new DetailAdapter(KidsDetailActivity.this, store_kidsdetail);
            lv.setAdapter(adapter);


        }
    }
}
