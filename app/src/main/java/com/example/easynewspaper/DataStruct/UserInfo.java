package com.example.easynewspaper.DataStruct;

public class UserInfo {
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public long getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }
    long userId;
    String nickname;
    String id;
    String pw;
}

