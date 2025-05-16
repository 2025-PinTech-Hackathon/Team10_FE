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
    public void setToken(String token) {
        this.token = token;
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

    public String getToken() {
        return token;
    }

    long userId;
    String nickname;
    String id;
    String pw;
    String token;
}

