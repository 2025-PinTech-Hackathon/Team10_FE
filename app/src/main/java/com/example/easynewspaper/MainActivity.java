package com.example.easynewspaper;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easynewspaper.DataStruct.UserInfo;

import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FileInputStream inFs = openFileInput("userInfo.json");

        //loadUserInfo(inFs);
        //loadLoginActivity();

        userInfo = new UserInfo();
        userInfo.setUserId(1);
        userInfo.setNickname("1");
        userInfo.setId("1");
        userInfo.setPw("1");

        Intent intent = new Intent(getApplicationContext(), HomeBaseActivity.class);

        startActivity(intent);
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

    void loadLoginActivity() {

    }

    void loadSignupActivity() {

    }
}

