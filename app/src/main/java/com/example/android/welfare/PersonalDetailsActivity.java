package com.example.android.welfare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class PersonalDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

//        LinearLayout activityPersonalLayout = findViewById(R.id.layout_activity_personal_details);
//        activityPersonalLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (! (v instanceof TextInputLayout)) {
//                    InputMethodManager inputMethodManager =
//                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                }
//                return true;
//            }
//        });

        final Button buttonNext = findViewById(R.id.button_personal_details_next);

        buttonNext.setOnClickListener(onClickListener);

        final Toolbar toolbar = findViewById(R.id.activity_toolbar);
        toolbar.setTitle(getString(R.string.activity_personal_heading));
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


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.button_personal_details_next): {
                    Intent familyDetailsIntent = new Intent(PersonalDetailsActivity.this,
                            FamilyDetailsActivity.class);
                    startActivity(familyDetailsIntent);
                }
                default: {}
            }
        }
    };

//    public boolean onTouchEvent(MotionEvent event) {
//        InputMethodManager inputMethodManager =
//                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//
//        return true;
//    }
}
