package com.example.easynewspaper.Activity;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easynewspaper.R;
import com.example.easynewspaper.Utility.Web;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        MainActivity.getInstance().addActivity(this);

        ((TextView)findViewById(R.id.profileNicknameTxt)).setText(Web.GetNickname() + "님\n안녕하세요!");

        findViewById(R.id.CloseProfileBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeBaseActivity.getInstance().removeBlur();

                finish();
            }
        });

        findViewById(R.id.EditUserInfoBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().openIntent(EIntent.EditUserinfo);
            }
        });

        findViewById(R.id.LogoutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().closeAllActivities();
                MainActivity.getInstance().openIntent(EIntent.Login);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        MainActivity.getInstance().removeActivity(this);
    }
}
