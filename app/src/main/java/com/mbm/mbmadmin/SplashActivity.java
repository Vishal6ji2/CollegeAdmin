package com.mbm.mbmadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mbm.mbmadmin.Activities.EmailVerifyActivity;
import com.mbm.mbmadmin.Activities.HomeActivity;
import com.mbm.mbmadmin.Activities.OtpVerifyActivity;
import com.mbm.mbmadmin.Sessions.LoginSession;

public class SplashActivity extends AppCompatActivity {


    LoginSession loginSession;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loginSession = new LoginSession(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                boolean isLoggedIn = loginSession.checkLogin();

                if (isLoggedIn){
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                }else {
                    startActivity(new Intent(SplashActivity.this, EmailVerifyActivity.class));
                }
                finish();
            }
        },3000);

    }
}