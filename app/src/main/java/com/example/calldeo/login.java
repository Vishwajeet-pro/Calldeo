package com.example.calldeo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
EditText email,password;
CheckBox remember;
Button loginbtn , signupbtn;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //initiating all views
    email = findViewById(R.id.email);
    password = findViewById(R.id.pass);
    loginbtn = findViewById(R.id.button);
    signupbtn = findViewById(R.id.create);
    //getting Instance of firebase authorization
    auth = FirebaseAuth.getInstance();
    //sharedpreferences initialization
    SharedPreferences sharedPreferences = getSharedPreferences("checkBox",MODE_PRIVATE);
    String checkbox=sharedPreferences.getString("remember", "");
    if (checkbox.equals("true")){
        startActivity(new Intent(login.this,dashboard.class));
    }else if (checkbox.equals("false")){
        Toast.makeText(login.this,"Please login ",Toast.LENGTH_SHORT).show();
    }

    //login button working
    loginbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String remail, rpassword;
            remail = email.getText().toString();
            rpassword = password.getText().toString();

            auth.signInWithEmailAndPassword(remail, rpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull  Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(login.this,"Logged in",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(login.this,dashboard.class));
                    }else{try {


                        Toast.makeText(login.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                    catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    }
                }
            });
        }
    });
    //creating remember me checkbox
    remember = findViewById(R.id.checkBox);
    remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.isChecked()){
                SharedPreferences sharedPreferences = getSharedPreferences("checkBox",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("remember","true");
                editor.apply();
            }else if (!buttonView.isChecked()){
                SharedPreferences sharedPreferences = getSharedPreferences("checkBox",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("remember","false");
                editor.apply();
            }
        }
    });
// signup button working
    signupbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(login.this,signup.class));
        }
    });
    }
}