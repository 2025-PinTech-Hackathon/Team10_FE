package com.example.easynewspaper.DataStruct;

public class ListItem {
    public long id;
    public String headerTxt;
    public String contentTxt;

    public ListItem(long id, String headerTxt, String contentTxt) {
        this.headerTxt = headerTxt;
        this.contentTxt = contentTxt;
    }
}