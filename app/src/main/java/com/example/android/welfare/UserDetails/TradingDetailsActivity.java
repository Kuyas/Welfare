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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.welfare.databaseconnection.APIService;
import com.example.android.welfare.databaseconnection.APIUtils;
import com.example.android.welfare.databaseconnection.DisplayErrorMessage;
import com.example.android.welfare.databaseconnection.responseclasses.ResponseData;
import com.example.android.welfare.databaseconnection.responseclasses.TradingData;
import com.example.android.welfare.login.LoginActivity;
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

public class TradingDetailsActivity extends AppCompatActivity {
    private static final boolean DEBUG = true;
    private static final String cacheDataFile = "tradingdatacache.data";

    private SharedPreferences sharedPreferences;
    private String loginID;
    private APIService tradingUsingAPI;
    private String ownershipSelect;
    private String officialSelect;

    private TextInputEditText firmName;
    private TextInputEditText firmAddress;
    private TextInputEditText branch;
    private TextInputEditText godown;
    private TextInputEditText factory;
    private TextInputEditText others;
    private TextInputEditText capital;
    private TextInputEditText gstn;
    private TextInputEditText licenseNumber;
    private TextInputEditText licenseAuthority;
    private Spinner authoritySpinner;
    private ArrayAdapter<CharSequence> authorityAdapter;
    private Spinner ownershipSpinner;
    private ArrayAdapter<CharSequence> ownershipAdapter;
    private CheckBox editableCheck;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("loggedInID", "").isEmpty()){
            Intent loginIntent = new Intent(TradingDetailsActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        } else {
            setContentView(R.layout.activity_trading_details);

            //Spinner for Type of Ownership
            ownershipSpinner = findViewById(R.id.spinner_layout_trading_details_ownership);
            ownershipAdapter = ArrayAdapter.createFromResource(this, R.array.activity_trading_details_spinner_ownership, android.R.layout.simple_spinner_item);
            ownershipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ownershipSpinner.setAdapter(ownershipAdapter);

            ownershipSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    Context context = getApplicationContext();
                    Object text = parent.getItemAtPosition(pos);
                    if (pos > 0) {
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
            authoritySpinner = findViewById(R.id.spinner_layout_trading_details_authority);
            authorityAdapter = ArrayAdapter.createFromResource(this, R.array.activity_trading_details_spinner_authority, android.R.layout.simple_spinner_item);
            authorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            authoritySpinner.setAdapter(authorityAdapter);

            authoritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    Context context = getApplicationContext();
                    Object text = parent.getItemAtPosition(pos);
                    if (pos > 0) {
                        Toast.makeText(context, (CharSequence) text, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
                }
            });



            tradingUsingAPI = APIUtils.getAPIService();
            firmName = findViewById(R.id.edit_text_trading_name);
            firmAddress = findViewById(R.id.edit_text_trading_address);
            branch = findViewById(R.id.edit_text_trading_branch);
            godown = findViewById(R.id.edit_text_trading_godown);
            factory = findViewById(R.id.edit_text_trading_factory);
            others = findViewById(R.id.edit_text_trading_others);
            capital = findViewById(R.id.edit_text_trading_capital);
            gstn = findViewById(R.id.edit_text_trading_gstn);
            licenseNumber = findViewById(R.id.edit_text_trading_license_number);
            licenseAuthority = findViewById(R.id.edit_text_trading_licensing_authority);
            editableCheck = findViewById(R.id.checkbox_trading_details_editable);
            loginID = sharedPreferences.getString("loggedInID", "");

            fillWithCache();

            final Button buttonNext = findViewById(R.id.button_trading_details_next);
            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editableCheck.isChecked()) {
                        if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                            boolean flag = true;

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

                            if (!validFirmName.isValid()) {
                                flag = false;
                                firmName.setError("Please enter a valid Firm Name");
                            }
                            if (!validFirmAddress.isValid()) {
                                flag = false;
                                firmAddress.setError("Please enter a valid Firm Address");
                            }
                            if (!validBranch.isValid()) {
                                flag = false;
                                branch.setError("Please enter a valid Branch Address");
                            }
                            if (!validGodown.isValid()) {
                                flag = false;
                                godown.setError("Please enter a valid Godown Address");
                            }
                            if (!validFactory.isValid()) {
                                flag = false;
                                factory.setError("Please enter a valid Factory Address");
                            }
                            if (!validOthers.isValid()) {
                                flag = false;
                                firmName.setError("Please enter a valid address for any other subsidiaries");
                            }
                            if (!validCapital.isValid()) {
                                flag = false;
                                capital.setError("Please enter a valid Capital");
                            }
                            if (!validGstn.isValid()) {
                                flag = false;
                                gstn.setError("Please enter a valid GSTN and Date");
                            }
                            if (!validLicenseNumber.isValid()) {
                                flag = false;
                                licenseNumber.setError("Please enter a valid License Number");
                            }
                            if (!validLicenseAuthority.isValid()) {
                                flag = false;
                                licenseAuthority.setError("Please enter a valid License Authority");
                            }
                            if (ownershipSpinner.getSelectedItem().toString().trim().equals("Type of Ownership")) {
                                flag = false;
                                Toast.makeText(TradingDetailsActivity.this, "Error. Please Select a Valid Type of Ownership", Toast.LENGTH_SHORT).show();
                            }else {
                                ownershipSelect = ownershipSpinner.getSelectedItem().toString().trim();
                            }
                            if (authoritySpinner.getSelectedItem().toString().trim().equals("Name of Official Authority")) {
                                flag = false;
                                Toast.makeText(TradingDetailsActivity.this, "Error. Please Select a Valid Official Authority", Toast.LENGTH_SHORT).show();
                            }else{
                                officialSelect = authoritySpinner.getSelectedItem().toString().trim();
                            }

                            if (flag || DEBUG) {

                                tradingUsingAPI.saveTrading(loginID, validFirmName.returnText(), validFirmAddress.returnText(),
                                        validBranch.returnText(), validGodown.returnText(), validFactory.returnText(), validOthers.returnText(),
                                        ownershipSelect, validCapital.returnText(), validGstn.returnText(), validLicenseNumber.returnText(), validLicenseAuthority.returnText(),
                                        officialSelect).enqueue(new Callback<ResponseData>() {
                                    @Override
                                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                                        int response_code = response.body().getResponseCode();
                                        if (response_code == 200) {
                                            Toast.makeText(TradingDetailsActivity.this, "Details Saved", Toast.LENGTH_LONG).show();
                                            nextActivity();
                                        } else {
                                            Toast.makeText(TradingDetailsActivity.this, DisplayErrorMessage.returnErrorMessage(response_code),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseData> call, Throwable t) {
                                        Toast.makeText(TradingDetailsActivity.this, "Request not sent",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        } else {
                            LinearLayout linearLayout = findViewById(R.id.layout_activity_trading_details);
                            Snackbar noConnectionSnackbar = Snackbar.make(linearLayout,
                                    getString(R.string.internet_connection_error_message), Snackbar.LENGTH_LONG);
                            noConnectionSnackbar.show();
                        }
                    } else {
                        nextActivity();
                    }
                }
            });

            editableCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editableCheck.isChecked()) {
                        allowEdit();
                    } else {
                        disableEdit();
                    }
                }
            });
        }



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
        homeButton.setOnClickListener(onClickListener);
    }

    public void nextActivity() {
        Intent otherDetailsIntent = new Intent(TradingDetailsActivity.this,
                OtherDetailsActivity.class);
        startActivity(otherDetailsIntent);
    }

    public void disableEdit() {

    }

    public void allowEdit() {

    }


    public void getCacheData() {
        APIService storeTradingData = APIUtils.getAPIService();
        storeTradingData.getTradingData(sharedPreferences.getString("mobile_number", ""), sharedPreferences.getString("password", "")).enqueue(new Callback<TradingData>() {
            @Override
            public void onResponse(Call<TradingData> call, Response<TradingData> response) {
                try {
                    int response_code = response.body().getResponseCode();
                    if (response_code== 200) {
                        File cache = new File(getCacheDir(), cacheDataFile);
                        ObjectOutputStream cacheWriter = new ObjectOutputStream(new FileOutputStream(cache));
                        cacheWriter.writeObject(response.body());
                        cacheWriter.close();
                        fillWithCache();
                    } else {
                        Toast.makeText(TradingDetailsActivity.this, DisplayErrorMessage.returnErrorMessage(response_code), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TradingData> call, Throwable t) {
                System.out.println("Request Failed");
            }
        });
    }

    public void fillWithCache() {
        try {
            ObjectInputStream cacheReader = new ObjectInputStream(new FileInputStream(getCacheDir() + File.separator + cacheDataFile));
            TradingData cached = (TradingData) cacheReader.readObject();
            firmName.setText(cached.getFirmName());
            firmAddress.setText(cached.getFirmAddress());
            branch.setText(cached.getMtpBranch());
            factory.setText(cached.getMtpFactory());
            godown.setText(cached.getMtpGodown());
            others.setText(cached.getMtpOthers());
            others.setText(cached.getMtpOthers());

            for (int i = 0; i < ownershipAdapter.getCount(); i++) {
                if (ownershipAdapter.getItem(i).equals(cached.getOwnershipType())) {
                    ownershipSpinner.setSelection(i);
                    break;
                }
            }
            
            capital.setText(cached.getCapitalContribution());
            gstn.setText(cached.getGstnDate());
            licenseNumber.setText(cached.getLicenseNum());
            licenseAuthority.setText(cached.getLicenseAuth());

            for (int i = 0; i < authorityAdapter.getCount(); i++) {
                if (authorityAdapter.getItem(i).equals(cached.getLicenseAuth())) {
                    authoritySpinner.setSelection(i);
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            getCacheData();
        }
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case (R.id.button_trading_details_next): {
//                    if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
//                        Intent otherDetailsIntent = new Intent(TradingDetailsActivity.this,
//                                OtherDetailsActivity.class);
//                        startActivity(otherDetailsIntent);
//                    } else {
//                        LinearLayout linearLayout = findViewById(R.id.layout_activity_trading_details);
//                        Snackbar noConnectionSnackbar = Snackbar.make(linearLayout,
//                                getString(R.string.internet_connection_error_message), Snackbar.LENGTH_LONG);
//                        noConnectionSnackbar.show();
//                    }
//                    break;
//                }
                case (R.id.activity_button_home): {
                    Intent homeIntent = new Intent(TradingDetailsActivity.this, MainActivity.class);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homeIntent);
                    overridePendingTransition(R.anim.slide_left_to_right, R.anim.slide_right_to_left);
                    break;
                }
                default: {
                    break;
                }
            }
        }
    };


}
