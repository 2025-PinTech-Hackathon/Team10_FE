package com.example.easynewspaper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.easynewspaper.DataStruct.NewsInfo;
import com.example.easynewspaper.DataStruct.NewsListAdapter;
import com.example.easynewspaper.DataStruct.NewsListItem;
import com.example.easynewspaper.DataStruct.Status;
import com.example.easynewspaper.Interface.Callback;
import com.example.easynewspaper.Utility.StatusCheck;
import com.example.easynewspaper.Utility.Web;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewsFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_news_list, container, false);

        ListView listView = view.findViewById(R.id.NewsListView);

        Web.GetNews(MainActivity.getInstance().userInfo.getUserId(), new Callback() {
            @Override
            public void isSuccessed(String response) {
                successedMethod(response);
            }

            @Override
            public void isFailed() {
                initNewsList();
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(),
                            "기사를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                });
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

                    List<NewsInfo> newsInfos = Collections.emptyList();

                    for (int i = 0; i < newsList.length(); i++) {
                        JSONObject newsItem = newsList.getJSONObject(i);

                        newsInfos.add(new NewsInfo(
                                        newsItem.getLong("NewspaperId"),
                                        newsItem.getString("title"),
                                        newsItem.getString("summary")
                                )
                        );
                    }

                    initNewsList(newsInfos);
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

    public void initNewsList() {
        ListView listView = view.findViewById(R.id.NewsListView);

        List<NewsListItem> items = new ArrayList<>();
        items.add(new NewsListItem(0, "첫 번째 헤더", "간단한 내용 1"));
        items.add(new NewsListItem(1, "두 번째 헤더", "간단한 내용 2"));

        NewsListAdapter adapter = new NewsListAdapter(getActivity(), items);
        listView.setAdapter(adapter);
    }

    public void initNewsList(List<NewsInfo> newsInfos) {
        ListView listView = view.findViewById(R.id.NewsListView);

        List<NewsListItem> items = new ArrayList<>();
        for (var newsInfo : newsInfos) {
            items.add(new NewsListItem(
                    newsInfo.NewspaperId,
                    newsInfo.title,
                    newsInfo.summary
                    )
            );
        }

        NewsListAdapter adapter = new NewsListAdapter(getActivity(), items);
        listView.setAdapter(adapter);
    }
}
