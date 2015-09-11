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
import android.widget.LinearLayout;
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
    ArrayList data_notification1;
    ArrayList data_notification2;
    ArrayList data_notification3;
    ArrayList data_notification4;
    JSONArray list=null;
    public static final String NOTIFICATION="offer";
    public static final String TITLE="title";
    public static final String DESCRIPTION="discription";
    public static final String COUPON_CODE="code";
   public static final String EXPIRE_DATE="expire";
    ProgressDialog dialog;
    TextView tv;
    LinearLayout layout;
    String R_user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);
        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        SharedPreferences pref = getSharedPreferences("User-id", MODE_PRIVATE);
        R_user_id = pref.getString("User_id", null);
        System.out.println("key"+R_user_id);
        new loadNotificationData().execute();
        layout=(LinearLayout)findViewById(R.id.ll_notifi_layout);
        data_notification1=new ArrayList();
        data_notification2=new ArrayList();
        data_notification3=new ArrayList();
        data_notification4=new ArrayList();
        

    }
    private class loadNotificationData extends AsyncTask<String,Void,Void>{
        protected void onPreExecute(){
            dialog=new ProgressDialog(NotificationActivity.this);
            dialog.setMessage("Loading...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();


        }
        @Override
        protected Void doInBackground(String... params) {
            ServiceHandler handler=new ServiceHandler();
            String jsonStr=handler.makeServiceCall(URL.NOTIFICATION_URL+R_user_id,ServiceHandler.GET);
            System.out.println("values----------->" + jsonStr);

            if(jsonStr != null){
                try {

                    JSONObject obj=new JSONObject(jsonStr);
                    list=obj.getJSONArray(NOTIFICATION);
                    for(int i=0;i<list.length();i++){
                        JSONObject ob=list.getJSONObject(i);
                        String notfi_code=ob.getString(COUPON_CODE);
                        String notfi_title=ob.getString(TITLE);
                        String notfi_date=ob.getString(EXPIRE_DATE);
                        String notfi_des=ob.getString(DESCRIPTION);

                         data_notification1.add(notfi_code);
                         data_notification2.add(notfi_title);
                         data_notification3.add(notfi_date);
                         data_notification4.add(notfi_des);

                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
            return null;
        }
        protected void onPostExecute(Void result){
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            try {
                for (int j = 0; j <= list.length() - 1; j++) {
                    tv = new TextView(NotificationActivity.this);
                    params.gravity = Gravity.CENTER;
                    tv.setWidth(150);
                    tv.setBackgroundColor(getResources().getColor(R.color.switch_thumb_material_dark));
                    tv.setTextColor(getResources().getColor(R.color.abc_search_url_text));
                    tv.setPadding(Gravity.CENTER, 10, Gravity.CENTER, 10);
                    tv.setTextSize(15);

                    params.setMargins(Gravity.CENTER, 30, 30, 30);
                    tv.setText(String.valueOf(data_notification2.get(j) + "\n" + data_notification4.get(j) + "\n" + data_notification1.get(j) + "\n" + data_notification3.get(j)));
                    tv.setTypeface(Typeface.SERIF, Typeface.BOLD);
                    tv.setLayoutParams(params);
                    layout.addView(tv);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            dialog.dismiss();

        }
    }

}
