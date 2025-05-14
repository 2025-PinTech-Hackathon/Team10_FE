package com.example.easynewspaper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;

import com.example.easynewspaper.DataStruct.NewsInfo;
import com.example.easynewspaper.DataStruct.CustomAdapter;
import com.example.easynewspaper.DataStruct.ListItem;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        initNewsList();
    }

    public void initNewsList() {
        ListView listView = findViewById(R.id.NewsListView);

        List<ListItem> items = new ArrayList<>();
        items.add(new ListItem("첫 번째 헤더", "간단한 내용 1"));
        items.add(new ListItem("두 번째 헤더", "간단한 내용 2"));

        CustomAdapter adapter = new CustomAdapter(this, items);
        listView.setAdapter(adapter);
    }

    public void initNewsList(List<NewsInfo> newsInfos) {
        ListView listView = findViewById(R.id.NewsListView);

        List<ListItem> items = new ArrayList<>();
        items.add(new ListItem("첫 번째 헤더", "간단한 내용 1"));
        items.add(new ListItem("두 번째 헤더", "간단한 내용 2"));

        CustomAdapter adapter = new CustomAdapter(this, items);
        listView.setAdapter(adapter);
    }
}
