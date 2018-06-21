package com.example.android.welfare;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class RenewMembershipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew_membership);

        final Toolbar toolbar;

        toolbar = findViewById(R.id.activity_main_toolbar);
        toolbar.setTitle(getString(R.string.activity_main_heading));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(getDrawable(R.drawable.ic_arrow_back_black_24dp));
    }
}
