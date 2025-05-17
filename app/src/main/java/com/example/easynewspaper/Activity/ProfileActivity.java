package com.example.easynewspaper.Activity;


import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easynewspaper.DataStruct.Status;
import com.example.easynewspaper.Interface.Callback;
import com.example.easynewspaper.R;
import com.example.easynewspaper.Utility.StatusCheck;
import com.example.easynewspaper.Utility.Web;

import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    TextView PointTxt;
    TextView nicknameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        MainActivity.getInstance().addActivity(this);


        nicknameTxt = findViewById(R.id.profileNicknameTxt);

        PointTxt = ((TextView) findViewById(R.id.pointTxt));

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
    protected void onResume() {
        super.onResume();

        Web.getMyPage(new Callback() {
            @Override
            public void isSuccessed(String response) {
                successedMethod(response);
            }

            @Override
            public void isFailed() {
                MainActivity.getInstance().sendToast("마이페이지를 불러오는 데 실패했습니다.");
            }
        });
    }

    void successedMethod(String response) {
        try {
            JSONObject resJson = new JSONObject(response);
            boolean isSuccess = resJson.getBoolean("isSuccess");
            int code = resJson.getInt("code");

            if (isSuccess) {
                Status status = StatusCheck.isSuccess(code);

                if (status.succesed) {
                    JSONObject data = resJson.getJSONObject("data");

                    setInfoTxt(
                            data.getString("nickname"),
                            data.getLong("point")
                    );
                } else {
                    MainActivity.getInstance().sendToast(status.msg);
                }
            }
        } catch (Exception e) {
            MainActivity.getInstance().sendToast("올바르지 않은 값입니다.");
        }
    }

    void setInfoTxt(String nickname, long point) {
        new Thread(() -> {

            nicknameTxt.post(() -> {
                nicknameTxt.setText(nickname + "님\n안녕하세요!");
            });


            PointTxt.post(() -> {
                PointTxt.setText("Point : " + point);
            });
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        MainActivity.getInstance().removeActivity(this);
    }
}
