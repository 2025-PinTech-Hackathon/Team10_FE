package com.example.easynewspaper.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easynewspaper.DataStruct.Status;
import com.example.easynewspaper.Interface.Callback;
import com.example.easynewspaper.R;
import com.example.easynewspaper.Utility.StatusCheck;
import com.example.easynewspaper.Utility.Web;

import org.json.JSONObject;

public class NewsDetailActivity extends AppCompatActivity {
    TextView titleTxt;
    TextView dateAndReporterTxt;
    TextView contentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        MainActivity.getInstance().addActivity(this);

        titleTxt = findViewById(R.id.TitleTxtView);
        dateAndReporterTxt = findViewById(R.id.DateAndReporterTxtView);
        contentTxt = findViewById(R.id.ContentTxtView);

        Web.GetDetailNews(getIntent().getLongExtra("id", -1),
                new Callback() {
                    @Override
                    public void isSuccessed(String response) {
                        try {
                            JSONObject resJson = new JSONObject(response);
                            boolean isSuccess = resJson.getBoolean("isSuccess");
                            int code = resJson.getInt("code");

                            if (isSuccess) {
                                Status status = StatusCheck.isSuccess(code);

                                if (status.succesed) {
                                    new Handler(Looper.getMainLooper()).post(() -> {
                                        try {
                                            JSONObject data = resJson.getJSONObject("data");

                                            //Timestamp date = Timestamp.valueOf(data.getString("date"));

                                            titleTxt.setText(data.getString("title"));
                                            contentTxt.setText(data.getString("content"));
                                            dateAndReporterTxt.setText(data.getString("date") + " " + data.getString("reporter"));

                                            HomeBaseActivity.getInstance().closeLoading();
                                        }
                                        catch (Exception e) {

                                        }
                                    });
                                } else {
                                    MainActivity.getInstance().sendToast(status.msg);
                                }
                            }
                        } catch (Exception e) {
                            titleTxt.setText("기본 헤더");
                            contentTxt.setText("기본 내용");
                            dateAndReporterTxt.setText("기본 날짜 기본 기자");

                            MainActivity.getInstance().sendToast(e.getStackTrace().toString());
                        }
                    }

                    @Override
                    public void isFailed() {
                        titleTxt.setText("기본 헤더");
                        contentTxt.setText("기본 내용");
                        dateAndReporterTxt.setText("기본 날짜 기본 기자");

                        MainActivity.getInstance().sendToast("연결에 실패하였습니다.");
                    }
                });

        ImageButton backBtn = findViewById(R.id.BackBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIntent().removeExtra("id");

                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        MainActivity.getInstance().removeActivity(this);
    }
}
