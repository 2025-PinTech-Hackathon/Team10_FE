package com.example.easynewspaper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easynewspaper.DataStruct.UserInfo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

enum EIntent {
    Login,
    Signup,
    Home,
    Profile,
    EditUserinfo,
}

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;

    public static MainActivity getInstance() {
        return instance;
    }

    static final List<Activity> instantiatedActivities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;

        //FileInputStream inFs = openFileInput("userInfo.json");

        //loadUserInfo(inFs);
        //loadLoginActivity();


        openIntent(EIntent.Login);
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
                break;
            case Signup:
                intent = new Intent(getApplicationContext(), SignupActivity.class);
                break;
            case Home:
                intent = new Intent(getApplicationContext(), HomeBaseActivity.class);
                break;
            case Profile:
                intent = new Intent(getApplicationContext(), ProfileActivity.class);
                break;
            case EditUserinfo:
                intent = new Intent(getApplicationContext(), UserInfoEditActivity.class);
                break;
            default:
                intent = new Intent(getApplicationContext(), HomeBaseActivity.class);
                break;
        }

        startActivity(intent);
    }

    public void addActivity(Activity activity) {
        instantiatedActivities.add(activity);
    }

    public void removeActivity(Activity activity) {
        instantiatedActivities.remove(activity);
    }

    public void closeAllActivities() {
        for (Activity activity : instantiatedActivities) {
            activity.finish();
        }
    }

    public void sendToast(String msg) {
        new Handler(Looper.getMainLooper()).post(() -> {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        });
    }
}

