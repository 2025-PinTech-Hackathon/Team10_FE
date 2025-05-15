package com.example.easynewspaper.DataStruct;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.easynewspaper.R;

import java.util.List;

public class ChatListAdapter extends ArrayAdapter<ChatListItem> {
    final Context context;
    private List<ChatListItem> itemList;

    public ChatListAdapter(Context context, List<ChatListItem> itemList) {
        super(context, 0, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ChatListItem item = itemList.get(position);

        if (convertView == null) {
            if (item.isAi) {
                convertView = LayoutInflater.from(context).inflate(R.layout.activity_detail_chat_ai, parent, false);
            }
            else {
                convertView = LayoutInflater.from(context).inflate(R.layout.activity_detail_chat_user, parent, false);
            }
        }

        TextView chatTxt = convertView.findViewById(R.id.ChatTxtView);
        TextView chatTimeTxt = convertView.findViewById(R.id.ChatTimeTxt);

        chatTxt.setText(item.chatStr);
        chatTimeTxt.setText(item.timeStr);

        FrameLayout chatFrame = convertView.findViewById(R.id.ChatFrame);

        chatTxt.post(new Runnable() {
            @Override
            public void run() {
                int txtHeight = chatTxt.getHeight();
                int paddingPx = (int) (24 * chatFrame.getResources().getDisplayMetrics().density);
                int totalHeight = txtHeight + paddingPx;

                ViewGroup.LayoutParams params = chatFrame.getLayoutParams();
                params.height = totalHeight;
                chatFrame.setLayoutParams(params);
                chatFrame.requestLayout();
            }
        });

        return convertView;
    }

    @Override
    public void add(ChatListItem object) {
        super.add(object);
    }
}

