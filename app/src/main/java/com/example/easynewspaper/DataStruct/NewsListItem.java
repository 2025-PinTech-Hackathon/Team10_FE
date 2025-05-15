package com.example.easynewspaper.DataStruct;

public class NewsListItem {
    public long id;
    public String headerTxt;
    public String contentTxt;

    public NewsListItem(long id, String headerTxt, String contentTxt) {
        this.id  = id;
        this.headerTxt = headerTxt;
        this.contentTxt = contentTxt;
    }
}