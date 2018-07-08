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

import com.example.android.welfare.databaseconnection.APIService;
import com.example.android.welfare.databaseconnection.APIUtils;
import com.example.android.welfare.databaseconnection.DisplayErrorMessage;
import com.example.android.welfare.databaseconnection.responseclasses.OtherData;
import com.example.android.welfare.databaseconnection.responseclasses.ResponseData;
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

public class OtherDetailsActivity extends AppCompatActivity {


    private SharedPreferences sharedPreferences;
    private static final String cacheDataFile = "otherdatacache.data";

    private TextInputEditText ownMainBranch;
    private TextInputEditText ownBranch;
    private TextInputEditText ownGodown;
    private TextInputEditText ownFactory;
    private TextInputEditText ownOthers;

    private TextInputEditText rentedMainBranch;
    private TextInputEditText rentedBranch;
    private TextInputEditText rentedGodown;
    private TextInputEditText rentedFactory;
    private TextInputEditText rentedOthers;
    private TextInputEditText tradersOrganisation;

    private AlterView alterView;
    private CheckBox editableCheck;
    private APIService otherUsingAPI;
    private String loginID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("loggedInID", "").isEmpty()) {
            Intent loginIntent = new Intent(OtherDetailsActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        } else {
            setContentView(R.layout.activity_other_details);

            final Button buttonNext = findViewById(R.id.button_other_details_next);
            final Button buttonHome = findViewById(R.id.activity_button_home);

            buttonNext.setOnClickListener(onClickListener);
            buttonHome.setOnClickListener(onClickListener);

            ownMainBranch = findViewById(R.id.edit_text_own_main_branch);
            ownBranch = findViewById(R.id.edit_text_own_branch);
            ownGodown = findViewById(R.id.edit_text_own_godown);
            ownFactory = findViewById(R.id.edit_text_own_factory);
            ownOthers = findViewById(R.id.edit_text_own_other);

            rentedMainBranch = findViewById(R.id.edit_text_rented_main_branch);
            rentedBranch = findViewById(R.id.edit_text_rented_branch);
            rentedGodown = findViewById(R.id.edit_text_rented_godown);
            rentedFactory = findViewById(R.id.edit_text_rented_factory);
            rentedOthers = findViewById(R.id.edit_text_rented_other);
            tradersOrganisation = findViewById(R.id.edit_text_traders_organisation);
            loginID = sharedPreferences.getString("loggedInID", "");

            alterView = new AlterView();
            editableCheck = findViewById(R.id.checkbox_other_details_editable);
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

            final Toolbar toolbar = findViewById(R.id.activity_toolbar);
            toolbar.setTitle(getString(R.string.activity_other_details_heading));
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


        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.button_other_details_next): {
                    if (editableCheck.isChecked()) {
                        if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                            final boolean flag = true;

                            if (flag || true) {
//                            Intent otherDetailsIntent = new Intent(OtherDetailsActivity.this,
//                                    BankingDetailsActivity.class);
//                            startActivity(otherDetailsIntent);
                                otherUsingAPI = APIUtils.getAPIService();
                                String ownMain = ownMainBranch.getText().toString();
                                String ownB = ownBranch.getText().toString();
                                String ownG = ownGodown.getText().toString();
                                String ownF = ownFactory.getText().toString();
                                String ownO = ownOthers.getText().toString();
                                String rentM = rentedMainBranch.getText().toString();
                                String rentB = rentedBranch.getText().toString();
                                String rentG = rentedGodown.getText().toString();
                                String rentF = rentedFactory.getText().toString();
                                String rentO = rentedOthers.getText().toString();
                                String trOr = tradersOrganisation.getText().toString();

                                otherUsingAPI.saveOther(loginID, ownMain, ownB, ownG, ownF, ownO, rentM,
                                        rentB, rentG, rentF, rentO, trOr).enqueue(new Callback<ResponseData>() {
                                    @Override
                                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                                        int response_code = response.body().getResponseCode();
                                        if (flag || response_code == 200) {
                                            Toast.makeText(OtherDetailsActivity.this,
                                                    getString(R.string.details_saved_confirmation),
                                                    Toast.LENGTH_LONG).show();
                                            nextActivity();

                                        } else {
                                            Toast.makeText(OtherDetailsActivity.this,
                                                    DisplayErrorMessage.returnErrorMessage(response_code),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseData> call, Throwable t) {
                                        Toast.makeText(OtherDetailsActivity.this,
                                                getString(R.string.request_not_sent),
                                                Toast.LENGTH_LONG).show();


                                    }
                                });

                            } else {
                                LinearLayout activityOtherDetailsLayout = findViewById(R.id.layout_activity_other_details);
                                Snackbar validationSnackbar = Snackbar.make(activityOtherDetailsLayout,
                                        getString(R.string.user_details_validation_snackbar_message),
                                        Snackbar.LENGTH_LONG);

                                validationSnackbar.show();
                            }
                        } else {
                            LinearLayout linearLayout = findViewById(R.id.layout_activity_other_details);
                            Snackbar noConnectionSnackbar = Snackbar.make(linearLayout,
                                    getString(R.string.internet_connection_error_message), Snackbar.LENGTH_LONG);
                            noConnectionSnackbar.show();
                        }
                    } else {
                        nextActivity();
                    }
                    break;
                }
                case (R.id.activity_button_home): {
                    Intent homeIntent = new Intent(OtherDetailsActivity.this, MainActivity.class);
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

    public void nextActivity () {
        Intent bankingDetailsActivity = new Intent(OtherDetailsActivity.this,
                BankingDetailsActivity.class);
        startActivity(bankingDetailsActivity);
    }

    public void disableEdit () {
        alterView.disableTextInput(ownMainBranch);
        alterView.disableTextInput(ownBranch);
        alterView.disableTextInput(ownGodown);
        alterView.disableTextInput(ownFactory);
        alterView.disableTextInput(ownOthers);
        alterView.disableTextInput(rentedMainBranch);
        alterView.disableTextInput(rentedBranch);
        alterView.disableTextInput(rentedGodown);
        alterView.disableTextInput(rentedFactory);
        alterView.disableTextInput(rentedOthers);
        alterView.disableTextInput(tradersOrganisation);
    }

    public void allowEdit () {
        alterView.enableTextInput(ownMainBranch);
        alterView.enableTextInput(ownBranch);
        alterView.enableTextInput(ownGodown);
        alterView.enableTextInput(ownFactory);
        alterView.enableTextInput(ownOthers);
        alterView.enableTextInput(rentedMainBranch);
        alterView.enableTextInput(rentedBranch);
        alterView.enableTextInput(rentedGodown);
        alterView.enableTextInput(rentedFactory);
        alterView.enableTextInput(rentedOthers);
        alterView.enableTextInput(tradersOrganisation);
    }

    public void getCacheData() {
        APIService storeOtherData = APIUtils.getAPIService();
        storeOtherData.getOtherData(sharedPreferences.getString("mobile_number", ""),
                sharedPreferences.getString("password", "")).
                enqueue(new Callback<OtherData>() {
                    @Override
                    public void onResponse(Call<OtherData> call, Response<OtherData> response) {
                        try {
                            int response_code = response.body().getResponseCode();
                            if (response_code == 200) {
                                File cache = new File(getCacheDir(), cacheDataFile);
                                ObjectOutputStream cacheWriter = new ObjectOutputStream(new
                                        FileOutputStream(cache));
                                cacheWriter.writeObject(response.body());
                                cacheWriter.close();
                                fillWithCache();
                            } else {
                                Toast.makeText(OtherDetailsActivity.this,
                                        DisplayErrorMessage.returnErrorMessage(response_code),
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<OtherData> call, Throwable t) {
                        System.out.println(getString(R.string.request_failed));
                    }
                });
    }


    public void fillWithCache() {
        try {
            ObjectInputStream cacheReader = new ObjectInputStream(new FileInputStream(
                    getCacheDir() + File.separator + cacheDataFile));
            OtherData cached = (OtherData) cacheReader.readObject();

            ownMainBranch.setText(cached.getOwnMainBranch());
            ownBranch.setText(cached.getOwnBranch());
            ownGodown.setText(cached.getOwnGodown());
            ownFactory.setText(cached.getOwnFactory());
            ownOthers.setText(cached.getOwnOther());

            rentedMainBranch.setText(cached.getRentedMainBranch());
            rentedBranch.setText(cached.getRentedBranch());
            rentedGodown.setText(cached.getRentedGodown());
            rentedFactory.setText(cached.getRentedFactory());
            rentedOthers.setText(cached.getRentedOther());

            tradersOrganisation.setText(cached.getTradersOrganisationName());


        } catch (IOException | ClassNotFoundException e) {
            getCacheData();
        }
    }
}

