package com.example.easynewspaper.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.easynewspaper.Activity.HomeBaseActivity;
import com.example.easynewspaper.Activity.MainActivity;
import com.example.easynewspaper.DataStruct.NewsListAdapter;
import com.example.easynewspaper.DataStruct.NewsListItem;
import com.example.easynewspaper.DataStruct.Status;
import com.example.easynewspaper.Interface.Callback;
import com.example.easynewspaper.R;
import com.example.easynewspaper.Utility.StatusCheck;
import com.example.easynewspaper.Utility.Web;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_news_list, container, false);

        Web.GetNews(new Callback() {
            @Override
            public void isSuccessed(String response) {
                successedMethod(response);
            }

            @Override
            public void isFailed() {
                initNewsList();
                MainActivity.getInstance().sendToast("기사를 불러오는 데 실패했습니다.");
            }
        });

        return view;
    }

    void successedMethod (String response) {
        try {
            JSONObject resJson = new JSONObject(response);
            boolean isSuccess = resJson.getBoolean("isSuccess");
            int code = resJson.getInt("code");

            if (isSuccess) {
                Status status = StatusCheck.isSuccess(code);

                if (status.succesed) {
                    JSONObject data = resJson.getJSONObject("data");

                    JSONArray newsList = data.getJSONArray("newsList");

                    List<NewsListItem> newsInfos = new ArrayList<>();

                    for (int i = 0; i < newsList.length(); i++) {
                        JSONObject newsItem = newsList.getJSONObject(i);

                        if (newsItem.isNull("summary")) {
                            newsInfos.add(new NewsListItem(
                                    newsItem.getLong("newspaperId"),
                                    newsItem.getString("title"),
                                    "none summary"
                                    )
                            );
                        }
                        else {
                            newsInfos.add(new NewsListItem(
                                            newsItem.getLong("newspaperId"),
                                            newsItem.getString("title"),
                                            newsItem.getString("summary")
                                    )
                            );
                        }
                    }

                    initNewsList(newsInfos);
                } else {
                    MainActivity.getInstance().sendToast(status.msg);
                }
            }
        } catch (Exception e) {
            MainActivity.getInstance().sendToast("올바르지 않은 값입니다.");
        }
    }

    public void initNewsList() {
        new Handler(Looper.getMainLooper()).post(() -> {
            ListView listView = view.findViewById(R.id.NewsListView);

            List<NewsListItem> items = new ArrayList<>();
            items.add(new NewsListItem(0, "첫 번째 헤더", "간단한 내용 1"));
            items.add(new NewsListItem(1, "두 번째 헤더", "간단한 내용 2"));

            NewsListAdapter adapter = new NewsListAdapter(getActivity(), items);
            listView.setAdapter(adapter);
        });
    }

    public void initNewsList(List<NewsListItem> newsInfos) {
        new Handler(Looper.getMainLooper()).post(() -> {
            ListView listView = view.findViewById(R.id.NewsListView);

            NewsListAdapter adapter = new NewsListAdapter(getActivity(), newsInfos);
            listView.setAdapter(adapter);

            listView.post(new Runnable() {
                @Override
                public void run() {
                    HomeBaseActivity.getInstance().closeLoading();
                }
            });
        });
    }
}
