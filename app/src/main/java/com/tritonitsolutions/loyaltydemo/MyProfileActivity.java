package com.tritonitsolutions.loyaltydemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TritonDev on 9/9/2015.
 */
public class MyProfileActivity extends ActionBarActivity {
    Toolbar toolbar;
    EditText email, phone_number, loyalty_no, pwd;
    Button submit;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String user_id;
    public static final String EDIT_PROFILE = "edit_profile";
    public static final String EDIT_PROFILE_VALUE = "value";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_layout);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        email = (EditText) findViewById(R.id.et_edit_email);
        phone_number = (EditText) findViewById(R.id.et_edit_phone);
        loyalty_no = (EditText) findViewById(R.id.et_edit_loyalty_no);
        pwd = (EditText) findViewById(R.id.et_edit_password);
        pwd.setTypeface(Typeface.DEFAULT);
        pwd.setTransformationMethod(new PasswordTransformationMethod());
        preferences = getSharedPreferences("User-id", MODE_PRIVATE);
        user_id = preferences.getString("User_id", null);
        System.out.println("key id--------->" + user_id);
        submit = (Button) findViewById(R.id.btn_edit_profile_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidEmail = false, isValidPhone = false, isValidLoyalty = false, isValidPwd = false;
                if (email.getText().toString().matches("")) {
                    email.setError("Enter eMail address !");
                } else if (!(email.getText().toString().matches(emailPattern))) {
                    email.setError("Enter valid eMail !");
                    isValidEmail = false;

                } else {
                    isValidEmail = true;
                }
                if (phone_number.getText().toString().matches("")) {
                    phone_number.setError("Enter phone number !");
                } else if (!(phone_number.getText().toString().length() == 10)) {
                    phone_number.setError("Enter valid phone number !");
                    isValidPhone = false;
                } else {
                    isValidPhone = true;
                }
                if (loyalty_no.getText().toString().matches("")) {
                    loyalty_no.setError("Enter loyalty number !");

                } else if (!(loyalty_no.getText().toString().length() == 12)) {
                    loyalty_no.setError("Enter valid loyalty number !");
                    isValidLoyalty = false;
                } else {
                    isValidLoyalty = true;
                }
                if (pwd.getText().toString().matches("")) {
                    pwd.setError("Enter password !");
                } else if (!(pwd.getText().toString().length() >= 8)) {
                    pwd.setError("Enter Minimum 8-char !");
                    isValidPwd = false;
                } else {
                    isValidPwd = true;
                }
                if (isValidEmail && isValidPhone && isValidLoyalty && isValidPwd) {
                    new loadChangeProfileData().execute();


                }


            }
        });

    }

    private class loadChangeProfileData extends AsyncTask<String, String, String> {
        String jsonValues;

        @Override
        protected String doInBackground(String... params) {
            String url = URL.EDIT_PROFILE_URL  + user_id + "&email=" + email.getText().toString() + "&mobile=" + phone_number.getText().toString() + "&card_no=" + loyalty_no.getText().toString() + "&password=" + pwd.getText().toString();
            System.out.println("urll---->"+url);
            try {
                ServiceHandler handler = new ServiceHandler();
                jsonValues = handler.makeServiceCall(url, ServiceHandler.GET);
                System.out.println("values------->" + jsonValues);

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(jsonValues);
                JSONArray jsonArray = jsonObject.getJSONArray(EDIT_PROFILE);
                JSONObject object = jsonArray.getJSONObject(0);
                int edit_profile_values = object.getInt(EDIT_PROFILE_VALUE);
                if (edit_profile_values == 1) {
                    Toast.makeText(getApplicationContext(), "Successfully changed your profile.please Re-login", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MyProfileActivity.this, LoginActivity.class);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("User_id");
                    editor.commit();
                    startActivity(intent);
                    email.setText("");
                    phone_number.setText("");
                    loyalty_no.setText("");
                    pwd.setText("");
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid data", Toast.LENGTH_LONG).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}
