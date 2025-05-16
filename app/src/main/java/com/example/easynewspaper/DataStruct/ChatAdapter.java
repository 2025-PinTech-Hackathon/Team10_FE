package com.example.easynewspaper.DataStruct;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.easynewspaper.R;

import java.util.List;
/*
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;

    private List<ChatListItem> messageList;

    public ChatAdapter(List<ChatListItem> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int getItemViewType(int position) {
        ChatListItem message = messageList.get(position);
        return !message.isAi ? VIEW_TYPE_SENT : VIEW_TYPE_RECEIVED;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_chat_user, parent, false);
            return new SentMessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_detail_chat_ai, parent, false);
            return new ReceivedMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatListItem message = messageList.get(position);
        if (holder instanceof SentMessageViewHolder) {
            ((SentMessageViewHolder) holder).bind(message);
        } else if (holder instanceof ReceivedMessageViewHolder) {
            ((ReceivedMessageViewHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    // 사용자 메시지 ViewHolder
    static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        SentMessageViewHolder(View itemView) {
            super(itemView);
            //textView = itemView.findViewById(R.id.textViewMessage);
        }

        void bind(ChatListItem message) {
            //textView.setText(message.getMessage());
        }
    }

    // 상대방 메시지 ViewHolder
    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ReceivedMessageViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewMessage);
        }

        void bind(ChatMessage message) {
            textView.setText(message.getMessage());
        }
    }
}
*/