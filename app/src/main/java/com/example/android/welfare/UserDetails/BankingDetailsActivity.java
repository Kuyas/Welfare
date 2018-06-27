package com.example.android.welfare.UserDetails;

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

import com.example.android.welfare.Login.LoginActivity;
import com.example.android.welfare.MainActivity;
import com.example.android.welfare.R;

public class BankingDetailsActivity extends AppCompatActivity{

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);
        if (!sharedPreferences.getString("loggedInID", "").isEmpty()) {
            //TODO: Remove the negation

            Intent loginIntent = new Intent(BankingDetailsActivity.this, LoginActivity.class);
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


            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean flag = true;
                    TextInputEditText bankName = findViewById(R.id.activity_bank_details_edittext_name);
                    TextInputEditText owner = findViewById(R.id.activity_bank_details_edittext_owner);
                    TextInputEditText accountNumber = findViewById(R.id.activity_bank_details_edittext_account);
                    TextInputEditText branch = findViewById(R.id.activity_bank_details_edittext_branch);
                    TextInputEditText ifsc = findViewById(R.id.activity_bank_details_edittext_ifsc);

                    TextValidator validbankName = new TextValidator(bankName);
                    TextValidator validOwner = new TextValidator(owner);
                    TextValidator validAccountNumber = new TextValidator(accountNumber);
                    TextValidator validBranch = new TextValidator(branch);
                    TextValidator validIfsc = new TextValidator(ifsc);

                    if (validbankName.isValid()) {
                        // write to variable
                    } else {
                        flag = false;
                        bankName.setError("Please enter a valid Bank name");
                    }

                    if (validOwner.isValid()) {
                        // write to variable;
                    } else {
                        flag = false;
                        owner.setError("Please enter a valid Account Holder name");
                    }

                    if (validAccountNumber.regexValidator(TextValidator.accountnumberregex)) {
                        // write to variable
                    } else {
                        flag = false;
                        accountNumber.setError("Please enter an account number between 9-18 digits long");
                    }

                    if (validBranch.isValid()) {
                        // write to variable
                    } else {
                        flag = false;
                        branch.setError("Please enter a valid branch name");
                    }

                    if (validIfsc.regexValidator(TextValidator.ifscregex)) {
                        // write to variable
                    } else {
                        flag = false;
                        ifsc.setError("IFSC code should have 4 alphabets and 7 digits");
                    }

                    if (flag) {
                        Toast.makeText(BankingDetailsActivity.this, "Details submitted", Toast.LENGTH_LONG).show();
                    }


                    if (flag) {
                        Intent paymentDetailsIntent = new Intent(BankingDetailsActivity.this,
                                PaymentDetailsActivity.class);
                        startActivity(paymentDetailsIntent);
                    } else {
                        LinearLayout activityBankingDetailsLayout = findViewById(R.id.layout_activity_bank_details);
                        Snackbar validationSnackbar = Snackbar.make(activityBankingDetailsLayout,
                                getString(R.string.user_details_validation_snackbar_message),
                                Snackbar.LENGTH_LONG);

                        validationSnackbar.show();
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
}
