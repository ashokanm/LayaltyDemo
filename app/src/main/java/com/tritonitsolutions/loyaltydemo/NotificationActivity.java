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
import android.widget.ImageView;
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
 * Created by TritonDev on 9/9/2015.
 */
public class NotificationActivity extends ActionBarActivity {
    Toolbar toolbar;
    ArrayList<HashMap<String,String>> notification_store;
    JSONArray list=null;
    public static final String NOTIFICATION="offer";
  // public static final String NOTIFICATION_ID = "id";
    public static final String TITLE="title";
    public static final String DESCRIPTION="discription";
    //public static final String COUPON_CODE="code";
    public static final String EXPIRE_DATE="expire";
    NotificationAdapter adapter;
    ListView lv;

    ProgressDialog dialog;

    LinearLayout layout;
    String R_user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);
        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notification");
        SharedPreferences pref = getSharedPreferences("User-id", MODE_PRIVATE);
        R_user_id = pref.getString("User_id", null);
        System.out.println("key"+R_user_id);
        notification_store=new ArrayList<HashMap<String, String>>();
        lv=(ListView)findViewById(R.id.lv_notification);
        new loadNotificationData().execute();

    }
    private class loadNotificationData extends AsyncTask<String,Void,Void>{
        String jsonStr;
        protected void onPreExecute(){
            dialog=new ProgressDialog(NotificationActivity.this);
            dialog.setMessage("Loading...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();


        }
        @Override
        protected Void doInBackground(String... params) {
            try {
                ServiceHandler handler=new ServiceHandler();
                jsonStr=handler.makeServiceCall(URL.NOTIFICATION_URL+R_user_id,ServiceHandler.GET);
                System.out.println("values----------->" + jsonStr);
            }catch (Exception e){

                e.printStackTrace();
            }

            if(jsonStr != null){
                try {

                    JSONObject obj=new JSONObject(jsonStr);
                    list=obj.getJSONArray(NOTIFICATION);
                    for(int i=0;i<list.length();i++){
                        JSONObject ob=list.getJSONObject(i);
                       // String notofi_id=ob.getString(NOTIFICATION_ID);
                        //String notfi_code=ob.getString(COUPON_CODE);
                        String notfi_title=ob.getString(TITLE);
                        String notfi_date=ob.getString(EXPIRE_DATE);
                        String notfi_des=ob.getString(DESCRIPTION);

                        HashMap<String,String>notification= new HashMap<String,String>();
                       // notification.put(NOTIFICATION_ID,notofi_id);
                        notification.put(TITLE,notfi_title);
                        notification.put(DESCRIPTION,notfi_des);
                        notification.put(EXPIRE_DATE,notfi_date);
                       // notification.put(COUPON_CODE,notfi_code);
                        notification_store.add(notification);


                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
            return null;
        }
        protected void onPostExecute(Void result){
            adapter=new NotificationAdapter(NotificationActivity.this,notification_store);
            lv.setAdapter(adapter);
             dialog.dismiss();

        }
    }

}
