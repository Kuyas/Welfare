package com.example.android.welfare.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.android.welfare.MainActivity;
import com.example.android.welfare.NetworkStatus;
import com.example.android.welfare.R;
import com.example.android.welfare.UserDetails.TextValidator;

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
                if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                    TextValidator validMobile = new TextValidator(mobile);
                    TextValidator validPassword = new TextValidator(password);
                    TextValidator validRetypePassword = new TextValidator(retypePassword);
                    TextValidator validOTP = new TextValidator(otp);

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
                    } else if (!validPassword.returnText().equals(validRetypePassword.returnText())) {
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
                } else {
                    LinearLayout linearLayout = findViewById(R.id.layout_activity_signup);
                    Snackbar noConnectionSnackbar = Snackbar.make(linearLayout,
                            getString(R.string.internet_connection_error_message), Snackbar.LENGTH_LONG);
                    noConnectionSnackbar.show();
                }
            }
        });

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    LinearLayout linearLayout = findViewById(R.id.layout_activity_signup);
                    Snackbar noConnectionSnackbar = Snackbar.make(linearLayout,
                            getString(R.string.internet_connection_error_message), Snackbar.LENGTH_LONG);
                    noConnectionSnackbar.show();
                }
            }
        });
    }
}
