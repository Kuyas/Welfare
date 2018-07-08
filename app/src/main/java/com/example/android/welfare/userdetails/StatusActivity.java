package com.example.android.welfare.userdetails;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.welfare.databaseconnection.APIService;
import com.example.android.welfare.databaseconnection.APIUtils;
import com.example.android.welfare.databaseconnection.responseclasses.ClaimsData;
import com.example.android.welfare.MainActivity;
import com.example.android.welfare.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusActivity extends AppCompatActivity {
    ProgressBar application;
    ProgressBar claim;
    ProgressBar pension;
    TextView status_view_application;
    TextView progress_view_application;
    TextView status_view_claim;
    TextView progress_view_claim;
    TextView status_view_pension;
    TextView progress_view_pension;
    private SharedPreferences sharedPreferences;
    private APIService checkStatusUsingAPI;
    private String loginID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);
//        sharedPreferences = this.getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);
////        if (sharedPreferences.getString("loggedInID", "").isEmpty()) {
////            Intent loginIntent = new Intent(StatusActivity.this, LoginActivity.class);
////            startActivity(loginIntent);
////        } else {
        setContentView(R.layout.activity_status);


        checkStatusUsingAPI = APIUtils.getAPIService();
        loginID = sharedPreferences.getString("loggedInID", "");
        status_view_application = findViewById(R.id.info_application_status);
         progress_view_application = findViewById(R.id.info_application_progress);
         status_view_claim = findViewById(R.id.info_claim_status);
         progress_view_claim = findViewById(R.id.info_claim_progress);
         status_view_pension = findViewById(R.id.info_pension_status);
         progress_view_pension = findViewById(R.id.info_pension_progress);


        final Toolbar toolbar = findViewById(R.id.activity_toolbar);
        toolbar.setTitle(getString(R.string.activity_status_heading));
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


        checkStatusUsingAPI.checkStatus(loginID).enqueue(new Callback<List<ClaimsData>>() {
            @Override
            public void onResponse(Call<List<ClaimsData>> call, Response<List<ClaimsData>> response) {

                if(response.isSuccessful()) {
                    int i =0;
                    List<ClaimsData> response_body = response.body();
                    for (ClaimsData c : response_body) {
                        if (i == 0) {
                            int response_code = c.getResponseCode();
                            i++;
                        } else {
                            String application_type = c.getApplicationType();
                            String status = c.getStatus();


                            Toast.makeText(StatusActivity.this, "Request gave response", Toast.LENGTH_LONG).show();

                            if (application_type.equals("application")) {
                                if (status.equals("0")) {
                                    application = findViewById(R.id.progress_bar_application);
                                    application.setProgress(0);
                                    application.setScaleY(2f);
                                    status_view_application.setText(R.string.StatusActivity_application_submitted);
                                    progress_view_application.setText("0/5");

                                }
                                if (status.equals("1")) {
                                    application = findViewById(R.id.progress_bar_application);
                                    application.setProgress(20);
                                    application.setScaleY(2f);
                                    status_view_application.setText(R.string.StatusActivity_cleared_by_clerk);
                                    progress_view_application.setText("1/5");
                                }
                                if (status.equals("2")) {
                                    application = findViewById(R.id.progress_bar_application);
                                    application.setProgress(40);
                                    application.setScaleY(2f);
                                    status_view_application.setText(R.string.StatusActivity_cleared_accounting_officer);
                                    progress_view_application.setText("2/5");
                                }
                                if (status.equals("3")) {
                                    application = findViewById(R.id.progress_bar_application);
                                    application.setProgress(60);
                                    application.setScaleY(2f);
                                    status_view_application.setText(R.string.StatusActivity_cleared_by_higher_auth);
                                    progress_view_application.setText("3/5");
                                }
                                if (status.equals("4")) {
                                    application = findViewById(R.id.progress_bar_application);
                                    application.setProgress(80);
                                    application.setScaleY(2f);
                                    status_view_application.setText(R.string.StatusActivity_waiting_for_dispersal);
                                    progress_view_application.setText("4/5");
                                }
                                if (status.equals("5")) {
                                    application = findViewById(R.id.progress_bar_application);
                                    application.setProgress(100);
                                    application.setScaleY(2f);
                                    status_view_application.setText(R.string.StatusActivity_accepted);
                                    progress_view_application.setText("5/5");
                                }
                                if (status.equals("-1")) {
                                    application = findViewById(R.id.progress_bar_application);
                                    application.setProgress(100);
                                    application.getProgressDrawable().setColorFilter(
                                            Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                    application.setScaleY(2f);
                                    status_view_application.setText(R.string.StatusActivity_rejected);
                                    progress_view_application.setText("");
                                }

                            }

                            /*
                            CLAIMS
                            */
                            if (application_type.equals("claim")) {
                                if (status.equals("0")) {
                                    claim = findViewById(R.id.progress_bar_claim);
                                    claim.setProgress(0);
                                    claim.setScaleY(2f);
                                    status_view_claim.setText(R.string.StatusActivity_application_submitted);
                                    progress_view_claim.setText("0/5");

                                }
                                if (status.equals("1")) {
                                    claim = findViewById(R.id.progress_bar_claim);
                                    claim.setProgress(20);
                                    claim.setScaleY(2f);
                                    status_view_claim.setText(R.string.StatusActivity_cleared_by_clerk);
                                    progress_view_claim.setText("1/5");

                                }
                                if (status.equals("2")) {
                                    claim = findViewById(R.id.progress_bar_claim);
                                    claim.setProgress(40);
                                    claim.setScaleY(2f);
                                    status_view_claim.setText(R.string.StatusActivity_cleared_accounting_officer);
                                    progress_view_claim.setText("2/5");

                                }
                                if (status.equals("3")) {
                                    claim = findViewById(R.id.progress_bar_claim);
                                    claim.setProgress(60);
                                    claim.setScaleY(2f);
                                    status_view_claim.setText(R.string.StatusActivity_cleared_by_higher_auth);
                                    progress_view_claim.setText("3/5");

                                }
                                if (status.equals("4")) {
                                    claim = findViewById(R.id.progress_bar_claim);
                                    claim.setProgress(80);
                                    claim.setScaleY(2f);
                                    status_view_claim.setText(R.string.StatusActivity_waiting_for_dispersal);
                                    progress_view_claim.setText("4/5");

                                }
                                if (status.equals("5")) {
                                    claim = findViewById(R.id.progress_bar_claim);
                                    claim.setProgress(100);
                                    claim.setScaleY(2f);
                                    status_view_claim.setText(R.string.StatusActivity_accepted);
                                    progress_view_claim.setText("5/5");

                                }
                                if (status.equals("-1")) {
                                    claim = findViewById(R.id.progress_bar_claim);
                                    claim.setProgress(100);
                                    claim.setScaleY(2f);
                                    claim.getProgressDrawable().setColorFilter(
                                            Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                    status_view_claim.setText(R.string.StatusActivity_rejected);
                                    progress_view_claim.setText("");

                                }
                            }

                            /*
                            PENSION
                             */

                            if (application_type.equals("pension")) {
                                if (status.equals("0")) {
                                    pension = findViewById(R.id.progress_bar_pension);
                                    pension.setProgress(0);
                                    pension.setScaleY(2f);
                                    status_view_pension.setText(R.string.StatusActivity_cleared_by_clerk);
                                    progress_view_pension.setText("0/5");

                                } if (status.equals("1")) {
                                    pension = findViewById(R.id.progress_bar_pension);
                                    pension.setProgress(20);
                                    pension.setScaleY(2f);
                                    status_view_pension.setText(R.string.StatusActivity_cleared_by_clerk);
                                    progress_view_pension.setText("1/5");

                                } if (status.equals("2")) {
                                    pension = findViewById(R.id.progress_bar_pension);
                                    pension.setProgress(40);
                                    pension.setScaleY(2f);
                                    status_view_pension.setText(R.string.StatusActivity_cleared_by_clerk);
                                    progress_view_pension.setText("2/5");

                                } if (status.equals("3")) {
                                    pension = findViewById(R.id.progress_bar_pension);
                                    pension.setProgress(60);
                                    pension.setScaleY(2f);
                                    status_view_pension.setText(R.string.StatusActivity_cleared_by_clerk);
                                    progress_view_pension.setText("3/5");

                                } if (status.equals("4")) {
                                    pension = findViewById(R.id.progress_bar_pension);
                                    pension.setProgress(80);
                                    pension.setScaleY(2f);
                                    status_view_pension.setText(R.string.StatusActivity_cleared_by_clerk);
                                    progress_view_pension.setText("4/5");

                                }
                                if (status.equals("5")) {
                                    pension = findViewById(R.id.progress_bar_pension);
                                    pension.setProgress(100);
                                    pension.setScaleY(2f);
                                    status_view_pension.setText(R.string.StatusActivity_cleared_by_clerk);
                                    progress_view_pension.setText("5/5");

                                }
                                if (status.equals("-1")) {
                                    pension = findViewById(R.id.progress_bar_pension);
                                    pension.setProgress(100);
                                    pension.setScaleY(2f);
                                    pension.getProgressDrawable().setColorFilter(
                                            Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                    status_view_pension.setText(R.string.StatusActivity_rejected);
                                    progress_view_pension.setText("");

                                }
                            }


                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ClaimsData>> call, Throwable t) {
                Toast.makeText(StatusActivity.this, "Request failed to send", Toast.LENGTH_LONG).show();

            }
        });



    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.activity_button_home): {
                    Intent homeIntent = new Intent(StatusActivity.this, MainActivity.class);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homeIntent);
                    overridePendingTransition(R.anim.slide_left_to_right, R.anim.slide_right_to_left);
                    break;
                }
                default: {}
            }
        }
    };
}