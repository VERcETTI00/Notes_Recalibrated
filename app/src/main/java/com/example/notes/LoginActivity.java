package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuWrapperICS;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button logInButton;
    EditText password;
    EditText mailLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mailLogin = findViewById(R.id.mailLogin);
        password = findViewById(R.id.password);
        logInButton = findViewById(R.id.logInButton);
        auth = FirebaseAuth.getInstance();

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mailLogin.getText().toString();
                String pass = password.getText().toString();
                if (!(email.isEmpty() && pass.isEmpty()))
                    loginClick(email,pass);
                else
                    Toast.makeText(LoginActivity.this,"Fields can't be Empty",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void loginClick(String email, String pass){
        auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Authenticated Successfully",Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
    
}
