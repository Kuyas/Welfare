package com.example.android.welfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BankingDetailsActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banking_details);

        Button submitButton = (Button) findViewById(R.id.activity_bank_details_button_submit);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = true;
                TextInputEditText bankName = (TextInputEditText) findViewById(R.id.activity_bank_details_edittext_name);
                TextInputEditText owner = (TextInputEditText) findViewById(R.id.activity_bank_details_edittext_owner);
                TextInputEditText accountNumber = (TextInputEditText) findViewById(R.id.activity_bank_details_edittext_account);
                TextInputEditText branch = (TextInputEditText) findViewById(R.id.activity_bank_details_edittext_branch);
                TextInputEditText ifsc = (TextInputEditText) findViewById(R.id.activity_bank_details_edittext_ifsc);

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

                if (validAccountNumber.regexValidator("^\\d{9,18}$")) {
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

                if (validIfsc.regexValidator("^[A-Za-z]{4}\\d{7}$")) {
                    // write to variable
                } else {
                    flag = false;
                    ifsc.setError("IFSC code should have 4 alphabets and 7 digits");
                }

                if (flag) {
                    Toast.makeText(BankingDetailsActivity.this, "Details submitted", Toast.LENGTH_LONG);
                }

            }
        });
    }
}
