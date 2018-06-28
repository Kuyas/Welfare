package com.example.android.welfare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.welfare.DatabaseConnection.APIService;
import com.example.android.welfare.DatabaseConnection.APIUtils;
import com.example.android.welfare.DatabaseConnection.ResponseClasses.LoginPostData;
import com.example.android.welfare.Login.OtpVerificationActivity;
import com.example.android.welfare.UserDetails.TextValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity{
    private static final int otpAcitivyCode = 1;
    private APIService registerUsingApi;
    private TextValidator validMobile;
    private TextValidator validPassword;
    private TextValidator validRetypePassword;
    private TextValidator validOTP;
    private static final boolean DEBUG = true;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sharedPreferences = this.getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);

        final Toolbar toolbar = findViewById(R.id.activity_toolbar);
        toolbar.setTitle(getString(R.string.activity_signup_title));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(getDrawable(R.drawable.ic_arrow_back_black_24dp));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Button SendOTPButton = findViewById(R.id.activity_signup_button_send_otp);

        final TextInputEditText mobile = findViewById(R.id.activity_signup_edittext_mobile);
        final TextInputEditText password = findViewById(R.id.activity_signup_edittext_password);
        final TextInputEditText retypePassword = findViewById(R.id.activity_signup_edittext_retype_password);

        SendOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validMobile = new TextValidator(mobile);
                validPassword = new TextValidator(password);
                validRetypePassword = new TextValidator(retypePassword);

                boolean flag = true;
                if (!validMobile.regexValidator(TextValidator.mobilenumberregex)) {
                    flag = false;
                    mobile.setError("Please enter valid 10 digit mobile number");
                } else if (!validPassword.regexValidator(TextValidator.passwordregex)) {
                    flag = false;
                    password.setError("Please enter a valid password between 8-16 characters");
                } else if (!validRetypePassword.regexValidator(TextValidator.passwordregex)) {
                    flag = false;
                    retypePassword.setError("Please enter a valid password between 8-16 characters");
                } else if (!validPassword.returnText().equals(validRetypePassword.returnText())){
                    flag = false;
                    retypePassword.setError("Password and retyped password are not same");
                } else {
                    //
                    String phoneNumber = validMobile.returnText();
                    Intent otpVerification = new Intent(getApplicationContext(), OtpVerificationActivity.class);
                    otpVerification.putExtra("phonenumber", phoneNumber);
                    startActivityForResult(otpVerification, otpAcitivyCode);
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case (otpAcitivyCode) : {
                if (resultCode == Activity.RESULT_OK) {
                    registerUsingApi = APIUtils.getAPIService();
                    registerUsingApi.registerUser(validMobile.returnText(), validPassword.returnText()).enqueue(new Callback<LoginPostData>() {
                        @Override
                        public void onResponse(Call<LoginPostData> call, Response<LoginPostData> response) {
                            long response_code = response.body().getResponseCode();
                            if (response_code==1) {
                                sharedPreferences.edit().putString("loggedInID", response.body().getId());
                                if (DEBUG) Toast.makeText(SignupActivity.this, "registered in ID is " + response.body().getId(), Toast.LENGTH_LONG).show();
                                Intent mainActivity = new Intent(SignupActivity.this, MainActivity.class);
                                startActivity(mainActivity);
                            } else {
                                Toast.makeText(SignupActivity.this, "Request gave erroneous response", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginPostData> call, Throwable t) {
                            Toast.makeText(SignupActivity.this, "Failed to make request", Toast.LENGTH_LONG).show();
                        }
                    });

                }
                break;
            }
        }
    }
}
