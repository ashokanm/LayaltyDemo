package com.tritonitsolutions.loyaltydemo;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;


import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Babu on 8/19/2015.
 */
public class StoreActivity extends ActionBarActivity {
    Toolbar toolbar;
    public static final String TAG_STORE="store";
    public static final String TAG_STORE_NAME="p_name";
    public static final String TAG_STORE_IMAGE="p_img";
    ArrayList<HashMap<String,String>>store_list;
    GridView gv;
    ProgressDialog dialog;
    JSONArray list=null;
    StoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_layout);
        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        gv=(GridView)findViewById(R.id.gridView);
        store_list=new ArrayList<HashMap<String, String>>();
        new loadStoreData().execute();
    }
    private class loadStoreData extends AsyncTask<Void,Void,Void>{
        protected void onPreExecute(){
            dialog=new ProgressDialog(StoreActivity.this);
            dialog.setMessage("Loading...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
         ServiceHandler handler=new ServiceHandler();
         String jsonStr=handler.makeServiceCall(URL.STORE_DETAILS,ServiceHandler.GET);
            System.out.println("values"+jsonStr);
         if(jsonStr != null){
             try {
                 JSONObject obj = new JSONObject(jsonStr);
                 list=obj.getJSONArray(TAG_STORE);
                 for(int i=0;i<list.length();i++){
                     JSONObject ob=list.getJSONObject(i);
                     String st_name=ob.getString(TAG_STORE_NAME);
                     String st_image=ob.getString(TAG_STORE_IMAGE);

                     HashMap<String,String> stores=new HashMap<String,String>();
                     stores.put(TAG_STORE_NAME,st_name);
                     stores.put(TAG_STORE_IMAGE,st_image);
                     store_list.add(stores);

                 }

             }catch (JSONException e){
                 e.printStackTrace();
             }
         }


            return null;
        }
        protected void onPostExecute(Void unused) {
            adapter = new StoreAdapter(StoreActivity.this, store_list);
            gv.setAdapter(adapter);
            dialog.dismiss();
        }
    }





}
