package com.tritonitsolutions.loyaltydemo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
public class WomenActivity extends Activity {
    public static final String TAG_WOMENS_CATEGORY = "women";
    public static final String TAG_WOMENS_NAME = "p_name";
    public static final String TAG_PRICE_RANGE = "p_prize";
    ListView lv;
    ArrayList<HashMap<String, String>> store_womens;
    JSONArray womens_list = null;
    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.women_layout);
        lv=(ListView)findViewById(R.id.lv_women);
        store_womens = new ArrayList<HashMap<String, String>>();
        new loadWomensCategory().execute();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map = (HashMap<String, String>) lv.getItemAtPosition(position);
                String value = map.get(TAG_WOMENS_NAME);
                Intent intent = new Intent(WomenActivity.this, WomenDetailActivity.class);
                intent.putExtra("women", value);
                startActivity(intent);

                Toast.makeText(getApplicationContext(), "women item" + value, Toast.LENGTH_LONG).show();

            }
        });


    }
    private class loadWomensCategory extends AsyncTask<String, Void, Void> {
        String jsonValue;

        @Override
        protected Void doInBackground(String... params) {
            try {
                ServiceHandler handler = new ServiceHandler();
                jsonValue = handler.makeServiceCall(URL.WOMENS_CATEGORY_URL, ServiceHandler.GET);

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (jsonValue != null) {
                try {
                    JSONObject object = new JSONObject(jsonValue);
                    womens_list=object.getJSONArray(TAG_WOMENS_CATEGORY);
                    for (int i=0;i<womens_list.length();i++){
                        JSONObject jsonObject=womens_list.getJSONObject(i);
                        String men_name=jsonObject.getString(TAG_WOMENS_NAME);
                        String price_range=jsonObject.getString(TAG_PRICE_RANGE);

                        HashMap<String,String> category=new HashMap<String,String>();
                        category.put(TAG_WOMENS_NAME,men_name);
                        category.put(TAG_PRICE_RANGE,price_range);
                        store_womens.add(category);
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
            adapter=new CategoryAdapter(WomenActivity.this,store_womens);
            lv.setAdapter(adapter);
        }
    }
}
