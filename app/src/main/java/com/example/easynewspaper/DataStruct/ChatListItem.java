package com.example.easynewspaper.DataStruct;

enum EDialogSide {
    User,
    Gpt,
}

public class ChatListItem {
    public EDialogSide eDialogSide;
    public String chatStr;
    public String timeStr;

    public ChatListItem(EDialogSide eDialogSide, String chatStr, String timeStr) {
        this.eDialogSide = eDialogSide;
        this.chatStr = chatStr;
        this.timeStr = timeStr;
    }
}