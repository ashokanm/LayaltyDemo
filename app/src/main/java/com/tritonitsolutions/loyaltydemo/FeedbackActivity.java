package com.tritonitsolutions.loyaltydemo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tritonitsolutions.layaltydemo.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by TritonDev on 8/28/2015.
 */
public class FeedbackActivity extends ActionBarActivity {
    Toolbar toolbar;
    EditText name,email,ph_no,loyalty_card_no,description;
    Button submit;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_layout);
        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        name=(EditText)findViewById(R.id.et_name);
        email=(EditText)findViewById(R.id.et_email);
        ph_no=(EditText)findViewById(R.id.et_ph_no);
        loyalty_card_no=(EditText)findViewById(R.id.et_loyalty_no);
        description=(EditText)findViewById(R.id.et_description);
        submit=(Button)findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidName,isValidEmail=false,isValidPhone = false,isValidCardno = false,isValidDescription;
                if(name.getText().toString().matches("")){
                    name.setError("Enter name !");
                    isValidName=false;
                }else {
                    isValidName=true;
                }
                if(email.getText().toString().matches("")){
                    email.setError("Enter eMail !");
                }
                else if(!(email.getText().toString().matches(emailPattern))){
                    email.setError("Enter valid eMail !");
                    isValidEmail=false;

                }else {
                    isValidEmail=true;
                }
                if(ph_no.getText().toString().matches("")){
                    ph_no.setError("Enter mobile number !");
                }
                else if(!(ph_no.getText().toString().length()==10)){
                    ph_no.setError("Enter 10-digit number !");
                    isValidPhone=false;
                }
                else {
                    isValidPhone=true;
                }
                if(loyalty_card_no.getText().toString().matches("")){
                    loyalty_card_no.setError("Enter card number !");

                }
                else if(!(loyalty_card_no.getText().toString().length()==12)){
                    loyalty_card_no.setError("Enter 12-digit card number !");

                    isValidCardno=false;

                }else {
                    isValidCardno=true;
                }
                if(description.getText().toString().matches("")){
                    description.setError("Enter your queries !");
                    isValidDescription=false;
                }else {
                    isValidDescription=true;
                }
                if(isValidName && isValidEmail && isValidPhone && isValidCardno && isValidDescription){
                    new PostDataAsynTask().execute();
                    Toast.makeText(getApplicationContext(),"Successfully sent your feedback.We will shortly get back to you",Toast.LENGTH_LONG).show();

                }



            }
        });
    }

    public class PostDataAsynTask extends AsyncTask<String,String,String>{

    protected void onPreExecute(){
        super.onPreExecute();
    }
        @Override
        protected String doInBackground(String... params) {
            try {
                postData();
            }catch (NullPointerException e){
                e.printStackTrace();

            }catch (Exception ex){
                ex.printStackTrace();
            }
            return null;
        }
  protected void onPostExecute(String lengthOfFile){

  }
        private void postData(){
            String url="http://whitehousecbe.in/demo/loyalty/admin/storefeedback.php?name="+name.getText().toString()+"&email="+email.getText().toString()+"&phone="+ph_no.getText().toString()+"&loyaltyid="+loyalty_card_no.getText().toString()+"&feedback="+ URLEncoder.encode(description.getText().toString());
            HttpClient client=new DefaultHttpClient();
            HttpGet get= new HttpGet(url);
            try {
                HttpResponse response=client.execute(get);
                HttpEntity entity=response.getEntity();
                if(entity !=null){
                    String respons= EntityUtils.toString(entity).toString().trim();
                    System.out.println("Respose-------->"+respons);
                }

            }catch (NullPointerException e){
                e.printStackTrace();

            } catch (IOException ex){
                ex.printStackTrace();
            }

        }

    }


}
