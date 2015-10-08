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
public class WomenDetailActivity extends ActionBarActivity {
    ProgressDialog dialog;
    String value;
    JSONArray womens_detail = null;
    ArrayList<HashMap<String, String>> store_womensdetail;
    DetailAdapter adapter;
    ListView lv;
    public static final String TAG_WOMENS_DETAILS = "";
    public static final String TAG_WOMENS_DETAILS_IMAGE = "";
    public static final String TAG_WOMENS_DETAILS_NAME = "";
    public static final String TAG_WOMENS_DETAILS_SIZE = "";
    public static final String TAG_WOMENS_DETAILS_PRICE = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.womensdetail_layout);
        lv = (ListView) findViewById(R.id.lv_womens_detail);
        value = getIntent().getStringExtra("mens");
        System.out.println("Data----->" + value);
        store_womensdetail = new ArrayList<HashMap<String, String>>();
        new loadMensDetailData().execute();

    }

    private class loadMensDetailData extends AsyncTask<String, Void, Void> {
        String jsonValue;

        protected void onPreExecute() {
            dialog = new ProgressDialog(WomenDetailActivity.this);
            dialog.setMessage("Loading...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                ServiceHandler handler = new ServiceHandler();
                jsonValue = handler.makeServiceCall(URL.WOMENS_DETAIL_URL + value, ServiceHandler.GET);

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (jsonValue != null) {
                try {
                    JSONObject object = new JSONObject(jsonValue);
                    womens_detail = object.getJSONArray(TAG_WOMENS_DETAILS);
                    for (int i = 0; i < womens_detail.length(); i++) {
                        JSONObject jsonObject = womens_detail.getJSONObject(i);
                        String pro_image = jsonObject.getString(TAG_WOMENS_DETAILS_IMAGE);
                        String pro_name = jsonObject.getString(TAG_WOMENS_DETAILS_NAME);
                        String pro_size = jsonObject.getString(TAG_WOMENS_DETAILS_SIZE);
                        String pro_price = jsonObject.getString(TAG_WOMENS_DETAILS_PRICE);

                        HashMap<String, String> men_product = new HashMap<String, String>();
                        men_product.put(TAG_WOMENS_DETAILS_IMAGE, pro_image);
                        men_product.put(TAG_WOMENS_DETAILS_NAME, pro_name);
                        men_product.put(TAG_WOMENS_DETAILS_SIZE, pro_size);
                        men_product.put(TAG_WOMENS_DETAILS_PRICE, pro_price);
                        store_womensdetail.add(men_product);
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
            adapter = new DetailAdapter(WomenDetailActivity.this, store_womensdetail);
            lv.setAdapter(adapter);


        }
    }
}
