package com.example.android.welfare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.android.welfare.Login.LoginActivity;
import com.example.android.welfare.UserDetails.PersonalDetailsActivity;

public class StatusActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        sharedPreferences = this.getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);
////        if (sharedPreferences.getString("loggedInID", "").isEmpty()) {
////            Intent loginIntent = new Intent(StatusActivity.this, LoginActivity.class);
////            startActivity(loginIntent);
////        } else {
            setContentView(R.layout.activity_status);

    }
}