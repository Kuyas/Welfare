package com.example.android.welfare.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.welfare.MainActivity;
import com.example.android.welfare.NetworkStatus;
import com.example.android.welfare.R;
import com.example.android.welfare.databaseconnection.APIService;
import com.example.android.welfare.databaseconnection.APIUtils;
import com.example.android.welfare.databaseconnection.responseclasses.AuthenticationData;
import com.example.android.welfare.userdetails.TextValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private String mobileString, passwordString;
    private TextValidator mobileValidator, passwordValidator;
    private boolean entry_flag;
    private APIService loginUsingApi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);

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

        final TextInputEditText mobile, password;
        Button login;

        mobile = findViewById(R.id.activity_login_edittext_mobile);
        password = findViewById(R.id.activity_login_edittext_password);
        loginUsingApi = APIUtils.getAPIService();


        Button signupbutton = findViewById(R.id.activity_login_button_signup);
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                    Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(intent);
                } else {
                    LinearLayout linearLayout = findViewById(R.id.layout_activity_login);
                    Snackbar noConnectionSnackbar = Snackbar.make(linearLayout,
                            getString(R.string.internet_connection_error_message), Snackbar.LENGTH_LONG);
                    noConnectionSnackbar.show();
                }
            }
        });

        login = findViewById(R.id.acitvity_login_button_signin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                    entry_flag = true;
                    mobileValidator = new TextValidator(mobile);
                    passwordValidator = new TextValidator(password);
                    if (!mobileValidator.regexValidator(TextValidator.mobilenumberregex)) {
                        entry_flag = false;
                        mobile.setError(getString(R.string.invalid_mobile_number));
                    } else if (!passwordValidator.regexValidator(TextValidator.passwordregex)) {
                        entry_flag = false;
                        password.setError(getString(R.string.activity_forgot_password_invalid_password));
                    } else {
                        mobileString = mobileValidator.returnText();
                        passwordString = passwordValidator.returnText();
                    }
                    if (entry_flag) {
                        loginUsingApi.loginUser(mobileValidator.returnText(), passwordValidator.
                                returnText()).enqueue(new Callback<AuthenticationData>() {
                            @Override
                            public void onResponse(Call<AuthenticationData> call,
                                                   Response<AuthenticationData> response) {
                                int response_code = response.body().getResponseCode();
                                if (response_code == 200) {
                                    sharedPreferences.edit().putString("loggedInID",
                                            response.body().getId()).apply();
                                    sharedPreferences.edit().putString("mobile_number",
                                            mobileValidator.returnText()).apply();
                                    sharedPreferences.edit().putString("password",
                                            passwordValidator.returnText()).apply();
                                    Intent mainActivity = new Intent(LoginActivity.this,
                                            MainActivity.class);
                                    startActivity(mainActivity);
                                } else {
                                    Toast.makeText(LoginActivity.this,
                                            getString(R.string.request_error), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<AuthenticationData> call, Throwable t) {
                                Toast.makeText(LoginActivity.this,
                                        getString(R.string.request_failed), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else {
                    LinearLayout linearLayout = findViewById(R.id.layout_activity_login);
                    Snackbar noConnectionSnackbar = Snackbar.make(linearLayout,
                            getString(R.string.internet_connection_error_message), Snackbar.LENGTH_LONG);
                    noConnectionSnackbar.show();
                }
            }
        });

        Button forgotpassword = findViewById(R.id.activity_login_button_forgot_password);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotpasswordActivity = new Intent(LoginActivity.this,
                        ForgotPasswordActivity.class);
                startActivity(forgotpasswordActivity);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent closeAppIntent = new Intent(Intent.ACTION_MAIN);
        closeAppIntent.addCategory(Intent.CATEGORY_HOME);
        closeAppIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(closeAppIntent);
    }

}
