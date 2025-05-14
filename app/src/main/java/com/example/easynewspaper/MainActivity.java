package com.example.easynewspaper;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

class UserInfo {
    String nickname;
    String loginId;
    String password;
}

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = new Intent(getApplicationContext(), NewsActivity.class);

        //startActivity(intent);

        /*
        try {
            FileInputStream inFs = openFileInput("userInfo.json");
            byte[] txt = new byte[30];
            inFs.read(txt);
            String str = new String(txt);

            inFs.close();
        } catch (IOException e) {

        }*/
    }
}

