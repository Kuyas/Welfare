package com.example.android.welfare.userdetails;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.welfare.login.LoginActivity;
import com.example.android.welfare.databaseconnection.APIService;
import com.example.android.welfare.databaseconnection.APIUtils;
import com.example.android.welfare.databaseconnection.responseclasses.TurnoverData;
import com.example.android.welfare.MainActivity;
import com.example.android.welfare.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassChangeActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private APIService turnoverUsingAPI;
    private String loginID;
    private String turnoverText;
    TextView oldTurnover;
    String s;
    TextView oldClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("loggedInID", "").isEmpty()){
            //TODO: Remove the negation

            Intent loginIntent = new Intent(ClassChangeActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        } else {
            setContentView(R.layout.activity_class_change);


            final Toolbar toolbar = findViewById(R.id.activity_toolbar);
            toolbar.setTitle(getString(R.string.activity_class_change_heading));
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
                    Intent homeIntent = new Intent(ClassChangeActivity.this, MainActivity.class);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homeIntent);
                    overridePendingTransition(R.anim.slide_left_to_right, R.anim.slide_right_to_left);
                }
            });


            turnoverUsingAPI = APIUtils.getAPIService();
            loginID = sharedPreferences.getString("loggedInID", "");
            oldClass = findViewById(R.id.activity_classchange_old_class);
            oldTurnover = findViewById(R.id.activity_classchange_old_turnover);
            oldTurnover.setText("");


            turnoverUsingAPI.getTurnoverData(loginID).enqueue(new Callback<TurnoverData>() {
                @Override
                public void onResponse(Call<TurnoverData> call, Response<TurnoverData> response) {
                        int response_code = response.body().getResponseCode();
                        if(response_code == 200){
                             turnoverText = response.body().getTurnover();
                             s = turnoverText;
                            oldTurnover.setText(turnoverText);

                        }
                }

                @Override
                public void onFailure(Call<TurnoverData> call, Throwable t) {
                    Toast.makeText(ClassChangeActivity.this, "Request failed to send", Toast.LENGTH_LONG).show();

                }
            });



//            oldTurnover = findViewById(R.id.activity_classchange_old_turnover);
//            String s = oldTurnover.getText().toString();
//            Float f = Float.parseFloat(s);
            Toast.makeText(ClassChangeActivity.this, s, Toast.LENGTH_LONG).show();
//            if(f <= 1000000.00){
//                oldClass.setText("D");
//
//            }else if( f > 1000000.00 && f <= 2500000.00){
//                oldClass.setText("C");
//            }else if( f > 2500000.00 && f <= 5000000.00){
//                oldClass.setText("B");
//            }else{
//                oldClass.setText("A");
//            }


        }
    }
}
