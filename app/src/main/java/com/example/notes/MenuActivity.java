package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView xRecyclerView;
    private RecyclerView.Adapter xAdapter;
    private RecyclerView.LayoutManager xLayoutManager;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ArrayList<NOTES> exampleList = new ArrayList<>();
        String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();/////////////////////

        firestore = FirebaseFirestore.getInstance();
        firestore.collection("user").document(currentUID).collection("notes")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//        CollectionReference noteRef = firestore.collection("users").document(currentUID).collection("notes");
//        noteRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("Note Data", document.getId() + " => " + document.getData());
                        NOTES notes = new NOTES(document.get("title").toString(),document.get("data").toString());
                        exampleList.add(notes);
                        System.out.println(notes.xhead);
                        System.out.println(notes.xbody);
                    }
                }
                else
                    System.out.println("Error Getting Docs");
            }
        });
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);


//        exampleList.add(new NOTES("tellus in metus vulputate eu", "20 Apr 2021"));
//        exampleList.add(new NOTES("asdfghjksdfghjkfghjkl", "20 Apr 2021"));
//        exampleList.add(new NOTES("asdfghjksdfghjkfghjkl", "20 Apr 2021"));
//        exampleList.add(new NOTES("asdfghjksdfghjkfghjkl", "20 Apr 2021"));
//        exampleList.add(new NOTES("asdfghjksdfghjkfghjkl", "20 Apr 2021"));
//        exampleList.add(new NOTES("test hardcoded", "7 may 2021"));

        xRecyclerView = findViewById(R.id.recyclerView);
        xRecyclerView.setHasFixedSize(true);
        xLayoutManager = new LinearLayoutManager(this);
        xAdapter = new AdapterActivity(exampleList);

        xRecyclerView.setLayoutManager(xLayoutManager);
        xRecyclerView.setAdapter(xAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this,CreateActivity.class));
            }
        });
    }
    private int getTime(){
        int time = (int) (System.currentTimeMillis());
        return time;
    }

}
