package com.example.notes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NOTES {
    String xhead;
    String xbody;
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    public NOTES(String head, String body){
        xhead = head;
        xbody = body;
    }

    public String getHead(){
        return xhead;
    }

    public String getBody(){
        return xbody;
    }

    public String getDate(){
        return date;
    }
}


