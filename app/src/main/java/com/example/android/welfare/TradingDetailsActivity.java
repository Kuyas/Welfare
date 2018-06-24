package com.example.android.welfare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class TradingDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading_details);


        //Spinner for Type of Ownership
        final Spinner spinner = findViewById(R.id.spinner_layout_trading_details_ownership);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activity_trading_details_spinner_ownership, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Context context = getApplicationContext();
                Object text =  parent.getItemAtPosition(pos);
                if(pos>0) {
                    Toast.makeText(context, (CharSequence) text, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
                Context context = getApplicationContext();
//            Object text =  parent.getItemAtPosition(pos);
                Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
            }
        });

        //Spinner for Type of Authority
        final Spinner spinner1 = findViewById(R.id.spinner_layout_trading_details_authority);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.activity_trading_details_spinner_ownership, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Context context = getApplicationContext();
                Object text =  parent.getItemAtPosition(pos);
                if(pos>0) {
                    Toast.makeText(context, (CharSequence) text, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
                Context context = getApplicationContext();
//            Object text =  parent.getItemAtPosition(pos);
                Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
            }
        });





        final Button buttonNext = findViewById(R.id.button_trading_details_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = true;
                TextInputEditText firmName = findViewById(R.id.edit_text_trading_name);
                TextInputEditText firmAddress = findViewById(R.id.edit_text_trading_address);
                TextInputEditText branch = findViewById(R.id.edit_text_trading_branch);
                TextInputEditText godown = findViewById(R.id.edit_text_trading_godown);
                TextInputEditText factory = findViewById(R.id.edit_text_trading_factory);
                TextInputEditText others = findViewById(R.id.edit_text_trading_others);
                TextInputEditText capital = findViewById(R.id.edit_text_trading_capital);
                TextInputEditText gstn = findViewById(R.id.edit_text_trading_gstn);
                TextInputEditText licenseNumber = findViewById(R.id.edit_text_trading_license_number);
                TextInputEditText licenseAuthority = findViewById(R.id.edit_text_trading_licensing_authority);

                TextValidator validFirmName = new TextValidator(firmName);
                TextValidator validFirmAddress = new TextValidator(firmAddress);
                TextValidator validBranch = new TextValidator(branch);
                TextValidator validGodown = new TextValidator(godown);
                TextValidator validFactory = new TextValidator(factory);
                TextValidator validOthers = new TextValidator(others);
                TextValidator validCapital = new TextValidator(capital);
                TextValidator validGstn = new TextValidator(gstn);
                TextValidator validLicenseNumber = new TextValidator(licenseNumber);
                TextValidator validLicenseAuthority = new TextValidator(licenseAuthority);

                if (validFirmName.isValid()) {
                    //write to variable
                } else {
                    flag = false;
                    firmName.setError("Please enter a valid Firm Name");
                }
                if (validFirmAddress.isValid()) {
                    //write to variable
                } else {
                    flag = false;
                    firmAddress.setError("Please enter a valid Firm Address");
                }
                if (validBranch.isValid()) {
                    //write to variable
                } else {
                    flag = false;
                    branch.setError("Please enter a valid Branch Address");
                }
                if (validGodown.isValid()) {
                    //write to variable
                } else {
                    flag = false;
                    godown.setError("Please enter a valid Godown Address");
                }
                if (validFactory.isValid()) {
                    //write to variable
                } else {
                    flag = false;
                    factory.setError("Please enter a valid Factory Address");
                }
                if (validOthers.isValid()) {
                    //write to variable
                } else {
                    flag = false;
                    firmName.setError("Please enter a valid address for any other subsidiaries");
                }
                if (validCapital.isValid()) {
                    //write to variable
                } else {
                    flag = false;
                    capital.setError("Please enter a valid Capital");
                }
                if (validGstn.isValid()) {
                    //write to variable
                } else {
                    flag = false;
                    gstn.setError("Please enter a valid GSTN and Date");
                }
                if (validLicenseNumber.isValid()) {
                    //write to variable
                } else {
                    flag = false;
                    licenseNumber.setError("Please enter a valid License Number");
                }
                if (validLicenseAuthority.isValid()) {
                    //write to variable
                } else {
                    flag = false;
                    licenseAuthority.setError("Please enter a valid License Authority");
                }
                if(spinner.getSelectedItem().toString().trim().equals("Type of Ownership")){
                    flag = false;
                    Toast.makeText(TradingDetailsActivity.this, "Error. Please Select a Valid Type of Ownership", Toast.LENGTH_SHORT).show();

                }
                if(spinner1.getSelectedItem().toString().trim().equals("Name of Official Authority")){
                    flag = false;
                    Toast.makeText(TradingDetailsActivity.this, "Error. Please Select a Valid Official Authority", Toast.LENGTH_SHORT).show();


                }

                if (!flag) {
                    Toast.makeText(TradingDetailsActivity.this, "Details Saved", Toast.LENGTH_LONG).show();

                    Intent paymentDetailsIntent = new Intent(TradingDetailsActivity.this,
                            OtherDetailsActivity.class);
                    startActivity(paymentDetailsIntent);
                }
            }
        });




        final Toolbar toolbar = findViewById(R.id.activity_toolbar);
        toolbar.setTitle(getString(R.string.activity_trading_details_heading));
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


        final Button homeButton = findViewById(R.id.activity_button_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(TradingDetailsActivity.this, MainActivity.class);
                startActivity(homeIntent);
                overridePendingTransition(R.anim.slide_left_to_right, R.anim.slide_right_to_left);
            }
        });
    }




    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.button_trading_details_next): {
                    Intent otherDetailsIntent = new Intent(TradingDetailsActivity.this,
                            OtherDetailsActivity.class);
                    startActivity(otherDetailsIntent);
                }
                default: {
                    break;
                }
            }
        }
    };


}
