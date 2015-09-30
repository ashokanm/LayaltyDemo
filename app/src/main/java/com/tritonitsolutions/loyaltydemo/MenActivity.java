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
public class MenActivity extends Activity {
    public static final String TAG_MENS_CATEGORY = "men";
    public static final String TAG_MENS_NAME = "p_name";
    public static final String TAG_PRICE_RANGE = "p_prize";
    ListView lv;
    ArrayList<HashMap<String, String>> store_mens;
    JSONArray mens_list = null;
    MensAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.men_layout);
        lv=(ListView)findViewById(R.id.lv_mens);
        store_mens = new ArrayList<HashMap<String, String>>();
        new loadMensCategory().execute();

    }

    private class loadMensCategory extends AsyncTask<String, Void, Void> {
        String jsonValue;

        @Override
        protected Void doInBackground(String... params) {
            try {
                ServiceHandler handler = new ServiceHandler();
                jsonValue = handler.makeServiceCall(URL.MENS_CATEGORY_URL, ServiceHandler.GET);

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (jsonValue != null) {
                try {
                    JSONObject object = new JSONObject(jsonValue);
                    mens_list=object.getJSONArray(TAG_MENS_CATEGORY);
                    for (int i=0;i<mens_list.length();i++){
                        JSONObject jsonObject=mens_list.getJSONObject(i);
                        String men_name=jsonObject.getString(TAG_MENS_NAME);
                        String price_range=jsonObject.getString(TAG_PRICE_RANGE);

                        HashMap<String,String> category=new HashMap<String,String>();
                        category.put(TAG_MENS_NAME,men_name);
                        category.put(TAG_PRICE_RANGE,price_range);
                        store_mens.add(category);
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
            adapter=new MensAdapter(MenActivity.this,store_mens);
            lv.setAdapter(adapter);
        }
    }
}
