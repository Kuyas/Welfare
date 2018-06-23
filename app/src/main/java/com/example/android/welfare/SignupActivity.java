package com.example.android.welfare;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;

public class SignupActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button SignupButton = (Button) findViewById(R.id.activity_signup_button_signup);
        Button SendOTPButton = (Button) findViewById(R.id.activity_signup_button_send_otp);

        SignupButton.setEnabled(false);

        final TextInputEditText mobile = (TextInputEditText) findViewById(R.id.activity_signup_edittext_mobile);
        final TextInputEditText password = (TextInputEditText) findViewById(R.id.activity_signup_edittext_password);
        final TextInputEditText retypePassword = (TextInputEditText) findViewById(R.id.activity_signup_edittext_retype_password);
        final TextInputEditText otp = (TextInputEditText) findViewById(R.id.activity_signup_edittext_otp);

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

        Button signupbutton = (Button) findViewById(R.id.activity_signup_button_signup);
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
