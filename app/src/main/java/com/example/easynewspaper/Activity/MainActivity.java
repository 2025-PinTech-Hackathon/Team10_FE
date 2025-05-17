package com.example.easynewspaper.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easynewspaper.DataStruct.UserInfo;
import com.example.easynewspaper.R;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

enum EIntent {
    Login,
    Signup,
    Home,
    Profile,
    EditUserinfo,
    Item,
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

        ImageView LogoView = findViewById(R.id.Logo);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int i = 0;
            @Override
            public void run() {
                i++;
                LogoView.setAlpha(i * 0.05f);

                if (i >= 20){
                    cancel();
                    openIntent(EIntent.Login);
                }
            }
        }, 0, 100); // 0ms 후 시작, 100ms마다 반복

        /*
        UserInfo userInfo = loadUserInfo();

        if (userInfo != null) {
            Web.Login(userInfo.getId(), userInfo.getPw(), new Callback() {
                @Override
                public void isSuccessed(String response) {
                    try {
                        JSONObject resJson = new JSONObject(response);
                        boolean isSuccess = resJson.getBoolean("isSuccess");
                        int code = resJson.getInt("code");

                        if (isSuccess) {
                            Status status = StatusCheck.isSuccess(code);

                            if (status.succesed) {
                                JSONObject data = resJson.getJSONObject("data");
                                boolean isLogin = data.getBoolean("isLogin");
                                if(isLogin) {
                                    openIntent(EIntent.Home);
                                }
                                else {
                                    openIntent(EIntent.Login);
                                }

                            } else {
                                sendToast(status.msg);
                            }
                        }
                    } catch (Exception e) {
                        sendToast("올바르지 않은 값입니다.");
                    }
                }

                @Override
                public void isFailed() {

                }
            });
        }*/
    }

    UserInfo loadUserInfo(){
        UserInfo userInfo = null;
        FileInputStream inFs;
        try {
            inFs = openFileInput("UserInfo.json");
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            String userStr = (new String(txt)).trim();

            inFs.close();

            try {
                JSONObject jsonObject = new JSONObject(userStr);
                String id = jsonObject.getString("id");
                String pw = jsonObject.getString("pw");

                userInfo.setId(id);
                userInfo.setPw(pw);

                return userInfo;
            }
            catch (Exception e) {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
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
            case Item:
                intent = new Intent(getApplicationContext(), ItemActivity.class);
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
        new Handler(Looper.getMainLooper()).post(() -> {
            for (Activity activity : instantiatedActivities) {
                activity.finish();
            }
        });
    }

    public void sendToast(String msg) {
        new Handler(Looper.getMainLooper()).post(() -> {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View layout = inflater.inflate(R.layout.toast_custom, null);

            TextView text = layout.findViewById(R.id.toastTxt);
            text.setText(msg);

            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();

            //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        });
    }
}

