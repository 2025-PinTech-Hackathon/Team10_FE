package com.example.easynewspaper;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easynewspaper.DataStruct.Status;
import com.example.easynewspaper.Interface.Callback;
import com.example.easynewspaper.Utility.StatusCheck;
import com.example.easynewspaper.Utility.Web;

import org.json.JSONObject;

public class LoginActivity extends Activity {
    EditText edtId, edtPwd;
    Button btnLogin, btnSignup;
    TextView tvWrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MainActivity.getInstance().addActivity(this);

        edtId = findViewById(R.id.edtId);
        edtPwd = findViewById(R.id.edtPwd);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Web.Login(edtId.getText().toString(), edtPwd.getText().toString(), new Callback() {
                    @Override
                    public void isSuccessed(String response) {
                        successedMethod(response);
                    }

                    @Override
                    public void isFailed() {
                        Toast.makeText(getApplicationContext(), "로그인 요청 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        MainActivity.getInstance().removeActivity(this);
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
                    boolean isLogin = data.getBoolean("isLogin");
                    if(isLogin) Logined(data.getLong("userId"));
                    else WrongLogin();

                } else {
                    Toast.makeText(getApplicationContext(), status.msg, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "올바르지 않은 값입니다.", Toast.LENGTH_SHORT).show();
        }
    }

    void Logined(Long userId) {
        Intent intent = new Intent(getApplicationContext(), HomeBaseActivity.class);

        intent.putExtra("id", userId);

        startActivity(intent);
    }

    void WrongLogin() {
        tvWrong = findViewById(R.id.tvWrong);
        tvWrong.setVisibility(View.VISIBLE);
    }
}
