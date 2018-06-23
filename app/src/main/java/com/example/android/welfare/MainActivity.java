package com.example.android.welfare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences flag;
    private Button buttonEditProfile;
    private Button buttonRenewMembership;
    private Button buttonClassChange;
    private Button buttonApplicationStatus;
    private Button buttonClaimStatus;
    private Button buttonPensionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flag = getSharedPreferences("flag", Context.MODE_PRIVATE);

        if (!flag.getBoolean("language", false)) {
            setContentView(R.layout.activity_lang);
            final SharedPreferences.Editor flagEditor = flag.edit();

            Button langEnglishButton = findViewById(R.id.button_lang_english);
            langEnglishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setLocale("en");

                    flagEditor.putBoolean("language", true);
                    flagEditor.apply();

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });

            Button langMalayalamButton = findViewById(R.id.button_lang_malayalam);
            langMalayalamButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setLocale("ml");

                    flagEditor.putBoolean("language", true);
                    flagEditor.apply();

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        } else {

            setContentView(R.layout.activity_main);

            buttonEditProfile = findViewById(R.id.button_main_edit_profile);
            buttonRenewMembership = findViewById(R.id.button_main_renew_membership);
            buttonClassChange = findViewById(R.id.button_main_class_change);
            buttonApplicationStatus = findViewById(R.id.button_main_application_status);
            buttonClaimStatus = findViewById(R.id.button_main_claim_status);
            buttonPensionStatus = findViewById(R.id.button_main_pension_status);

            buttonEditProfile.setOnClickListener(onClickListener);
            buttonRenewMembership.setOnClickListener(onClickListener);
            buttonClassChange.setOnClickListener(onClickListener);
            buttonApplicationStatus.setOnClickListener(onClickListener);
            buttonClaimStatus.setOnClickListener(onClickListener);
            buttonPensionStatus.setOnClickListener(onClickListener);

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
                    Intent editProfileIntent = new Intent(MainActivity.this, PersonalDetailsActivity.class);
                    startActivity(editProfileIntent);
                    break;
                }
                case (R.id.button_main_renew_membership): {
                    Intent renewMembershipIntent = new Intent(MainActivity.this, RenewMembershipActivity.class);
                    startActivity(renewMembershipIntent);
                    break;
                }
                case (R.id.button_main_class_change): {
                    Intent classchangeIntent = new Intent(MainActivity.this, ClassChangeActivity.class);
                    startActivity(classchangeIntent);
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

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                //TODO: implement Logout functionality
                break;
            }
            case (R.id.button_menu_change_language): {
                LinearLayout mainActivityLinearLayout = findViewById(R.id.layout_activity_main);
                Snackbar changeLanguageSnackbar = Snackbar.make(mainActivityLinearLayout, "Redirect to Change Language page", Snackbar.LENGTH_LONG);
                changeLanguageSnackbar.show();
                //TODO: redirect to Select Language activity
                break;
            }
            default: {
                break;
            }
        }

        return true;
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
}
