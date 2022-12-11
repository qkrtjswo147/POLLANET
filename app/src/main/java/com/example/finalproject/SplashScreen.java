package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.finalproject.LoginPage.LoginActivity;
import com.example.finalproject.LoginPage.shared_preferences;

public class SplashScreen extends AppCompatActivity {

    Intent intent = null;
    CheckBox auto_login_checkBox;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        auto_login_checkBox = findViewById(R.id.auto_login_checkbox);

        imageView=(ImageView) findViewById(R.id.splpollanet);
        Glide.with(this).load(R.drawable.mobile_splash).into(imageView);

    }
        //** 앱 시작후 첫 화면이 Splash 이기때문에 여기서 shared_preferences를 선언하여
        // 로그인 페이지로갈지 자동 로그인하여 메인 페이지로 갈지 iF문*/
    @Override
    protected void onStart() {
        super.onStart();
        Handler mHandler=new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (shared_preferences.get_user_email(SplashScreen.this).length() > 0) {
                    intent = new Intent(SplashScreen.this, MainActivity.class);
                } else if(shared_preferences.get_user_email(SplashScreen.this).length() > 0 && !auto_login_checkBox.isChecked()) {
                    intent=new Intent(SplashScreen.this,LoginActivity.class);
                } else {
                    intent=new Intent(SplashScreen.this,LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}