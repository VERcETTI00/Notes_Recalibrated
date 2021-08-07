package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.makeText;

public class SignUpActivity extends AppCompatActivity {

    EditText username;
    EditText mailSignUp;
    EditText phoneNo;
    EditText password;
    FirebaseAuth auth;
    FirebaseFirestore db;
    Button signUpButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db =FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        username = findViewById(R.id.username);
        mailSignUp = findViewById(R.id.mailSignUp);
        phoneNo = findViewById(R.id.phoneNo);
        password = findViewById(R.id.signUpPass);
        signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String email = mailSignUp.getText().toString();
                String phone = phoneNo.getText().toString();
                String pass = password.getText().toString();
                if(!(email.isEmpty()&&pass.isEmpty()))
                    loginClick(user,email,phone,pass);
                else
                    Toast.makeText(SignUpActivity.this,"Fields can't be Empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createUser(String email, String password, USER user){
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String uid = authResult.getUser().getUid();
                saveUser(user,uid);
            }
        });
    }

    public void saveUser(USER user, String uID){
        Map<String,Object> customUser = new HashMap<>();
        customUser.put("username",user.username);
        customUser.put("phoneNo",user.phoneNo);
        customUser.put("email",user.email);
        db.collection("user").document(uID).set(customUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Registered Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this,MenuActivity.class);
                startActivity(intent);
                finish();   
            }
        });
    }

    public void loginClick(String username, String email, String phoneNo, String pass){
        if (!username.isEmpty() && !email.isEmpty() && !phoneNo.isEmpty() && !pass.isEmpty()){
            USER user = new USER(username, email, phoneNo);
            createUser(email,pass,user);
        }
        else {
            Toast.makeText(getApplicationContext(),"Fill all the details",Toast.LENGTH_SHORT).show();
        }
    }
}
