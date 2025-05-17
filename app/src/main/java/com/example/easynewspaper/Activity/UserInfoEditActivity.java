package com.example.easynewspaper.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easynewspaper.DataStruct.Status;
import com.example.easynewspaper.Interface.Callback;
import com.example.easynewspaper.R;
import com.example.easynewspaper.Utility.StatusCheck;
import com.example.easynewspaper.Utility.Web;

import org.json.JSONObject;

public class UserInfoEditActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        MainActivity.getInstance().addActivity(this);

        EditText userPwEditTxt = findViewById(R.id.userPwEditTxt);
        EditText userPwConfirmEditTxt = findViewById(R.id.userPwConfirmEditTxt);
        EditText userNicknameEditTxt = findViewById(R.id.userNicknameEditTxt);

        TextView idTxt = findViewById(R.id.userIdTxt);

        ImageView passwordDismatchImgView = findViewById(R.id.PwDismatchImgView);

        findViewById(R.id.closeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Web.getMyPageInfo(new Callback() {
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

                            String id = data.getString("loginId");
                            String nickname = data.getString("nickname");

                            new Handler(Looper.getMainLooper()).post(() -> {
                                idTxt.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        idTxt.setText(id);
                                    }
                                });

                                userNicknameEditTxt.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        userNicknameEditTxt.setText(nickname);
                                    }
                                });
                            });
                        } else {
                            MainActivity.getInstance().sendToast(status.msg);
                        }
                    }
                } catch (Exception e) {
                    MainActivity.getInstance().sendToast("올바르지 않은 값입니다.");
                }
            }

            @Override
            public void isFailed() {
                MainActivity.getInstance().sendToast("마이페이지를 불러오는 데 실패했습니다.");
            }
        });

        userPwConfirmEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (userPwEditTxt.getText().toString().equals(s.toString())) {
                    passwordDismatchImgView.setVisibility(View.INVISIBLE);
                }
                else {
                    passwordDismatchImgView.setVisibility(View.VISIBLE);
                }
            }
        });

        findViewById(R.id.editBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordDismatchImgView.getVisibility() == View.INVISIBLE) {
                    Web.EditUserInfo(
                            userNicknameEditTxt.getText().toString(),
                            userPwEditTxt.getText().toString(),
                            new Callback() {
                                @Override
                                public void isSuccessed(String response) {
                                    finish();
                                }

                                @Override
                                public void isFailed() {

                                }
                            }
                    );
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        MainActivity.getInstance().removeActivity(this);
    }
}
