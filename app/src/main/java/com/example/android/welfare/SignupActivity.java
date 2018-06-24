package com.example.android.welfare;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class SignupActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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


        Button SignupButton = findViewById(R.id.activity_signup_button_signup);
        Button SendOTPButton = findViewById(R.id.activity_signup_button_send_otp);

        SignupButton.setEnabled(false);

        final TextInputEditText mobile = findViewById(R.id.activity_signup_edittext_mobile);
        final TextInputEditText password = findViewById(R.id.activity_signup_edittext_password);
        final TextInputEditText retypePassword = findViewById(R.id.activity_signup_edittext_retype_password);
        final TextInputEditText otp = findViewById(R.id.activity_signup_edittext_otp);

        SendOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextValidator validMobile = new TextValidator(mobile);
                TextValidator validPassword = new TextValidator(password);
                TextValidator validRetypePassword = new TextValidator(retypePassword);
                TextValidator validOTP = new TextValidator(otp);

                boolean flag = true;
                if (!validMobile.regexValidator("\\d{10}")) {
                    flag = false;
                    mobile.setError("Please enter valid 10 digit mobile number");
                } else if (!validPassword.regexValidator("[a-zA-Z0-9]{8,16}")) {
                    flag = false;
                    password.setError("Please enter a valid password between 8-16 characters");
                } else if (!validRetypePassword.regexValidator("[a-zA-Z]{8,16}")) {
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
                    startActivity(otpVerification);

                    // TODO: send data to server on verification of OTP
                }
            }
        });

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
