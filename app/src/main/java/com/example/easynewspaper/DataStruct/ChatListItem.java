package com.example.easynewspaper.DataStruct;

enum EDialogSide {
    User,
    Gpt,
}

public class ChatListItem {
    public boolean isAi;
    public String chatStr;
    public String timeStr;

    public ChatListItem(boolean isAi, String chatStr, String timeStr) {
        this.isAi = isAi;
        this.chatStr = chatStr;
        this.timeStr = timeStr;
    }
}