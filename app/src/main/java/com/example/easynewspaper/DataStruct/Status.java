package com.example.easynewspaper.DataStruct;

public class Status {
    public Status() {

    }
    public Status(boolean succesed) {
        this.succesed = succesed;
    }
    public Status(boolean succesed, String msg) {
        this.succesed = succesed;
        this.msg = msg;
    }
    public boolean succesed;
    public String msg;
}
