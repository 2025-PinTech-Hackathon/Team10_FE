package com.example.easynewspaper.Unuse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easynewspaper.DataStruct.Status;
import com.example.easynewspaper.Interface.Callback;
import com.example.easynewspaper.MainActivity;
import com.example.easynewspaper.NewsDetailActivity;
import com.example.easynewspaper.R;
import com.example.easynewspaper.Utility.StatusCheck;
import com.example.easynewspaper.Utility.Web;

import org.json.JSONObject;

public class NewsCustomListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button detailNewsBtn = findViewById(R.id.LoadNewsBtn);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list_view);

        detailNewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Web.GetDetailNews(
                        MainActivity.userInfo.getUserId(),
                        Long.parseLong(findViewById(R.id.newsIdTxt).toString()),
                        new Callback() {
                            @Override
                            public void isSuccessed(String response) {
                                successedMethod(response);
                            }

                            @Override
                            public void isFailed() {
                                Toast.makeText(getApplicationContext(),
                                        "연결에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
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

                    //Timestamp date = Timestamp.valueOf(data.getString("date"));

                    Intent intent = new Intent(getApplicationContext(), NewsDetailActivity.class);

                    intent.putExtra("title", data.getString("title"));
                    intent.putExtra("content", data.getString("content"));
                    intent.putExtra("repoter", data.getString("repoter"));
                    intent.putExtra("date", data.getString("date"));

                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            status.msg, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {

            Intent intent = new Intent(getApplicationContext(), NewsDetailActivity.class);

            intent.putExtra("title", "기본 헤더");
            intent.putExtra("content", "기본 내용");
            intent.putExtra("repoter", "기본 기자");
            intent.putExtra("date", "기본 날짜");

            startActivity(intent);

            Toast.makeText(getApplicationContext(),
                    "올바르지 않은 값입니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
