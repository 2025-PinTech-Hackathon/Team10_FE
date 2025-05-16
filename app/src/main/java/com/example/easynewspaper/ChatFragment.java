package com.example.easynewspaper;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ChatFragment extends Fragment {
    boolean onAiChat;
    View view;
    EditText chatEditTxt;
    ImageButton sendChatBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_chat, container, false);

        chatEditTxt = view.findViewById(R.id.ChatEditTxt);
        sendChatBtn = view.findViewById(R.id.SendChatBtn);

        Web.GetChat(MainActivity.getInstance().userInfo.getUserId(), new Callback() {
            @Override
            public void isSuccessed(String response) {
                successedMethod(response);
            }

            @Override
            public void isFailed() {
                initChatList();
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(),
                            "채팅을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                });
            }
        });

        sendChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!onAiChat){
                    onAiChat = true;

                    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
//수정이 필요한 지점 (TimeStamp 이거 어캐 다뤄야됨?)
                    String timeStr =
                            new SimpleDateFormat(
                                    "HH:mm",
                                    Locale.getDefault()
                            ).format(
                                    currentTime
                            );

                    ChatListItem chatListItem = new ChatListItem(false, chatEditTxt.getText().toString(), timeStr);
                    addChat(chatListItem);

                    chatEditTxt.setText("");

                    Web.PostChat(
                            MainActivity.getInstance().userInfo.getUserId(),
                            chatEditTxt.getText().toString(),
                            currentTime,
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
                                                JSONObject data = resJson.getJSONObject("data");
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
                                                    "전송에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                        });
                                    }

                                    onAiChat = false;
                                }

                                @Override
                                public void isFailed() {
                                    requireActivity().runOnUiThread(() -> {
                                        Toast.makeText(getContext(),
                                                "전송에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                    });

                                    onAiChat = false;
                                }
                            }
                    );
                }
            }
        });

        chatEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!chatEditTxt.getText().toString().isEmpty()) {
                    sendChatBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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

                    initChatList(newsInfos);
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

    public void initChatList() {
        ListView listView = view.findViewById(R.id.ChatListView);

        List<ChatListItem> items = new ArrayList<>();
        items.add(new ChatListItem(false, "유저 채팅 유저 채팅 유저 채팅 유저 채팅 유저 채팅 유저 채팅 유저 채팅 유저 채팅", "10:00"));
        items.add(new ChatListItem(true, "지피티 채팅 지피티 채팅 지피티 채팅 지피티 채팅 지피티 채팅 지피티 채팅 지피티 채팅 지피티 채팅 지피티 채팅 지피티 채팅 지피티 채팅", "10:01"));

        ChatListAdapter adapter = new ChatListAdapter(getActivity(), items);
        listView.setAdapter(adapter);
    }

    public void initChatList(List<ChatListItem> chatInfos) {
        ListView listView = view.findViewById(R.id.ChatListView);

        ChatListAdapter adapter = new ChatListAdapter(getActivity(), chatInfos);
        listView.setAdapter(adapter);
    }

    public void addChat(ChatListItem chatListItem) {
        ListView listView = view.findViewById(R.id.ChatListView);
        ChatListAdapter adapter = (ChatListAdapter) listView.getAdapter();

        adapter.add(chatListItem);
        listView.setAdapter(adapter);
    }
}
