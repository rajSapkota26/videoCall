package com.technoabinash.videocallingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    //variable
    EditText userEmail, userName, userPassword;
    Button signBtn;
    FirebaseAuth mAuth;
    FirebaseFirestore database;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();


    }

    private void init() {
        database=FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userEmail = findViewById(R.id.signEmailTxt);
        userPassword = findViewById(R.id.signPasswordTxt);
        userName = findViewById(R.id.userNameTxt);
        signBtn = findViewById(R.id.sign_signupBtn);
        dialog=new ProgressDialog(SignUpActivity.this);
        dialog.setMessage("Creating Account...");
    }

    public void goForLogin(View view) {
        startActivity(new Intent(getApplicationContext(), LogInActivity.class));

    }

    public void signInUser(View view) {
        dialog.show();
        String email, password, name;
        email = userEmail.getText().toString();
        password = userPassword.getText().toString();
        name = userName.getText().toString();
        User user1=new User();
        user1.setEmail(email);
        user1.setName(name);
        user1.setPassword(password);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            database.collection("Users").document().set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(getApplicationContext(),LogInActivity.class));
                                }
                            });
                       //     Toast.makeText(SignUpActivity.this, "Account is created", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
}