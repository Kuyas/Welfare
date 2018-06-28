package com.example.android.welfare;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;

import static android.content.ContentValues.TAG;

public class OtpVerificationActivity extends AppCompatActivity implements VerificationListener{
    private final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private Verification mobileVerification;
    private TextView message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        message = (TextView) findViewById(R.id.activity_otp_verification_textview_message);
        Button resendButton = (Button) findViewById(R.id.activity_otp_verification_button_resend);
        TextInputEditText otpText = (TextInputEditText) findViewById(R.id.activity_otp_verification_edittext_otp);

        if (getIntent().getExtras()!=null) {
            mobileVerification = SendOtpVerification.createSmsVerification(
                    SendOtpVerification
                            .config("+91" + getIntent().getExtras().getString("phonenumber"))
                            .context(this)
                            .autoVerification(true)
                            .build(), this);
            sendForOTP();
        } else {
            finish();
        }

        otpText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length()==4) {
                    mobileVerification.verify(s.toString());
                }
            }
        });

        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendForOTP();
            }
        });
    }

    public void sendForOTP() {
        if(Build.VERSION.SDK_INT < 23){
            mobileVerification.initiate();
        }else {
            requestSMSPermission();
        }
    }

    private void requestSMSPermission() {
        int hasContactPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);

        if(hasContactPermission != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this , new String[] {Manifest.permission.RECEIVE_SMS}, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            mobileVerification.initiate();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                // Check if the only required permission has been granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("Permission", "Contact permission has now been granted. Showing result.");
                    mobileVerification.initiate();
                } else {
                    Log.i("Permission", "Contact permission was NOT granted.");
                    message.setText("Please enter OTP manually");
                }
                break;
        }
    }

    @Override
    public void onInitiated(String response) {
        Log.d(TAG, "Initialized!" + response);
        message.setText(R.string.activity_otp_verification_textview_otp_sent);
    }

    @Override
    public void onInitiationFailed(Exception exception) {
        Log.e(TAG, "Verification initialization failed: " + exception.getMessage());
        message.setText(R.string.activity_otp_verification_textview_otp_not_sent);
    }

    @Override
    public void onVerified(String response) {
        Log.d(TAG, "Verified!\n" + response);
        message.setText(R.string.activity_otp_verification_textview_otp_verified);
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onVerificationFailed(Exception exception) {
        Log.e(TAG, "Verification failed: " + exception.getMessage());
        message.setText(R.string.activity_otp_verification_textview_otp_failed);
    }
}

