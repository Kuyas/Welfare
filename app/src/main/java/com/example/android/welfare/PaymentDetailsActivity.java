package com.example.android.welfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PaymentDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        final Button buttonNext = findViewById(R.id.button_payment_details_submit);


        buttonNext.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.button_payment_details_submit): {
                    // TODO: implement SUBMIT functionality
                }
                default: {
                    break;
                }
            }
        }
    };
}
