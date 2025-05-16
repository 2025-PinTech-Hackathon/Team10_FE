package com.example.easynewspaper.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easynewspaper.DataStruct.Status;
import com.example.easynewspaper.Interface.Callback;
import com.example.easynewspaper.R;
import com.example.easynewspaper.Utility.StatusCheck;
import com.example.easynewspaper.Utility.Web;

import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText userPwEditTxt = findViewById(R.id.userPwEditTxt);
        EditText userPwConfirmEditTxt = findViewById(R.id.userPwConfirmEditTxt);
        ImageButton signupBtn = findViewById(R.id.signupBtn);
        ImageButton closeBtn = findViewById(R.id.closeBtn);
        ImageView passwordDismatchImgView = findViewById(R.id.PwDismatchImgView);

        MainActivity.getInstance().addActivity(this);

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

        signupBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (passwordDismatchImgView.getVisibility() == View.VISIBLE) { //Password dismatch

                }
                else if (passwordDismatchImgView.getVisibility() == View.INVISIBLE){ //Password match
                    if (!((EditText)findViewById(R.id.userIdEditTxt)).getText().toString().isEmpty() &&
                            !((EditText)findViewById(R.id.userIdEditTxt)).getText().toString().isEmpty() &&
                            !userPwEditTxt.getText().toString().isEmpty()) {
                        String nickname = ((EditText)findViewById(R.id.userNicknameEditTxt)).getText().toString();
                        String id = ((EditText)findViewById(R.id.userIdEditTxt)).getText().toString();
                        String pw = userPwEditTxt.getText().toString();
                        Web.Signup(
                                nickname,
                                id,
                                pw,
                                new Callback() {
                                    @Override
                                    public void isSuccessed(String response) {
                                        successedMethod(nickname, id, pw, response);
                                    }

                                    @Override
                                    public void isFailed() {

                                    }
                                }
                        );
                    }
                }
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        MainActivity.getInstance().removeActivity(this);
    }

    void successedMethod(String nickname, String id, String pw, String response) {
        try {
            JSONObject resJson = new JSONObject(response);
            boolean isSuccess = resJson.getBoolean("isSuccess");
            int code = resJson.getInt("code");

            JSONObject data = resJson.getJSONObject("data");

            if (!data.isNull("userId")) { //unique id
                Status status = StatusCheck.isSuccess(code);

                if (status.succesed) {
                    MainActivity.getInstance().openIntent(EIntent.Home);
                }
                else {
                    MainActivity.getInstance().sendToast(status.msg);
                }
            }
            else { //duplicated id
                ImageView duplicatedIdImgView = findViewById(R.id.DuplicatedIdImgView);
                duplicatedIdImgView.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception e){
            MainActivity.getInstance().sendToast(e.getStackTrace().toString());
        }
    }
}
