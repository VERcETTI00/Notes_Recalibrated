package com.example.notes;
import java.util.Date;

public class NOTES {
    String head;
    String body;
    Date date;
    String documentId;
   // final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    public NOTES(String head, String body, Date date , String documentId){
        this.head = head;
        this.body = body;
        this.date = date;
        this.documentId = documentId;
    }

    public String getHead(){
        return head;
    }

    public String getBody(){
        return body;
    }

    public Date getDate(){
        return date;
    }

    public String getDocumentId(){return documentId;}
}


