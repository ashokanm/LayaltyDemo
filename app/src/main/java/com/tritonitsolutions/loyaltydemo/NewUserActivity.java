package com.tritonitsolutions.loyaltydemo;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by TritonDev on 11/9/2015.
 */
public class NewUserActivity extends ActionBarActivity {
    Toolbar toolbar;
    EditText reg_name, reg_email, reg_ph_no, reg_lcn, reg_pwd, reg_confirm_pwd;
    Spinner gender;
    Button reg_submit;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Intent intent;
    String[] str_gender={"Male","Female"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_layout);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        reg_name = (EditText) findViewById(R.id.et_newuser_name);
        reg_email = (EditText) findViewById(R.id.et_newuser_email);
        reg_ph_no = (EditText) findViewById(R.id.et_newuser_ph_no);
        gender=(Spinner)findViewById(R.id.et_newuser_gender);
        ArrayAdapter<String>sp_gender=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,str_gender);
        gender.setAdapter(sp_gender);
       // reg_lcn = (EditText) findViewById(R.id.et_newuser_loyalty_no);
        reg_pwd = (EditText) findViewById(R.id.et_newuser_pwd);
        reg_pwd.setTypeface(Typeface.DEFAULT);
        reg_pwd.setTransformationMethod(new PasswordTransformationMethod());
        reg_confirm_pwd = (EditText) findViewById(R.id.et_newuser_confirm_pwd);
        reg_confirm_pwd.setTypeface(Typeface.DEFAULT);
        reg_confirm_pwd.setTransformationMethod(new PasswordTransformationMethod());
        reg_submit = (Button) findViewById(R.id.btn_newuser_submit);
        reg_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidName, isValidEmail = false, isValidPhone = false, isValidCardno = false, isValidPassword = false, isValidConfirmPassword = false;
                if (reg_name.getText().toString().matches("")) {
                    reg_name.setError("Enter name !");
                    isValidName = false;
                } else {
                    isValidName = true;
                }
                if (reg_email.getText().toString().matches("")) {
                    reg_email.setError("Enter eMail !");
                } else if (!(reg_email.getText().toString().matches(emailPattern))) {
                    reg_email.setError("Enter valid eMail !");
                    isValidEmail = false;

                } else {
                    isValidEmail = true;
                }
                if (reg_ph_no.getText().toString().matches("")) {
                    reg_ph_no.setError("Enter mobile number !");
                } else if (!(reg_ph_no.getText().toString().length() == 10)) {
                    reg_ph_no.setError("Enter 10-digit number !");
                    isValidPhone = false;
                } else {
                    isValidPhone = true;
                }
//                if (reg_lcn.getText().toString().matches("")) {
//                    reg_lcn.setError("Enter card number !");
//
//                } else if (!(reg_lcn.getText().toString().length() == 12)) {
//                    reg_lcn.setError("Enter 12-digit card number !");
//
//                    isValidCardno = false;
//
//                } else {
//                    isValidCardno = true;
//                }
                if (reg_pwd.getText().toString().matches("")) {
                    reg_pwd.setError("Enter your password !");

                } else if (!(reg_pwd.getText().toString().length() >= 8)) {
                    reg_pwd.setError("password minimum 8-char !");
                    isValidPassword = false;
                } else {
                    isValidPassword = true;
                }

                if (!(reg_confirm_pwd.getText().toString().equals(reg_pwd.getText().toString()))) {
                    reg_confirm_pwd.setError("Password Mismatch !");
                    isValidConfirmPassword = false;
                } else {
                    isValidConfirmPassword = true;
                }


                if (isValidName && isValidEmail && isValidPhone && isValidPassword && isValidConfirmPassword) {
                    new newUserAsynTask().execute();

                }

            }
        });

    }

    public class newUserAsynTask extends AsyncTask<String, String, String> {
        String jsonStr;

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = URL.NEW_USER_URL +"name=" + reg_name.getText().toString() + "&email=" + reg_email.getText().toString() + "&mobile=" + reg_ph_no.getText().toString() + "&cardnumber=" + reg_lcn.getText().toString() + "&password=" + reg_pwd.getText().toString();
            System.out.println("URL VALUES-------->"+url);
            ServiceHandler handler = new ServiceHandler();
            jsonStr = handler.makeServiceCall(url, ServiceHandler.GET);

            return null;
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(jsonStr);
                JSONArray jArray = object.getJSONArray("check");
                JSONObject jasonObject = jArray.getJSONObject(0);
                int returnedResult = jasonObject.getInt("value");

                if (returnedResult == 1) {
                    Toast.makeText(getApplicationContext(), "Card number already registered", Toast.LENGTH_LONG).show();

                } else if (returnedResult == 2) {
                    Toast.makeText(getApplicationContext(), "eMail already registered", Toast.LENGTH_LONG).show();


                } else if (returnedResult == 3) {
                    Toast.makeText(getApplicationContext(), "Phone number already registered", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(getApplicationContext(), "Successfully registered", Toast.LENGTH_LONG).show();
                    intent=new Intent(NewUserActivity.this,MainActivity.class);
                    startActivity(intent);
                    reg_name.setText("");
                    reg_email.setText("");
                    reg_ph_no.setText("");
                    reg_lcn.setText("");
                    reg_pwd.setText("");
                    reg_confirm_pwd.setText("");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }
}



