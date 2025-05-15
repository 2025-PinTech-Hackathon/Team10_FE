package com.example.easynewspaper.Utility;

class Status {
    Status() {

    }
    Status(boolean succesed) {
        this.succesed = succesed;
    }
    Status(boolean succesed, String msg) {
        this.succesed = succesed;
        this.msg = msg;
    }
    public boolean succesed;
    public String msg;
}

public class StatusCheck {
    public static Status isSuccess(int code) {
        if (code == 200) {
            return new Status(true);
        }
        else {
            Status status = new Status(false);

            switch (code) {
                
            }

            return status;
        }
    }
}
