package com.example.easynewspaper.DataStruct;

public class NewsInfo {
    public NewsInfo(long NewspaperId, String title, String summary) {
        this.NewspaperId = NewspaperId;
        this.title = title;
        this.summary = summary;
    }

    public long NewspaperId;
    public String title;
    public String summary;
}
