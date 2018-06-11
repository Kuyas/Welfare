package com.example.android.welfare;


import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {

    private Button editProfile;
    private Button renewMembership;
    private Button classChange;
    private Button applicationStatus;
    private Button claimStatus;
    private Button pensionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        toolbar.setTitle(getString(R.string.activity_main_heading));
        setSupportActionBar(toolbar);


        editProfile = findViewById(R.id.button_main_edit_profile);
        renewMembership = findViewById(R.id.button_main_renew_membership);
        classChange = findViewById(R.id.button_main_class_change);
        applicationStatus = findViewById(R.id.button_main_application_status);
        claimStatus = findViewById(R.id.button_main_claim_status);
        pensionStatus = findViewById(R.id.button_main_pension_status);

        editProfile.setOnClickListener(onClickListener);
        renewMembership.setOnClickListener(onClickListener);
        classChange.setOnClickListener(onClickListener);
        applicationStatus.setOnClickListener(onClickListener);
        claimStatus.setOnClickListener(onClickListener);
        pensionStatus.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case (R.id.button_main_edit_profile): {
                    Intent editProfileIntent = new Intent(MainActivity.this, UserDetailsActivity.class);
                    startActivity(editProfileIntent);
                    break;
                }
                case (R.id.button_main_renew_membership): {
                    Intent renewMembershipIntent = new Intent(MainActivity.this, RenewMembershipActivity.class);
                    startActivity(renewMembershipIntent);
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
                Snackbar logoutSnackbar = Snackbar.make(linearLayout, "You have successfully logged out", Snackbar.LENGTH_LONG);
                logoutSnackbar.show();
                //TODO: implement Logout functionality
                break;
            }
            case (R.id.button_menu_change_language): {
                LinearLayout linearLayout = findViewById(R.id.layout_activity_main);
                Snackbar changeLanguageSnackbar = Snackbar.make(linearLayout, "Redirect to Change Language page", Snackbar.LENGTH_LONG);
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
}
