package com.example.android.welfare.userdetails;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.welfare.login.LoginActivity;
import com.example.android.welfare.databaseconnection.APIService;
import com.example.android.welfare.databaseconnection.APIUtils;
import com.example.android.welfare.databaseconnection.DisplayErrorMessage;
import com.example.android.welfare.databaseconnection.responseclasses.ResponseData;
import com.example.android.welfare.MainActivity;
import com.example.android.welfare.NetworkStatus;
import com.example.android.welfare.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankingDetailsActivity extends AppCompatActivity{

    private SharedPreferences sharedPreferences;

    private TextInputEditText bankName;
    private TextInputEditText accountHolderName;
    private TextInputEditText accountNumber;
    private TextInputEditText branch;
    private TextInputEditText ifsc;

    private APIService bankingUsingAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("loggedInID", "").isEmpty()) {
            //TODO: Remove the negation

            Intent loginIntent = new Intent(BankingDetailsActivity.this,
                    LoginActivity.class);
            startActivity(loginIntent);
        } else {
            setContentView(R.layout.activity_banking_details);

            Button submitButton = findViewById(R.id.activity_bank_details_button_submit);

            final Toolbar toolbar = findViewById(R.id.activity_toolbar);
            toolbar.setTitle(getString(R.string.activity_bank_details_heading));
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

            bankName = findViewById(R.id.activity_bank_details_edittext_name);
            accountHolderName = findViewById(R.id.activity_bank_details_edittext_owner);
            accountNumber = findViewById(R.id.activity_bank_details_edittext_account);
            branch = findViewById(R.id.activity_bank_details_edittext_branch);
            ifsc = findViewById(R.id.activity_bank_details_edittext_ifsc);


            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                        boolean flag = true;

                        TextValidator validbankName = new TextValidator(bankName);
                        TextValidator validAccountHolderName = new TextValidator(accountHolderName);
                        TextValidator validAccountNumber = new TextValidator(accountNumber);
                        TextValidator validBranch = new TextValidator(branch);
                        TextValidator validIfsc = new TextValidator(ifsc);

                        if (!validbankName.isValid()) {
                            flag = false;
                            bankName.setError(getString(
                                    R.string.activity_banking_details_invalid_bank_name));
                        }

                        if (!validAccountHolderName.isValid()) {
                            flag = false;
                            accountHolderName.setError(getString(
                                    R.string.activity_banking_details_invalid_account_holder_name));
                        }

                        if (!validAccountNumber.regexValidator(TextValidator.accountnumberregex)) {
                            flag = false;
                            accountNumber.setError(getString(
                                    R.string.activity_banking_details_invalid_acount_number));
                        }

                        if (!validBranch.isValid()) {
                            flag = false;
                            branch.setError(getString(
                                    R.string.activity_banking_details_invalid_branch_name));
                        }

                        if (!validIfsc.regexValidator(TextValidator.ifscregex)) {
                            flag = false;
                            ifsc.setError(getString(
                                    R.string.activity_banking_details_invalid_ifsc_code));
                        }

                        if (flag) {
                            bankingUsingAPI = APIUtils.getAPIService();
                            bankingUsingAPI.saveBanking(sharedPreferences.
                                            getString("loggedInID", ""),
                                    validbankName.returnText(), validAccountNumber.returnText(),
                                    validAccountHolderName.returnText(), validBranch.returnText(),
                                    validIfsc.returnText()).enqueue(new Callback<ResponseData>() {
                                @Override
                                public void onResponse(Call<ResponseData> call,
                                                       Response<ResponseData> response) {
                                    int response_code = response.body().getResponseCode();
                                    if (response_code == 200) {
                                        Toast.makeText(BankingDetailsActivity.this,
                                                getString(R.string.details_saved_confirmation),
                                                Toast.LENGTH_LONG).show();
                                        nextActivity();
                                    } else {
                                        Toast.makeText(BankingDetailsActivity.this,
                                                DisplayErrorMessage.returnErrorMessage(response_code),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseData> call, Throwable t) {
                                    Toast.makeText(BankingDetailsActivity.this,
                                            getString(R.string.request_not_sent),
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            LinearLayout activityBankingDetailsLayout = findViewById(
                                    R.id.layout_activity_bank_details);
                            Snackbar validationSnackbar = Snackbar.make(activityBankingDetailsLayout,
                                    getString(R.string.user_details_validation_snackbar_message),
                                    Snackbar.LENGTH_LONG);

                            validationSnackbar.show();
                        }
                    } else {
                        LinearLayout linearLayout = findViewById(R.id.layout_activity_bank_details);
                        Snackbar noConnectionSnackbar = Snackbar.make(linearLayout,
                                getString(R.string.internet_connection_error_message), Snackbar.LENGTH_LONG);
                        noConnectionSnackbar.show();
                    }
                }
            });


            final Button homeButton = findViewById(R.id.activity_button_home);
            homeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent homeIntent = new Intent(BankingDetailsActivity.this, MainActivity.class);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homeIntent);
                    overridePendingTransition(R.anim.slide_left_to_right, R.anim.slide_right_to_left);
                }
            });
        }
    }

    public void nextActivity() {
        Intent next = new Intent(BankingDetailsActivity.this, PaymentDetailsActivity.class);
        startActivity(next);
    }
}
