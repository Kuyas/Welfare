package com.example.android.welfare;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.welfare.JSONParser;
import org.apache.http.message.BasicNameValuePair;
import org.json.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import org.json.simple.parser.ParseException;


import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final EditText username,password;
        Button login;
        String url = "http://10.0.3.2/test_android/index.php";

        JSONParser jsonParser=new JSONParser();

        int i=0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username =(EditText)findViewById(R.id.activity_login_edittext_mobile);
        password = (EditText)findViewById(R.id.activity_login_edittext_password);


//        Button loginbutton = (Button) findViewById(R.id.acitvity_login_button_login);
//        loginbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
        Button signupbutton = (Button) findViewById(R.id.activity_login_button_signup);
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        login = (Button)findViewById(R.id.acitvity_login_button_signin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttemptLogin attemptLogin= new AttemptLogin();
                attemptLogin.execute(username.getText().toString(),password.getText().toString(),"");
            }

        });
    }

    private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args)  {



            String email = args[2];
            String password = args[1];
            String name= args[0];

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("username", name));
            params.add(new BasicNameValuePair("password", password));
            if(email.length()>0)
                params.add(new BasicNameValuePair("email",email));

            JSONObject json = JSONParser.makeHttpRequest("http://192.168.43.56/test_android/index.php", "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                if (result != null) {
                    Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                   startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }



}
