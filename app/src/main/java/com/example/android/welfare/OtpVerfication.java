package com.example.android.welfare;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;

import static android.content.ContentValues.TAG;

public abstract class OtpVerfication extends AppCompatActivity implements VerificationListener{
    private Verification mobileVerification;
    private Context CallerActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras()!=null) {
            mobileVerification = SendOtpVerification.createSmsVerification(
                    SendOtpVerification
                            .config("+91" + getIntent().getExtras().getString("phonenumber"))
                            .context(this)
                            .autoVerification(true)
                            .build(), this);
        } else {
            // TODO: show error on not receiving phone number
            finish();
        }
    }

    public void setter(String phoneNumber, Context that) {
        mobileVerification = SendOtpVerification.createSmsVerification(
                SendOtpVerification
                        .config("+91" + phoneNumber)
                        .context(that)
                        .autoVerification(true)
                        .build(), this);
    }

    public void initiate() {
        mobileVerification.initiate();
    }

    public void verify(String otp_code) {
        mobileVerification.verify(otp_code);
    }

    public void resend(String type) {
        mobileVerification.resend(type);
    }


    @Override
    public void onInitiated(String response) {
        Log.d(TAG, "Initialized!" + response);
        //OTP successfully resent/sent.
    }

    @Override
    public void onInitiationFailed(Exception exception) {
        Log.e(TAG, "Verification initialization failed: " + exception.getMessage());
        //sending otp failed.
    }

    @Override
    public void onVerified(String response) {
        Log.d(TAG, "Verified!\n" + response);
        //OTP verified successfully.
    }

    @Override
    public void onVerificationFailed(Exception exception) {
        Log.e(TAG, "Verification failed: " + exception.getMessage());
        //OTP  verification failed.
    }
}
