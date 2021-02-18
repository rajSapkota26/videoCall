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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText userEmail, userPassword;
    Button logBtn;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initValue();
    }

    private void initValue() {
        mAuth = FirebaseAuth.getInstance();
        logBtn = findViewById(R.id.loginBtn);
        userEmail = findViewById(R.id.emailTxt);
        userPassword = findViewById(R.id.passwordTxt);
        dialog=new ProgressDialog(LogInActivity.this);
        dialog.setMessage("please wait...");

    }

    public void goForCreateAccount(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }

    public void loggedInUser(View view) {
        dialog.show();
        String email, password, user;
        email = userEmail.getText().toString();
        password = userPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LogInActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(LogInActivity.this,DashBoardActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}