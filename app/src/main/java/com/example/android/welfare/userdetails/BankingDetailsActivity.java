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
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.welfare.databaseconnection.responseclasses.BankingData;
import com.example.android.welfare.login.LoginActivity;
import com.example.android.welfare.databaseconnection.APIService;
import com.example.android.welfare.databaseconnection.APIUtils;
import com.example.android.welfare.databaseconnection.DisplayErrorMessage;
import com.example.android.welfare.databaseconnection.responseclasses.ResponseData;
import com.example.android.welfare.MainActivity;
import com.example.android.welfare.NetworkStatus;
import com.example.android.welfare.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankingDetailsActivity extends AppCompatActivity{

    private static final String cacheDataFile = "bankingdatacache.data";

    private SharedPreferences sharedPreferences;

    private TextInputEditText bankName;
    private TextInputEditText accountHolderName;
    private TextInputEditText accountNumber;
    private TextInputEditText bankBranch;
    private TextInputEditText ifscCode;

    private CheckBox editableCheck;
    private AlterView alterView;
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
            bankBranch = findViewById(R.id.activity_bank_details_edittext_branch);
            ifscCode = findViewById(R.id.activity_bank_details_edittext_ifsc);

            alterView = new AlterView();
            editableCheck = findViewById(R.id.checkbox_banking_details_editable);
            bankingUsingAPI = APIUtils.getAPIService();

            fillWithCache();
            disableEdit();

            editableCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editableCheck.isChecked()) {
                        enableEdit();
                    } else {
                        disableEdit();
                    }
                }
            });

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editableCheck.isChecked()) {
                        if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                            boolean flag = true;

                            TextValidator validbankName = new TextValidator(bankName);
                            TextValidator validAccountHolderName = new TextValidator(accountHolderName);
                            TextValidator validAccountNumber = new TextValidator(accountNumber);
                            TextValidator validBranch = new TextValidator(bankBranch);
                            TextValidator validifscCode = new TextValidator(ifscCode);

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
                                bankBranch.setError(getString(
                                        R.string.activity_banking_details_invalid_branch_name));
                            }

                            if (!validifscCode.regexValidator(TextValidator.ifscregex)) {
                                flag = false;
                                ifscCode.setError(getString(
                                        R.string.activity_banking_details_invalid_ifsc_code));
                            }

                            if (flag) {
                                bankingUsingAPI.saveBanking(sharedPreferences.
                                                getString("loggedInID", ""),
                                        validbankName.returnText(), validAccountNumber.returnText(),
                                        validAccountHolderName.returnText(), validBranch.returnText(),
                                        validifscCode.returnText()).enqueue(new Callback<ResponseData>() {
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
                    } else {
                        nextActivity();
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

    public void disableEdit() {
        alterView.disableTextInput(bankName);
        alterView.disableTextInput(accountNumber);
        alterView.disableTextInput(accountHolderName);
        alterView.disableTextInput(bankBranch);
        alterView.disableTextInput(ifscCode);
    }

    public void enableEdit () {
        alterView.enableTextInput(bankName);
        alterView.enableTextInput(accountNumber);
        alterView.enableTextInput(accountHolderName);
        alterView.enableTextInput(bankBranch);
        alterView.enableTextInput(ifscCode);
    }

    public void getCacheData () {
        APIService storeBankingData = APIUtils.getAPIService();
        storeBankingData.getBankingData(sharedPreferences.getString("mobile_number", ""),
                sharedPreferences.getString("password", "")).enqueue(new Callback<BankingData>() {
            @Override
            public void onResponse(Call<BankingData> call, Response<BankingData> response) {
                try {
                    int response_code = response.body().getResponseCode();
                    if (response_code == 200) {
                        File cache = new File(getCacheDir(), cacheDataFile);
                        ObjectOutputStream cacheWriter = new ObjectOutputStream(new FileOutputStream(cache));
                        cacheWriter.writeObject(response.body());
                        cacheWriter.close();
                        fillWithCache();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BankingData> call, Throwable t) {}
        });
    }

    public void fillWithCache () {
        try {
            ObjectInputStream cacheReader = new ObjectInputStream(new FileInputStream(
                    getCacheDir() + File.separator + cacheDataFile));
            BankingData cached = (BankingData) cacheReader.readObject();

            bankName.setText(cached.getBankName());
            accountNumber.setText(cached.getAccountNumber());
            accountHolderName.setText(cached.getAccountHolderName());
            bankBranch.setText(cached.getBankBranch());
            ifscCode.setText(cached.getIfscCode());


        } catch (IOException | ClassNotFoundException e) {
            getCacheData();
        }
    }
}
