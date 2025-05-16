package com.example.easynewspaper;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easynewspaper.DataStruct.UserInfo;

import java.io.FileInputStream;
import java.io.IOException;

enum EIntent {
    Login,
    Signup,
    Home,
    Profile,
}

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;

    public static MainActivity getInstance() {
        return instance;
    }

    public UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FileInputStream inFs = openFileInput("userInfo.json");

        //loadUserInfo(inFs);
        //loadLoginActivity();

        userInfo = new UserInfo();
        //userInfo.setUserId(1);
        //userInfo.setNickname("1");
        //userInfo.setId("1");
        //userInfo.setPw("1");

        openIntent(EIntent.Home);
    }

    UserInfo loadUserInfo(FileInputStream inFs){
        try {
            byte[] txt = new byte[30];
            inFs.read(txt);
            String str = new String(txt);

            inFs.close();
        } catch (IOException e) {

        }

        return null;
    }

    public void openIntent(EIntent eIntent) {
        Intent intent;

        switch (eIntent) {
            case Login:
                intent = new Intent(getApplicationContext(), LoginActivity.class);
            case Signup:
                intent = new Intent(getApplicationContext(), SignupActivity.class);
            case Home:
                intent = new Intent(getApplicationContext(), HomeBaseActivity.class);
            case Profile:
                intent = new Intent(getApplicationContext(), ProfileActivity.class);
            default:
                intent = new Intent(getApplicationContext(), HomeBaseActivity.class);
        }

        startActivity(intent);
    }

    public void loadLoginActivity() {

    }

    public void loadSignupActivity() {

    }

    public void loadHomeActivity() {

    }
}

