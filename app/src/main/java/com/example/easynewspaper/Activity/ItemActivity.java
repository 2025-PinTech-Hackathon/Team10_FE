package com.example.easynewspaper.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easynewspaper.DataStruct.Status;
import com.example.easynewspaper.Interface.Callback;
import com.example.easynewspaper.R;
import com.example.easynewspaper.Utility.StatusCheck;
import com.example.easynewspaper.Utility.Web;

import org.json.JSONObject;

public class ItemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        TextView pointTxt = findViewById(R.id.pointTxt);

        Web.getMyPage(new Callback() {
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

                            new Handler(Looper.getMainLooper()).post(() -> {
                                pointTxt.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            pointTxt.setText("point : " + data.getLong("point"));
                                        }
                                        catch (Exception e) {
                                            MainActivity.getInstance().sendToast("포인트 조회에 실패했습니다.");
                                        }
                                    }
                                });
                            });
                        } else {
                            MainActivity.getInstance().sendToast(status.msg);
                        }
                    }
                } catch (Exception e) {
                    MainActivity.getInstance().sendToast("포인트 조회에 실패했습니다.");
                }
            }

            @Override
            public void isFailed() {

            }
        });

        findViewById(R.id.PurchaseItem01ImgBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Web.purchaseItem(1, new Callback() {
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

                                    if (data.getBoolean("isPurchase")) {
                                        //MainActivity.getInstance().sendToast("상품 '" + data.getString("itemName") + "' 구매에 성공하였습니다.");
                                        MainActivity.getInstance().sendToast("상품 '온라인 상품권' 구매에 성공하였습니다.");

                                        new Handler(Looper.getMainLooper()).post(() -> {
                                            pointTxt.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        pointTxt.setText("point : " + data.getLong("reservedPoint"));
                                                    }
                                                    catch (Exception e) {
                                                        MainActivity.getInstance().sendToast("남은 포인트 조회에 실패했습니다.");
                                                    }
                                                }
                                            });
                                        });
                                    }
                                    else {
                                        MainActivity.getInstance().sendToast("포인트가 부족합니다.");
                                    }
                                } else {
                                    MainActivity.getInstance().sendToast(status.msg);
                                }
                            }
                        } catch (Exception e) {
                            MainActivity.getInstance().sendToast("구매에 실패했습니다.");
                        }
                    }

                    @Override
                    public void isFailed() {

                    }
                });
            }
        });

        findViewById(R.id.PurchaseItem02ImgBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Web.purchaseItem(2, new Callback() {
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

                                    if (data.getBoolean("isPurchase")) {
                                        //MainActivity.getInstance().sendToast("상품 '" + data.getString("itemName") + "' 구매에 성공하였습니다.");
                                        MainActivity.getInstance().sendToast("상품 '햄버거 기프티콘' 구매에 성공하였습니다.");

                                        new Handler(Looper.getMainLooper()).post(() -> {
                                            pointTxt.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        pointTxt.setText("point : " + data.getLong("reservedPoint"));
                                                    }
                                                    catch (Exception e) {
                                                        MainActivity.getInstance().sendToast("남은 포인트 조회에 실패했습니다.");
                                                    }
                                                }
                                            });
                                        });
                                    }
                                    else {
                                        MainActivity.getInstance().sendToast("포인트가 부족합니다.");
                                    }
                                } else {
                                    MainActivity.getInstance().sendToast(status.msg);
                                }
                            }
                        } catch (Exception e) {
                            MainActivity.getInstance().sendToast("구매에 실패했습니다.");
                        }
                    }

                    @Override
                    public void isFailed() {

                    }
                });
            }
        });

        findViewById(R.id.closeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
