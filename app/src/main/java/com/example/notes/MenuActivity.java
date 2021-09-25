package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class MenuActivity extends AppCompatActivity {

    ImageButton signOut;
    FirebaseAuth auth;
    private RecyclerView xRecyclerView;
    private RecyclerView.Adapter xAdapter;
    private RecyclerView.LayoutManager xLayoutManager;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

//        xRecyclerView.notify();

        signOut = findViewById(R.id.signOutButton);
        auth = FirebaseAuth.getInstance();

        xRecyclerView = findViewById(R.id.recyclerView);

        ArrayList<NOTES> exampleList = new ArrayList<>();
        String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firestore = FirebaseFirestore.getInstance();
        firestore.collection("user").document(currentUID).collection("notes").orderBy("date", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("Note Data", document.getId() + " => " + document.getData());
                        NOTES notes = new NOTES(document.get("title").toString(),document.get("data").toString(), document.getDate("date"), document.getId());
                        exampleList.add(notes);
                        System.out.println(notes.head);
                        System.out.println(notes.body);
                        System.out.println(document.get("date"));
                    }
                    xRecyclerView.setHasFixedSize(true);
                    xAdapter = new AdapterActivity(exampleList, MenuActivity.this);
                    xRecyclerView.setLayoutManager(xLayoutManager);
                    xRecyclerView.setAdapter(xAdapter);
                }
                else
                    System.out.println("Error Getting Docs");
            }
        });
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);

        xLayoutManager = new LinearLayoutManager(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MenuActivity.this,CreateActivity.class));
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,LoginActivity.class);
                auth.signOut();
                startActivity(intent);
                finish();
            }
        });
    }
    private int getTime(){
        int time = (int) (System.currentTimeMillis());
        return time;
    }

}
