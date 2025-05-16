package com.example.easynewspaper.DataStruct;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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
    private final Context context;
    private final List<ChatListItem> itemList;

    public ChatListAdapter(Context context, List<ChatListItem> itemList) {
        super(context, 0, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    private static class ViewHolder {
        TextView chatTxt;
        TextView chatTimeTxt;
        FrameLayout chatFrame;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        ChatListItem item = itemList.get(position);

        int layoutRes = item.isAi
                ? R.layout.activity_detail_chat_ai
                : R.layout.activity_detail_chat_user;
        convertView = LayoutInflater.from(context).inflate(layoutRes, parent, false);

        holder = new ViewHolder();
        holder.chatTxt = convertView.findViewById(R.id.ChatTxtView);
        holder.chatTimeTxt = convertView.findViewById(R.id.ChatTimeTxt);
        holder.chatFrame = convertView.findViewById(R.id.ChatFrame);

        convertView.setTag(holder);

        holder.chatTxt.setText(item.chatStr);
        holder.chatTimeTxt.setText(item.timeStr);

        holder.chatTxt.post(() -> {
            int txtHeight = holder.chatTxt.getHeight();
            int paddingPx = (int) (24 * context.getResources().getDisplayMetrics().density);
            int totalHeight = txtHeight + paddingPx;

            ViewGroup.LayoutParams params = holder.chatFrame.getLayoutParams();
            if (params.height != totalHeight) {
                params.height = totalHeight;
                holder.chatFrame.setLayoutParams(params);
            }
        });

        return convertView;
    }

    @Override
    public void add(ChatListItem object) {
        super.add(object);
    }
}