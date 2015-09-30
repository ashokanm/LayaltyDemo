package com.tritonitsolutions.loyaltydemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TritonDev on 28/9/2015.
 */
public class KidsActivity extends Activity {
    public static final String TAG_KIDS_CATEGORY = "kids";
    public static final String TAG_KIDS_NAME = "p_name";
    public static final String TAG_PRICE_RANGE = "p_prize";
    ListView lv;
    ArrayList<HashMap<String, String>> store_kids;
    JSONArray kids_list = null;
    KidsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kids_layout);
        lv=(ListView)findViewById(R.id.lv_kids);
        store_kids = new ArrayList<HashMap<String, String>>();
        new loadKidsCategory().execute();
    }
    private class loadKidsCategory extends AsyncTask<String, Void, Void> {
        String jsonValue;

        @Override
        protected Void doInBackground(String... params) {
            try {
                ServiceHandler handler = new ServiceHandler();
                jsonValue = handler.makeServiceCall(URL.KIDS_CATEGORY_URL, ServiceHandler.GET);

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (jsonValue != null) {
                try {
                    JSONObject object = new JSONObject(jsonValue);
                    kids_list=object.getJSONArray(TAG_KIDS_CATEGORY);
                    for (int i=0;i<kids_list.length();i++){
                        JSONObject jsonObject=kids_list.getJSONObject(i);
                        String kids_name=jsonObject.getString(TAG_KIDS_NAME);
                        String price_range=jsonObject.getString(TAG_PRICE_RANGE);

                        HashMap<String,String> category=new HashMap<String,String>();
                        category.put(TAG_KIDS_NAME,kids_name);
                        category.put(TAG_PRICE_RANGE,price_range);
                        store_kids.add(category);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
            return null;
        }
        protected  void onPostExecute(Void result){
            super.onPostExecute(result);
            adapter=new KidsAdapter(KidsActivity.this,store_kids);
            lv.setAdapter(adapter);
        }
    }
}
