package com.example.logindemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private FirebaseAuth auth;
    private String txt_email;
    private String txt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button button_register = findViewById(R.id.button_register);
        Button button_login = findViewById(R.id.button_login);
        auth = FirebaseAuth.getInstance();
        txt_email = email.getText().toString();
        txt_password = password.getText().toString();

        button_login.setOnClickListener(v -> {
            txt_email = email.getText().toString();
            txt_password = password.getText().toString();
            if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                Toast.makeText(LoginActivity.this, "Missing Credentials", Toast.LENGTH_SHORT).show();
            } else if (txt_password.length() < 6) {
                Toast.makeText(LoginActivity.this, "Password too short!", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(txt_email, txt_password);
            }
        });

        button_register.setOnClickListener(v -> {
            txt_email = email.getText().toString();
            txt_password = password.getText().toString();
            if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                Toast.makeText(LoginActivity.this, "Missing Credentials!", Toast.LENGTH_SHORT).show();
            } else if (txt_password.length() < 6) {
                Toast.makeText(LoginActivity.this, "Password too short!", Toast.LENGTH_SHORT).show();
            } else {
                registerUser(txt_email, txt_password);
            }
        });
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(LoginActivity.this, StudentAdmissions.class));
                finish();
            } else {
                try {
                    throw Objects.requireNonNull(task.getException());
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(LoginActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    throw Objects.requireNonNull(task.getException());
                } catch(FirebaseAuthUserCollisionException e) {
                    Toast.makeText(LoginActivity.this, "User already exist!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null) {
            startActivity(new Intent(LoginActivity.this, StudentAdmissions.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }
}