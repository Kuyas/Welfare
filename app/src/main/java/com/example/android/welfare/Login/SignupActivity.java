package com.example.android.welfare.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.welfare.databaseconnection.APIService;
import com.example.android.welfare.databaseconnection.APIUtils;
import com.example.android.welfare.databaseconnection.DisplayErrorMessage;
import com.example.android.welfare.databaseconnection.responseclasses.AuthenticationData;
import com.example.android.welfare.MainActivity;
import com.example.android.welfare.NetworkStatus;
import com.example.android.welfare.R;
import com.example.android.welfare.userdetails.TextValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity{
    private static final int otpAcitivyCode = 1;

    private TextValidator validMobile;
    private TextValidator validPassword;
    private TextValidator validRetypePassword;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sharedPreferences = getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);

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
                if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
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
                    } else if (!validPassword.returnText().equals(validRetypePassword.returnText())) {
                        flag = false;
                        retypePassword.setError("Password and retyped password are not same");
                    } else {
                        //
                        String phoneNumber = validMobile.returnText();
                        Intent otpVerification = new Intent(getApplicationContext(), OtpVerificationActivity.class);
                        otpVerification.putExtra("phonenumber", phoneNumber);
                        startActivityForResult(otpVerification, otpAcitivyCode);
                    }
                } else {
                    LinearLayout linearLayout = findViewById(R.id.layout_activity_signup);
                    Snackbar noConnectionSnackbar = Snackbar.make(linearLayout,
                            getString(R.string.internet_connection_error_message), Snackbar.LENGTH_LONG);
                    noConnectionSnackbar.show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case (otpAcitivyCode): {
                if (resultCode == Activity.RESULT_OK) {
                    APIService registerUSerAPI = APIUtils.getAPIService();
                    registerUSerAPI.registerUser(validMobile.returnText(), validPassword.returnText()).enqueue(new Callback<AuthenticationData>() {
                        @Override
                        public void onResponse(Call<AuthenticationData> call, Response<AuthenticationData> response) {
                            int response_code = response.body().getResponseCode();
                            if (response_code == 200) {
                                Toast.makeText(SignupActivity.this, DisplayErrorMessage.returnErrorMessage(response_code), Toast.LENGTH_LONG).show();
                                sharedPreferences.edit().putString("loggedInID", response.body().getId()).apply();
                                sharedPreferences.edit().putString("mobile_number", validMobile.returnText()).apply();
                                sharedPreferences.edit().putString("password", validPassword.returnText()).apply();
                                Intent mainActivityIntent = new Intent(SignupActivity.this, MainActivity.class);
                                startActivity(mainActivityIntent);
                            } else {
                                Toast.makeText(SignupActivity.this, DisplayErrorMessage.returnErrorMessage(response_code), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthenticationData> call, Throwable t) {
                            Toast.makeText(SignupActivity.this, "Could not make request", Toast.LENGTH_LONG).show();
                        }
                    });
                    break;
                }
            }
        }
    }
}
