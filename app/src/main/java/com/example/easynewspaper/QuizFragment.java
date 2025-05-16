package com.example.easynewspaper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.easynewspaper.DataStruct.Status;
import com.example.easynewspaper.Interface.Callback;
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
    int textSize;
    int lineGap;
    boolean isCorrect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_quiz, container, false);

        Web.GetQuiz(MainActivity.getInstance().userInfo.getUserId(), new Callback() {
            @Override
            public void isSuccessed(String response) {
                successedMethod(response);
            }

            @Override
            public void isFailed() {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(),
                            "기사를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                });
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
                    textSize = data.getInt("textSize");
                    lineGap = data.getInt("lineGap");

                    initView(question, solveCount, textSize, lineGap);
                } else {
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(),
                                status.msg, Toast.LENGTH_SHORT).show();
                    });
                }
            }
        } catch (Exception e) {
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(),
                        "올바르지 않은 값입니다.", Toast.LENGTH_SHORT).show();
            });
        }
    }

    void initView(String q, int cnt, int tSize, int lGap) {
        int left = 3 - cnt;
        if(left < 0) left = 0;

        tvSolved = view.findViewById(R.id.tvSolveCount);
        tvSolved.setText("포인트를 받을 수 있는 횟수 : " + left + "/3");
        tvQuestion = view.findViewById(R.id.tvQuestion);
        tvQuestion.setText("Q " + q);
        edtAns = view.findViewById(R.id.edtAnswer);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtAns.getText().toString().isEmpty()) return;

                sendAnswer(edtAns.getText().toString());
            }
        });
    }

    void sendAnswer(String request) {
        Web.solveQuiz(MainActivity.getInstance().userInfo.getUserId(), quizId, request, new Callback() {
            @Override
            public void isSuccessed(String response) {
                successedSolve(response);
            }

            @Override
            public void isFailed() {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(),
                            "퀴즈를 해결하는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                });
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
                    textSize = data.getInt("textSize");
                    lineGap = data.getInt("lineGap");

                    solved(isCorrect, question, textSize, lineGap);
                } else {
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(),
                                status.msg, Toast.LENGTH_SHORT).show();
                    });
                }
            }
        } catch (Exception e) {
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(),
                        "올바르지 않은 값입니다.", Toast.LENGTH_SHORT).show();
            });
        }
    }

    void solved(boolean isCorrect, String question, int textSize, int lineGap) {
        if(isCorrect) {
            if(solveCount > 3) Toast.makeText(getContext(), "정답입니다", Toast.LENGTH_SHORT).show();
            else Toast.makeText(getContext(), "정답입니다. 5p지급됩니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "오답입니다", Toast.LENGTH_SHORT).show();
        }

        initView(question, solveCount + 1, textSize, lineGap);
    }
}
