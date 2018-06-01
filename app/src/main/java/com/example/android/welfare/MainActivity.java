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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        toolbar.setTitle(getString(R.string.activity_main_heading));
        setSupportActionBar(toolbar);

    }


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
                //TODO: implement Logout functionality
                break;
            }
            default: {
                break;
            }
        }

        return true;
    }
}
