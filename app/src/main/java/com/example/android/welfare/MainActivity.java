package com.example.android.welfare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.android.welfare.databaseconnection.APIService;
import com.example.android.welfare.login.LanguageActivity;
import com.example.android.welfare.login.LoginActivity;
import com.example.android.welfare.userdetails.ClassChangeActivity;
import com.example.android.welfare.userdetails.PersonalDetailsActivity;
import com.example.android.welfare.userdetails.RenewMembershipActivity;
import com.example.android.welfare.userdetails.StatusActivity;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Button buttonEditProfile;
    private Button buttonRenewMembership;
    private Button buttonClassChange;
    private Button buttonApplicationStatus;
    private Button buttonClaimStatus;
    private Button buttonPensionStatus;
    private APIService statusUsingAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);

        if (!sharedPreferences.getBoolean("language", false)) {
            Intent languageIntent = new Intent(MainActivity.this, LanguageActivity.class);
            startActivity(languageIntent);
        } else if (sharedPreferences.getString("loggedInID", "").isEmpty()){
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        } else {
            setContentView(R.layout.activity_main);

            buttonEditProfile = findViewById(R.id.button_main_edit_profile);
            buttonRenewMembership = findViewById(R.id.button_main_renew_membership);
            buttonClassChange = findViewById(R.id.button_main_class_change);
            buttonClaimStatus = findViewById(R.id.button_main_claim_status);

            buttonEditProfile.setOnClickListener(onClickListener);
            buttonRenewMembership.setOnClickListener(onClickListener);
            buttonClassChange.setOnClickListener(onClickListener);
            buttonClaimStatus.setOnClickListener(onClickListener);

            final Toolbar toolbar = findViewById(R.id.activity_toolbar);
            toolbar.setTitle(getString(R.string.activity_main_heading));
            setSupportActionBar(toolbar);

        }
    }




    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case (R.id.button_main_edit_profile): {
                    if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                        Intent editProfileIntent = new Intent(MainActivity.this, PersonalDetailsActivity.class);
                        startActivity(editProfileIntent);
                    } else {
                        LinearLayout linearLayout = findViewById(R.id.layout_activity_main);
                        Snackbar noConnectionSnackbar = Snackbar.make(linearLayout,
                                getString(R.string.internet_connection_error_message), Snackbar.LENGTH_LONG);
                        noConnectionSnackbar.show();
                    }
                    break;
                }
                case (R.id.button_main_renew_membership): {
                    if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                        Intent renewMembershipIntent = new Intent(MainActivity.this, RenewMembershipActivity.class);
                        startActivity(renewMembershipIntent);
                    } else {
                        LinearLayout linearLayout = findViewById(R.id.layout_activity_main);
                        Snackbar noConnectionSnackbar = Snackbar.make(linearLayout,
                                getString(R.string.internet_connection_error_message), Snackbar.LENGTH_LONG);
                        noConnectionSnackbar.show();
                    }
                    break;
                }
                case (R.id.button_main_class_change): {
                    if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                        Intent classchangeIntent = new Intent(MainActivity.this, ClassChangeActivity.class);
                        startActivity(classchangeIntent);
                    } else {
                        LinearLayout linearLayout = findViewById(R.id.layout_activity_main);
                        Snackbar noConnectionSnackbar = Snackbar.make(linearLayout,
                                getString(R.string.internet_connection_error_message), Snackbar.LENGTH_LONG);
                        noConnectionSnackbar.show();
                    }
                    break;
                }

                case (R.id.button_main_claim_status): {
                    if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                        Intent statusCheckIntent = new Intent(MainActivity.this, StatusActivity.class);
                        startActivity(statusCheckIntent);
                    } else {
                        LinearLayout linearLayout = findViewById(R.id.layout_activity_main);
                        Snackbar noConnectionSnackbar = Snackbar.make(linearLayout,
                                getString(R.string.internet_connection_error_message), Snackbar.LENGTH_LONG);
                        noConnectionSnackbar.show();
                    }

//
                    break;

                }
                default: {
                    break;
                }
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.button_menu_logout): {
                LinearLayout linearLayout = findViewById(R.id.layout_activity_main);
                Snackbar snackbar = Snackbar.make(linearLayout, "You have successfully logged out", Snackbar.LENGTH_LONG);
                snackbar.show();
                sharedPreferences.edit().remove("loggedInID").apply();
                sharedPreferences.edit().remove("mobile_number").apply();
                sharedPreferences.edit().remove("password").apply();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            }
            case (R.id.button_menu_change_language): {
                Intent langActivityIntent = new Intent(MainActivity.this, LanguageActivity.class);
                startActivity(langActivityIntent);
                break;
            }
            default: {
                break;
            }
        }

        return true;
    }

    public void openBrowser(View view){

        //Get url from tag
        String url = (String)view.getTag();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);

        //pass the url to intent data
        intent.setData(Uri.parse(url));

        startActivity(intent);
    }
}


