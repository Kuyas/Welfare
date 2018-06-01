package com.example.android.welfare;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class UserDetailsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);


        toolbar = findViewById(R.id.activity_main_toolbar);
        toolbar.setTitle(getString(R.string.activity_main_heading));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(getDrawable(R.drawable.ic_arrow_back_black_24dp));

        tabLayout = findViewById(R.id.activity_user_details_tabs);

        tabLayout.addTab(tabLayout.newTab().setText("Personal Details"));
        tabLayout.addTab(tabLayout.newTab().setText("Trading Details"));
        tabLayout.addTab(tabLayout.newTab().setText("Other Details"));
        tabLayout.addTab(tabLayout.newTab().setText("Payment"));
    }
}
