package com.example.easynewspaper.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.easynewspaper.Activity.HomeBaseActivity;
import com.example.easynewspaper.DataStruct.Status;
import com.example.easynewspaper.Interface.Callback;
import com.example.easynewspaper.Activity.MainActivity;
import com.example.easynewspaper.R;
import com.example.easynewspaper.Utility.StatusCheck;
import com.example.easynewspaper.Utility.Web;
import org.json.JSONObject;

public class QuizFragment extends Fragment {
    View view;
    TextView tvSolved, tvQuestion;
    Button btnSubmit;
    EditText edtAns;
    Long quizId;
    String question;
    int solveCount;
    boolean isCorrect;

    String testQStr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_quiz, container, false);

        Web.GetQuiz(new Callback() {
            @Override
            public void isSuccessed(String response) {
                successedMethod(response);
            }

            @Override
            public void isFailed() {
                MainActivity.getInstance().sendToast("기사를 불러오는 데 실패했습니다.");
            }
        });

        return view;
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
                    quizId = data.getLong("quizId");
                    question = data.getString("content");
                    solveCount = data.getInt("todayQuizCount");

                    initView(question, solveCount);
                } else {
                    MainActivity.getInstance().sendToast(status.msg);
                }
            }
        } catch (Exception e) {
            MainActivity.getInstance().sendToast("올바르지 않은 값입니다.");
        }
    }

    void initView(String q, int cnt) {
        solveCount = cnt;
        tvSolved = view.findViewById(R.id.tvSolveCount);
        //tvSolved.setText("포인트를 받을 수 있는 횟수 : " + cnt + "/3");
        tvQuestion = view.findViewById(R.id.tvQuestion);
        //tvQuestion.setText("Q " + q);
        edtAns = view.findViewById(R.id.edtAnswer);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        new Thread(() -> {
            // 백그라운드 작업 수행

            tvSolved.post(() -> {
                tvSolved.setText("포인트를 받을 수 있는 횟수 : " + cnt + "/3");
            });
        }).start();

        new Thread(() -> {
            // 백그라운드 작업 수행

            tvQuestion.post(() -> {
                tvQuestion.setText("Q " + q);

                HomeBaseActivity.getInstance().closeLoading();
            });
        }).start();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtAns.getText().toString().isEmpty()) return;

                sendAnswer(edtAns.getText().toString());

                new Thread(() -> {
                    // 백그라운드 작업 수행

                    edtAns.post(() -> {
                        edtAns.setText("");
                    });
                }).start();
            }
        });
    }

    void sendAnswer(String request) {
        Web.solveQuiz(quizId, request, new Callback() {
            @Override
            public void isSuccessed(String response) {
                successedSolve(response);
            }

            @Override
            public void isFailed() {
                MainActivity.getInstance().sendToast("퀴즈를 해결하는 데 실패했습니다.");
            }
        });
    }

    void successedSolve(String response) {
        try {
            JSONObject resJson = new JSONObject(response);
            boolean isSuccess = resJson.getBoolean("isSuccess");
            int code = resJson.getInt("code");

            if (isSuccess) {
                Status status = StatusCheck.isSuccess(code);

                if (status.succesed) {
                    JSONObject data = resJson.getJSONObject("data");
                    isCorrect = data.getBoolean("isCorrect");
                    quizId = data.getLong("quizId");
                    question = data.getString("content");

                    Log.d("태그", quizId + " 번");

                    solved(isCorrect, question);
                } else {
                    MainActivity.getInstance().sendToast(status.msg);
                }
            }
        } catch (Exception e) {
            MainActivity.getInstance().sendToast("올바르지 않은 값입니다.");
        }
    }

    void solved(boolean isCorrect, String question) {
        if (isCorrect) {
            if (solveCount == 0) {
                MainActivity.getInstance().sendToast("정답입니다.");
            } else {
                MainActivity.getInstance().sendToast("정답입니다. 5p지급됩니다.");
            }
            initView(question, solveCount - 1);

        } else {
            MainActivity.getInstance().sendToast("오답입니다");
            initView(question, solveCount);
        }

    }
}
