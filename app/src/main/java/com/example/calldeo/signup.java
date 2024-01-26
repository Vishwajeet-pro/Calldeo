package com.example.calldeo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class signup extends AppCompatActivity {
FirebaseAuth auth;
FirebaseFirestore database;
    EditText email,name,password;
    Button loginbtn  ,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        loginbtn = findViewById(R.id.create);
        signup = findViewById(R.id.signupbutton);
        //firebase components
        auth =FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        //button working
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email ,Name,  Password;
                Email = email.getText().toString();
                Password = password.getText().toString();
                Name = name.getText().toString();

                Users user= new Users();
                user.setEmail(Email);
                user.setPassword(Password);
                user.setName(Name);

                //getting email and password of users
                auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            database.collection("Users")
                            .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                               startActivity(new Intent(signup.this,login.class));
                                }
                            });
                            Toast.makeText(signup.this,"Account is created",Toast.LENGTH_SHORT).show();
                        }
                         else{
                             Toast.makeText(signup.this ,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signup.this,login.class));
            }
        });

    }
}