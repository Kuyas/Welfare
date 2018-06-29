package com.example.android.welfare.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.android.welfare.MainActivity;
import com.example.android.welfare.R;
import com.example.android.welfare.UserDetails.TextValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    private static final boolean DEBUG = true;
    private static final int otpAcitivyCode = 1;
    private boolean verified = false;
    private Context currentContext;

    private SharedPreferences sharedPreferences;
    private TextInputEditText mobile;
    private TextInputEditText password;
    private TextInputEditText retypepassword;
    private APIService changePasswordAPI;
    private APIService checkMobileIdAPI;
    private String loggedInID;

    private TextValidator validMobile;
    private TextValidator validPassword;
    private TextValidator validRetypePassword;
    private Button sendOTP;
    private Button changePassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        currentContext = ForgotPasswordActivity.this;

        sharedPreferences = this.getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);

        mobile = findViewById(R.id.activity_forgot_password_edittext_mobile);
        password = findViewById(R.id.activity_forgot_password_edittext_password);
        retypepassword = findViewById(R.id.activity_forgot_password_edittext_retype_password);

        final Toolbar toolbar = findViewById(R.id.activity_toolbar);
        toolbar.setTitle(getString(R.string.activity_forgot_password_title));
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

        sendOTP = (Button) findViewById(R.id.activity_forgot_password_button_send_otp);
        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validMobile = new TextValidator(mobile);
                if (DEBUG || validMobile.regexValidator(TextValidator.mobilenumberregex)) {
                    checkMobileIdAPI = APIUtils.getAPIService();
                    checkMobileIdAPI.checkMobile(validMobile.returnText()).enqueue(new Callback<LoginPostData>() {
                        @Override
                        public void onResponse(Call<LoginPostData> call, Response<LoginPostData> response) {
                            int response_code = response.body().getResponseCode();
                            if (response_code == 200) {
                                Intent otpVerification = new Intent(getApplicationContext(), OtpVerificationActivity.class);
                                otpVerification.putExtra("phonenumber", validMobile.returnText());
                                startActivityForResult(otpVerification, otpAcitivyCode);
                            } else {
                                mobile.setError("This mobile number does not exist in database");
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginPostData> call, Throwable t) {
                            mobile.setError("Failed to send request");
                        }
                    });
                }
            }
        });

        changePassword = findViewById(R.id.activity_forgot_password_button_change_login);
        changePassword.setEnabled(false);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verified) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please verify your mobile number", Toast.LENGTH_LONG);
                } else {
                    validPassword = new TextValidator(password);
                    validRetypePassword = new TextValidator(retypepassword);

                    boolean flag = true;
                    if (!validPassword.regexValidator(TextValidator.passwordregex)) {
                        flag = false;
                        password.setError("Please enter a valid password between 8-16 characters");
                    } else if (!validRetypePassword.regexValidator(TextValidator.passwordregex)) {
                        flag = false;
                        retypepassword.setError("Please enter a valid password between 8-16 characters");
                    } else if (!validPassword.returnText().equals(validRetypePassword.returnText())) {
                        flag = false;
                        retypepassword.setError("Password and retyped password are not same");
                    } else {
                        changePasswordAPI = APIUtils.getAPIService();
                        changePasswordAPI.changePassword(validMobile.returnText(), validPassword.returnText()).enqueue(new Callback<LoginPostData>() {
                            @Override
                            public void onResponse(Call<LoginPostData> call, Response<LoginPostData> response) {
                                int response_code = response.body().getResponseCode();
                                if (response_code==200) {
                                    if (DEBUG) Toast.makeText(ForgotPasswordActivity.this, "changed password ", Toast.LENGTH_LONG).show();
                                    sharedPreferences.edit().putString("loggedInID", loggedInID).apply();
                                    Intent mainactivity = new Intent(currentContext, MainActivity.class);
                                    startActivity(mainactivity);
                                } else {
                                    Toast.makeText(ForgotPasswordActivity.this, "Request gave erroneous response", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<LoginPostData> call, Throwable t) {
                                Toast.makeText(ForgotPasswordActivity.this, "Request not completed please try again", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case (otpAcitivyCode): {
                if (resultCode == Activity.RESULT_OK) {
                    verified = true;
                    mobile.setFocusable(false);
                    sendOTP.setEnabled(false);
                    changePassword.setEnabled(true);
                    break;
                }
            }
        }
    }
}
