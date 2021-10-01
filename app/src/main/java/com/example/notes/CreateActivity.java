package com.example.notes;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateActivity extends AppCompatActivity {

    ImageButton backButton;
    ImageButton next;
    FloatingActionButton fab;

    EditText head;
    TextView date;
    EditText body;

    FirebaseFirestore db;
    NOTES NOTES;
    String documentId = "";
    String existingTitle = "";
    String existingBody = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();

        try{
            existingTitle = bundle.getString("title");
            existingBody = bundle.getString("body");
            documentId = bundle.getString("doc");
        }catch (Exception e){

        }

        backButton = findViewById(R.id.back);
        next = findViewById(R.id.next);
        fab = findViewById(R.id.delete);

        head = findViewById(R.id.titleOfTheNote);
        date = findViewById(R.id.date);
        body = findViewById(R.id.body);

        head.setText(existingTitle);
        body.setText(existingBody);


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
                onNextClicked(mHead,mBody);
                finish();
            }
        });

        if (documentId.isEmpty()){
            fab.setVisibility(View.INVISIBLE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onDeleteClicked();
            }
        });

    }
    public void onDeleteClicked(){
        String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("user").document(currentUID).collection("notes").document(documentId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    public void onNextClicked(String mHead, String mBody) {
        if(documentId.isEmpty()){
            NOTES = new NOTES(mHead,mBody, new Date(),"");
            createNote();
        }
        else{
            NOTES = new NOTES(mHead,mBody, new Date(),documentId);
            updateNote();
        }
    }

    public void createNote() {
        String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String,Object> customNote =new HashMap<>();
        customNote.put("title", NOTES.head);
        customNote.put("data", NOTES.body);
        customNote.put("date", NOTES.date);
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

    public void updateNote(){
        String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String,Object> customNote =new HashMap<>();
        customNote.put("title", NOTES.head);
        customNote.put("data", NOTES.body);
        customNote.put("date", NOTES.date);
        db.collection("user").document(currentUID).collection("notes").document(NOTES.documentId)
                .set(customNote)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull Void unused) {
                        Toast.makeText(CreateActivity.this, "New Note Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateActivity.this, "Failed to update note", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
