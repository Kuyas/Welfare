package com.example.android.welfare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.msg91.sendotp.library.SendOtpVerification;

public class SignupActivity extends AppCompatActivity {
    private final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private OtpVerfication otpVerfication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button sendotp = (Button) findViewById(R.id.activity_signup_button_send_otp);
        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: implement checks for mobile number and password values
                otpVerfication = new OtpVerfication();
                String phoneNumber = "123456789";
                otpVerfication.setter(phoneNumber, SignupActivity.this);

                if(Build.VERSION.SDK_INT < 23){
                    otpVerfication.initiate();
                }else {
                    requestSMSPermission();
                }
            }
        });

        Button signupbutton = (Button) findViewById(R.id.activity_signup_button_signup);
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void requestSMSPermission() {
        int hasContactPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);

        if(hasContactPermission != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this , new String[] {Manifest.permission.RECEIVE_SMS}, REQUEST_CODE_ASK_PERMISSIONS);
        }else {
            otpVerfication.initiate();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                // Check if the only required permission has been granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("Permission", "Contact permission has now been granted. Showing result.");
                    otpVerfication.initiate();
                } else {
                    Log.i("Permission", "Contact permission was NOT granted.");
                }
                break;
        }
    }
}
