package com.example.easynewspaper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.easynewspaper.DataStruct.ChatListAdapter;
import com.example.easynewspaper.DataStruct.ChatListItem;
import com.example.easynewspaper.DataStruct.Status;
import com.example.easynewspaper.Interface.Callback;
import com.example.easynewspaper.Utility.StatusCheck;
import com.example.easynewspaper.Utility.Web;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_chat, container, false);

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
                            "채팅을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
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

                    JSONArray chatList = data.getJSONArray("chatList");

                    List<ChatListItem> newsInfos = Collections.emptyList();

                    for (int i = 0; i < chatList.length(); i++) {
                        JSONObject chatItem = chatList.getJSONObject(i);

                        newsInfos.add(new ChatListItem(
                                data.getBoolean("isAI"),
                                data.getString("content"),
                                data.getString("date")
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
        ListView listView = view.findViewById(R.id.ChatListView);

        List<ChatListItem> items = new ArrayList<>();
        items.add(new ChatListItem(false, "첫 번째 헤더", "10:00"));
        items.add(new ChatListItem(true, "두 번째 헤더", "10:01"));

        ChatListAdapter adapter = new ChatListAdapter(getActivity(), items);
        listView.setAdapter(adapter);
    }

    public void initNewsList(List<ChatListItem> newsInfos) {
        ListView listView = view.findViewById(R.id.ChatListView);

        ChatListAdapter adapter = new ChatListAdapter(getActivity(), newsInfos);
        listView.setAdapter(adapter);
    }
}
