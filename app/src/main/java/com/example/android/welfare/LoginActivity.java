package com.example.android.welfare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final boolean DEBUG = true;
    private SharedPreferences sharedPreferences;
    private String mobileString, passwordString;
    private TextValidator mobileValidator, passwordValidator;
    private boolean entry_flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);

        // Login if mobile and password is already stored
        if (!sharedPreferences.getString("mobile", "").isEmpty() &&
                !sharedPreferences.getString("password", "").isEmpty() &&
                DEBUG) {
            LoginWithDetails(sharedPreferences.getString("mobile", ""), sharedPreferences.getString("password", ""));
        }

        setContentView(R.layout.activity_login);

        final Toolbar toolbar = findViewById(R.id.activity_toolbar);
        toolbar.setTitle(getString(R.string.activity_login_title));

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final TextInputEditText mobile,password;
        Button login;

        mobile = findViewById(R.id.activity_login_edittext_mobile);
        password = findViewById(R.id.activity_login_edittext_password);


        Button signupbutton = findViewById(R.id.activity_login_button_signup);
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        login = findViewById(R.id.acitvity_login_button_signin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entry_flag = true;
                mobileValidator = new TextValidator(mobile);
                passwordValidator = new TextValidator(password);
                if (!mobileValidator.regexValidator(TextValidator.mobilenumberregex)) {
                    entry_flag = false;
                    mobile.setError("Please enter valid 10 digit mobile number");
                } else if (!passwordValidator.regexValidator(TextValidator.passwordregex)) {
                    entry_flag = false;
                    password.setError("Please enter a valid password between 8-16 characters");
                } else {
                    mobileString = mobileValidator.returnText();
                    passwordString = passwordValidator.returnText();
                }
                if (entry_flag || DEBUG) {
                    LoginWithDetails(mobileValidator.returnText(), passwordValidator.returnText());
                }
            }
        });
    }

    private void LoginWithDetails(String mobile, String password) {
        AttemptLogin attemptLogin = new AttemptLogin();
        attemptLogin.execute(mobile, password);
    }

    @Override
    public void onBackPressed() {
        Intent closeAppIntent = new Intent(Intent.ACTION_MAIN);
        closeAppIntent.addCategory(Intent.CATEGORY_HOME);
        closeAppIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(closeAppIntent);
    }


    private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override
        protected JSONObject doInBackground(String... args)  {

            String password = args[1];
            String mobile= args[0];

//            HashMap<String, String> params = new HashMap<>();
//            params.put("mobile", mobile);
//            params.put("password", password);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair( "mobile_number", mobile));
            params.add(new BasicNameValuePair("password", password));

            JSONObject json = JSONParser.makeHttpRequest("http://192.168.43.56/Welfare-backend/login_user.php", "POST", params);

            return json;

//            return null;
        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                if (result != null) {
                    Toast.makeText(getApplicationContext(),result.getString("code"),Toast.LENGTH_LONG).show();
                    if (result.get("code").toString().equals("200")) {
                        sharedPreferences.edit().putString("loggedInID", result.get("loggedInID").toString()).apply();
                    }

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Mobile or Password", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }



}
