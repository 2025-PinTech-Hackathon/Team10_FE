package com.example.easynewspaper.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.easynewspaper.Activity.HomeBaseActivity;
import com.example.easynewspaper.DataStruct.ChatListAdapter;
import com.example.easynewspaper.DataStruct.ChatListItem;
import com.example.easynewspaper.DataStruct.Status;
import com.example.easynewspaper.Interface.Callback;
import com.example.easynewspaper.Activity.MainActivity;
import com.example.easynewspaper.R;
import com.example.easynewspaper.Utility.StatusCheck;
import com.example.easynewspaper.Utility.Web;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String timeStr =
                new SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                ).format(
                        currentTime
                );

        ((TextView)view.findViewById(R.id.ChatDateTxtView)).setText(timeStr);

        Web.GetChat(new Callback() {
            @Override
            public void isSuccessed(String response) {
                successedMethod(response);
            }

            @Override
            public void isFailed() {
                initChatList();

                MainActivity.getInstance().sendToast("채팅을 불러오는 데 실패했습니다.");
            }
        });

        sendChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!onAiChat){
                    onAiChat = true;

                    sendChatBtn.setVisibility(View.INVISIBLE);

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

                    Web.PostChat(
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

                                                ChatListItem chatListItem = new ChatListItem(
                                                        true,
                                                        data.getString("content"),
                                                        data.getString("date"));

                                                addChat(chatListItem);
                                            } else {
                                                MainActivity.getInstance().sendToast(status.msg);
                                            }
                                        }

                                    } catch (Exception e) {
                                        MainActivity.getInstance().sendToast(Arrays.toString(e.getStackTrace()));
                                    }

                                    onAiChat = false;
                                }

                                @Override
                                public void isFailed() {
                                    onAiChat = false;

                                    MainActivity.getInstance().sendToast("전송에 실패했습니다.");
                                }
                            }
                    );

                    chatEditTxt.setText("");
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

                    List<ChatListItem> newsInfos = new ArrayList<>();

                    if (!data.isNull("chatList")) {
                        JSONArray chatList = data.getJSONArray("chatList");

                        for (int i = 0; i < chatList.length(); i++) {
                            JSONObject chatItem = chatList.getJSONObject(i);

                            newsInfos.add(new ChatListItem(
                                    chatItem.getBoolean("isAI"),
                                    chatItem.getString("content"),
                                    chatItem.getString("date")
                                    )
                            );
                        }
                    }
                    else {
                        MainActivity.getInstance().sendToast("진행 중인 채팅이 없습니다.");
                    }

                    initChatList(newsInfos);
                } else {
                    MainActivity.getInstance().sendToast(status.msg);
                }
            }
        } catch (Exception e) {
            MainActivity.getInstance().sendToast(e.getStackTrace().toString());
        }
    }

    public void initChatList() {
        new Handler(Looper.getMainLooper()).post(() -> {
            ListView listView = view.findViewById(R.id.ChatListView);

            List<ChatListItem> items = new ArrayList<>();
            items.add(new ChatListItem(false, "유저 채팅 유저 채팅 유저 채팅 유저 채팅 유저 채팅 유저 채팅 유저 채팅 유저 채팅", "10:00"));
            items.add(new ChatListItem(true, "지피티 채팅 지피티 채팅 지피티 채팅 지피티 채팅 지피티 채팅 지피티 채팅 지피티 채팅 지피티 채팅 지피티 채팅 지피티 채팅 지피티 채팅", "10:01"));

            ChatListAdapter adapter = new ChatListAdapter(getActivity(), items);
            listView.setAdapter(adapter);
        });
    }

    public void initChatList(List<ChatListItem> chatInfos) {
        new Handler(Looper.getMainLooper()).post(() -> {
            ListView listView = view.findViewById(R.id.ChatListView);

            ChatListAdapter adapter = new ChatListAdapter(getActivity(), chatInfos);
            listView.setAdapter(adapter);

            listView.post(new Runnable() {
                @Override
                public void run() {
                    HomeBaseActivity.getInstance().closeLoading();

                    listView.setSelection(listView.getCount() - 1);
                }
            });
        });
    }

    public void addChat(ChatListItem chatListItem) {
        new Handler(Looper.getMainLooper()).post(() -> {
            ListView listView = view.findViewById(R.id.ChatListView);
            ChatListAdapter adapter = (ChatListAdapter) listView.getAdapter();

            adapter.add(chatListItem);
            listView.setAdapter(adapter);

            listView.post(new Runnable() {
                @Override
                public void run() {
                    HomeBaseActivity.getInstance().closeLoading();

                    listView.setSelection(listView.getCount() - 1);
                }
            });
        });
    }
}
