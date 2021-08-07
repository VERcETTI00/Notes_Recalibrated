package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateActivity extends AppCompatActivity {

    ImageButton backButton;
    ImageButton next;

    EditText head;
    TextView date;
    EditText body;

    FirebaseFirestore db;
    NOTES NOTES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        db = FirebaseFirestore.getInstance();

        backButton = findViewById(R.id.back);
        next = findViewById(R.id.next);

        head = findViewById(R.id.titleOfTheNote);
        date = findViewById(R.id.date);
        body = findViewById(R.id.body);


//        mDate = NOTES.getDate();
//        date.setText(mDate);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mHead = head.getText().toString();
                String mBody = body.getText().toString();
                loginClick(mHead,mBody);
                finish();
//                startActivity(new Intent(CreateActivity.this,MenuActivity.class));
            }
        });

    }

    public void loginClick(String mHead, String mBody) {
        NOTES = new NOTES(mHead,mBody);
        createNote(mHead,mBody);

    }

    public void createNote(String mHead, String mBody) {
        String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String,Object> customNote =new HashMap<>();
        customNote.put("title", NOTES.xhead);
        customNote.put("data", NOTES.xbody);
        db.collection("user").document(currentUID).collection("notes")
                .add(customNote)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CreateActivity.this, "New Note Created Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateActivity.this, "Failed to create note", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
