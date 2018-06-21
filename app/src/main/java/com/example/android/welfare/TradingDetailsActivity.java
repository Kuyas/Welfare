package com.example.android.welfare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TradingDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading_details);

        final Button buttonNext = findViewById(R.id.button_trading_details_next);


        buttonNext.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.button_trading_details_next): {
                    Intent otherDetailsIntent = new Intent(TradingDetailsActivity.this,
                            OtherDetailsActivity.class);
                    startActivity(otherDetailsIntent);
                }
                default: {
                    break;
                }
            }
        }
    };
}
