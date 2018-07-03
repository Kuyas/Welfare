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

import com.example.android.welfare.login.LoginActivity;
import com.example.android.welfare.MainActivity;
import com.example.android.welfare.NetworkStatus;
import com.example.android.welfare.R;

public class OtherDetailsActivity extends AppCompatActivity {


    private SharedPreferences sharedPreferences;

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

//    private TextValidator validateOwnMainBranch;
//    private TextValidator validateOwnBranch;
//    private TextValidator validateOwnGodown;
//    private TextValidator validateOwnFactory;
//    private TextValidator validateOwnOthers;
//
//    private TextValidator validateRentedMainBranch;
//    private TextValidator validateRentedBranch;
//    private TextValidator validateRentedGodown;
//    private TextValidator validateRentedFactory;
//    private TextValidator validateRentedOthers;
//    private TextValidator validateTradersOrganisation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("loggedInID", "").isEmpty()) {
            //TODO: Remove the negation

            Intent loginIntent = new Intent(OtherDetailsActivity.this,
                    LoginActivity.class);
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

//        validateOwnMainBranch = new TextValidator(ownMainBranch);
//        validateOwnBranch = new TextValidator(ownBranch);
//        validateOwnGodown = new TextValidator(ownGodown);
//        validateOwnFactory = new TextValidator(ownFactory);
//        validateOwnOthers = new TextValidator(ownOthers);
//
//        validateRentedMainBranch = new TextValidator(rentedMainBranch);
//        validateRentedBranch = new TextValidator(rentedBranch);
//        validateRentedGodown = new TextValidator(rentedGodown);
//        validateRentedFactory = new TextValidator(rentedFactory);
//        validateRentedOthers = new TextValidator(rentedOthers);
//        validateTradersOrganisation = new TextValidator(tradersOrganisation);


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
                    if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                        boolean flag = true;

                        // TODO: check which fields in OtherDetails are to be compulsory
//                    if (validateOwnMainBranch.isValid()) {
//                        // store value
//                    } else {
//                        flag = false;
//                        ownMainBranch.setError("Please enter a valid branch");
//                    }


                        if (flag) {
                            Intent otherDetailsIntent = new Intent(OtherDetailsActivity.this,
                                    BankingDetailsActivity.class);
                            startActivity(otherDetailsIntent);
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

}
