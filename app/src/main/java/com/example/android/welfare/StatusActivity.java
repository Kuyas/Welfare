package com.example.android.welfare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.welfare.DatabaseConnection.APIService;
import com.example.android.welfare.DatabaseConnection.APIUtils;
import com.example.android.welfare.DatabaseConnection.ResponseClasses.ClaimsData;
import com.example.android.welfare.Login.LoginActivity;
import com.example.android.welfare.UserDetails.PersonalDetailsActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusActivity extends AppCompatActivity {
    ProgressBar application;
    ProgressBar claim;
    ProgressBar pension;
    TextView status_view;
    TextView progress_view;
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
        final TextView status_view = findViewById(R.id.info_application_status);
        final TextView progress_view = findViewById(R.id.info_application_progress);


        checkStatusUsingAPI.checkStatus(loginID).enqueue(new Callback<ClaimsData>() {
            @Override
            public void onResponse(Call<ClaimsData> call, Response<ClaimsData> response) {
                int response_code = response.body().getResponseCode();
                String status = response.body().getStatus();
                String application_type = response.body().getApplicationType();
                if (response_code == 200) {

                    if (application_type.equals("application")) {
                        if (status.equals("0")) {
                            application = findViewById(R.id.progress_bar_application);
                            application.setProgress(0);
                            application.setScaleY(2f);
                            status_view.setText(R.string.StatusActivity_application_submitted);
                            progress_view.setText("0/5");

                        }
                        if (status.equals("1")) {
                            application = findViewById(R.id.progress_bar_application);
                            application.setProgress(20);
                            application.setScaleY(2f);
                            status_view.setText(R.string.StatusActivity_cleared_by_clerk);
                            progress_view.setText("0/5");
                        }
                        if (status.equals("2")) {
                            application = findViewById(R.id.progress_bar_application);
                            application.setProgress(40);
                            application.setScaleY(3f);
                            status_view.setText(R.string.StatusActivity_cleared_accounting_officer);
                            progress_view.setText("2/5");
                        }
                        if (status.equals("3")) {
                            application = findViewById(R.id.progress_bar_application);
                            application.setProgress(60);
                            application.setScaleY(3f);
                            status_view.setText(R.string.StatusActivity_cleared_by_higher_auth);
                            progress_view.setText("3/5");
                        }
                        if (status.equals("4")) {
                            application = findViewById(R.id.progress_bar_application);
                            application.setProgress(80);
                            application.setScaleY(3f);
                            status_view.setText(R.string.StatusActivity_waiting_for_dispersal);
                            progress_view.setText("4/5");
                        }
                        if (status.equals("5")) {
                            application = findViewById(R.id.progress_bar_application);
                            application.setProgress(100);
                            application.setScaleY(3f);
                            status_view.setText(R.string.StatusActivity_accepted);
                            progress_view.setText("5/5");
                        }
                        if (status.equals("-1")) {
                            application = findViewById(R.id.progress_bar_application);
                            application.setProgress(100);
                            application.getProgressDrawable().setColorFilter(
                                    Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                            application.setScaleY(3f);
                            status_view.setText(R.string.StatusActivity_rejected);
                            progress_view.setText("");
                        }


                    }




                    Toast.makeText(StatusActivity.this, "Request gave response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ClaimsData> call, Throwable t) {

                Toast.makeText(StatusActivity.this, "Request gave erroneous response", Toast.LENGTH_LONG).show();

            }
        });


    }
}