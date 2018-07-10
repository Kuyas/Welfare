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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.welfare.MainActivity;
import com.example.android.welfare.NetworkStatus;
import com.example.android.welfare.OnStartCacheRetrieval;
import com.example.android.welfare.R;
import com.example.android.welfare.databaseconnection.APIService;
import com.example.android.welfare.databaseconnection.APIUtils;
import com.example.android.welfare.databaseconnection.DisplayErrorMessage;
import com.example.android.welfare.databaseconnection.responseclasses.ResponseData;
import com.example.android.welfare.databaseconnection.responseclasses.TradingData;
import com.example.android.welfare.login.LoginActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.welfare.OnStartCacheRetrieval.othercachefile;
import static com.example.android.welfare.OnStartCacheRetrieval.tradingcachefile;

public class TradingDetailsActivity extends AppCompatActivity {
    private AlterView alterView;

    private SharedPreferences sharedPreferences;
    private String loginID;
    private APIService tradingUsingAPI;
    private String ownershipSelect;
    private String officialSelect;

    private TextInputEditText firmName;
    private TextInputEditText firmAddress;
    private TextInputEditText branch;
    private TextInputEditText godown;
    private TextInputEditText turnover;
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

    private TradingData cached;

    private TextValidator validFirmName;
    private TextValidator validFirmAddress;
    private TextValidator validBranch;
    private TextValidator validGodown;
    private TextValidator validFactory;
    private TextValidator validOthers;
    private TextValidator validCapital;
    private TextValidator validGstn;
    private TextValidator validLicenseNumber;
    private TextValidator validLicenseAuthority;
    private TextValidator validTurnover;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("loggedInID", "").isEmpty()) {
            Intent loginIntent = new Intent(TradingDetailsActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        } else {
            setContentView(R.layout.activity_trading_details);

            //Spinner for Type of Ownership
            ownershipSpinner = findViewById(R.id.spinner_layout_trading_details_ownership);
            ownershipAdapter = ArrayAdapter.createFromResource(this,
                    R.array.activity_trading_details_spinner_ownership,
                    android.R.layout.simple_spinner_item);
            ownershipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ownershipSpinner.setAdapter(ownershipAdapter);

            //Spinner for Type of Authority
            authoritySpinner = findViewById(R.id.spinner_layout_trading_details_authority);
            authorityAdapter = ArrayAdapter.createFromResource(this,
                    R.array.activity_trading_details_spinner_authority,
                    android.R.layout.simple_spinner_item);
            authorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            authoritySpinner.setAdapter(authorityAdapter);

            alterView = new AlterView();

            tradingUsingAPI = APIUtils.getAPIService();
            firmName = findViewById(R.id.edit_text_trading_name);
            firmAddress = findViewById(R.id.edit_text_trading_address);
            turnover = findViewById(R.id.edit_text_trading_turnover);
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
            disableEdit();

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


            final Button buttonNext = findViewById(R.id.button_trading_details_next);
            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editableCheck.isChecked()) {
                        if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                            boolean flag = true;

                            validFirmName = new TextValidator(firmName);
                            validFirmAddress = new TextValidator(firmAddress);
                            validBranch = new TextValidator(branch);
                            validGodown = new TextValidator(godown);
                            validFactory = new TextValidator(factory);
                            validOthers = new TextValidator(others);
                            validCapital = new TextValidator(capital);
                            validGstn = new TextValidator(gstn);
                            validLicenseNumber = new TextValidator(licenseNumber);
                            validLicenseAuthority = new TextValidator(licenseAuthority);
                            validTurnover = new TextValidator(turnover);


                            if (!validFirmName.isValid()) {
                                flag = false;
                                firmName.setError(getString(
                                        R.string.activity_trading_details_invalid_firm_name));
                            }
                            if (!validFirmAddress.isValid()) {
                                flag = false;
                                firmAddress.setError(getString(
                                        R.string.activity_trading_details_invalid_firm_address));
                            }
                            if (!validTurnover.isValid()) {
                                flag = false;
                                turnover.setError(getString(
                                        R.string.activity_trading_details_invalid_annual_turnover));
                            }
                            if (!validBranch.isValid()) {
                                flag = false;
                                branch.setError(getString(
                                        R.string.activity_trading_details_invalid_branch_address));
                            }
                            if (!validGodown.isValid()) {
                                flag = false;
                                godown.setError(getString(
                                        R.string.activity_trading_details_invalid_godown_address));
                            }
                            if (!validFactory.isValid()) {
                                flag = false;
                                factory.setError(getString(
                                        R.string.activity_trading_details_invalid_factory_address));
                            }
//                            if (!validOthers.isValid()) {
//                                flag = false;
//                                firmName.setError("Please enter a valid address for any other subsidiaries");
//                            }
                            if (!validCapital.isValid()) {
                                flag = false;
                                capital.setError(getString(
                                        R.string.activity_trading_details_invalid_capital_contribution));
                            }
                            if (!validGstn.isValid()) {
                                flag = false;
                                gstn.setError(getString(
                                        R.string.activity_trading_details_invalid_gstn_date));
                            }
                            if (!validLicenseNumber.isValid()) {
                                flag = false;
                                licenseNumber.setError(getString(
                                        R.string.activity_trading_details_invalid_license_number));
                            }
                            if (!validLicenseAuthority.isValid()) {
                                flag = false;
                                licenseAuthority.setError(getString(
                                        R.string.activity_trading_details_invalid_license_authority));
                            }
                            if (ownershipSpinner.getSelectedItem().toString().trim().equals(
                                    getString(R.string.activity_trading_details_ownership_type))) {
                                flag = false;
                                Toast.makeText(TradingDetailsActivity.this, getString(
                                        R.string.activity_trading_details_invalid_ownership_type),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                ownershipSelect = ownershipSpinner.getSelectedItem().toString().trim();
                            }
                            if (authoritySpinner.getSelectedItem().toString().trim().equals(
                                    getString(R.string.activity_trading_details_official_authority))) {
                                flag = false;
                                Toast.makeText(TradingDetailsActivity.this, getString(
                                        R.string.activity_trading_details_invalid_official_authority),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                officialSelect = authoritySpinner.getSelectedItem().toString().trim();
                            }


                            if (flag) {

                                tradingUsingAPI.saveTrading(loginID, validFirmName.returnText(),
                                        validFirmAddress.returnText(), validTurnover.returnText(),
                                        validBranch.returnText(), validGodown.returnText(),
                                        validFactory.returnText(), validOthers.returnText(),
                                        ownershipSelect, validCapital.returnText(),
                                        validGstn.returnText(), validLicenseNumber.returnText(),
                                        validLicenseAuthority.returnText(),
                                        officialSelect).enqueue(new Callback<ResponseData>() {
                                    @Override
                                    public void onResponse(Call<ResponseData> call,
                                                           Response<ResponseData> response) {
                                        int response_code = response.body().getResponseCode();
                                        if (response_code == 200) {
                                            Toast.makeText(TradingDetailsActivity.this,
                                                    getString(R.string.details_saved_confirmation),
                                                    Toast.LENGTH_LONG).show();
                                            editableCheck.setChecked(false);
                                            disableEdit();
                                            changeCache();
                                            nextActivity();
                                        } else {
                                            Toast.makeText(TradingDetailsActivity.this,
                                                    DisplayErrorMessage.returnErrorMessage(response_code),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseData> call, Throwable t) {
                                        Toast.makeText(TradingDetailsActivity.this,
                                                getString(R.string.request_not_sent),
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
        alterView.disableTextInput(firmName);
        alterView.disableTextInput(firmAddress);
        alterView.disableTextInput(turnover);
        alterView.disableTextInput(branch);
        alterView.disableTextInput(godown);
        alterView.disableTextInput(factory);
        alterView.disableTextInput(others);

        alterView.disableSpinner(ownershipSpinner);

        alterView.disableTextInput(capital);
        alterView.disableTextInput(gstn);
        alterView.disableTextInput(licenseNumber);
        alterView.disableTextInput(licenseAuthority);

        alterView.disableSpinner(authoritySpinner);
    }

    public void allowEdit() {
        alterView.enableTextInput(firmName);
        alterView.enableTextInput(firmAddress);
        alterView.enableTextInput(turnover);
        alterView.enableTextInput(branch);
        alterView.enableTextInput(godown);
        alterView.enableTextInput(factory);
        alterView.enableTextInput(others);

        alterView.enableSpinner(ownershipSpinner);

        alterView.enableTextInput(capital);
        alterView.enableTextInput(gstn);
        alterView.enableTextInput(licenseNumber);
        alterView.enableTextInput(licenseAuthority);

        alterView.enableSpinner(authoritySpinner);
    }

    public void changeCache() {
        cached.setAnnualTurnover(validTurnover.returnText());
        cached.setMtpBranch(validBranch.returnText());
        cached.setCapital(validCapital.returnText());
        cached.setMtpFactory(validFactory.returnText());
        cached.setFirmAddress(validFirmAddress.returnText());
        cached.setFirmName(validFirmName.returnText());
        cached.setGstn(validGstn.returnText());
        cached.setMtpGodown(validGodown.returnText());
        cached.setLicenseAuth(validLicenseAuthority.returnText());
        cached.setLicenseNum(validLicenseNumber.returnText());
        cached.setMtpOthers(validOthers.returnText());
        cached.setOwnership(ownershipSelect);
        cached.setOfficial(officialSelect);
        try {
            File cache = new File(getCacheDir(), tradingcachefile);
            ObjectOutputStream cacheWriter = new ObjectOutputStream(new FileOutputStream(cache));
            cacheWriter.writeObject(cached);
            cacheWriter.close();
        } catch (Exception e) {
            Toast.makeText(this, R.string.activity_forms_cached_save_failed, Toast.LENGTH_LONG).show();
        }
    }

    public void fillWithCache() {
        try {
            ObjectInputStream cacheReader = new ObjectInputStream(new FileInputStream(
                    getCacheDir() + File.separator + OnStartCacheRetrieval.tradingcachefile));
            cached = (TradingData) cacheReader.readObject();
            firmName.setText(cached.getFirmName());
            firmAddress.setText(cached.getFirmAddress());
            branch.setText(cached.getMtpBranch());
            factory.setText(cached.getMtpFactory());
            godown.setText(cached.getMtpGodown());
            others.setText(cached.getMtpOthers());
            others.setText(cached.getMtpOthers());
            turnover.setText(cached.getAnnualTurnover());

            for (int i = 0; i < ownershipAdapter.getCount(); i++) {
                if (ownershipAdapter.getItem(i).equals(cached.getOwnership())) {
                    ownershipSpinner.setSelection(i);
                    break;
                }
            }

            capital.setText(cached.getCapital());
            gstn.setText(cached.getGstn());
            licenseNumber.setText(cached.getLicenseNum());
            licenseAuthority.setText(cached.getLicenseAuth());

            for (int i = 0; i < authorityAdapter.getCount(); i++) {
                if (authorityAdapter.getItem(i).equals(cached.getLicenseAuth())) {
                    authoritySpinner.setSelection(i);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.activity_button_home): {
                    Intent homeIntent = new Intent(TradingDetailsActivity.this,
                            MainActivity.class);
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
