package com.example.easynewspaper.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easynewspaper.Interface.Callback;
import com.example.easynewspaper.R;
import com.example.easynewspaper.Utility.Web;

public class UserInfoEditActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        MainActivity.getInstance().addActivity(this);

        EditText userPwEditTxt = findViewById(R.id.userPwEditTxt);
        EditText userPwConfirmEditTxt = findViewById(R.id.userPwConfirmEditTxt);
        EditText userNicknameEditTxt = findViewById(R.id.userNicknameEditTxt);

        ImageView passwordDismatchImgView = findViewById(R.id.PwDismatchImgView);

        findViewById(R.id.closeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ((TextView)findViewById(R.id.userIdTxt)).setText(Web.GetId());
        userNicknameEditTxt.setText(Web.GetNickname());

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
