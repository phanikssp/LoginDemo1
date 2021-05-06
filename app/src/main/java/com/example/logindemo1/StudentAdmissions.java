package com.example.logindemo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import com.google.firebase.auth.FirebaseAuth;

public class StudentAdmissions extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_admissions);
        setTitle("Login");

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(item -> {
            if(item.toString().equals("Logout")) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(StudentAdmissions.this, LoginActivity.class));
                finish();
                return true;
            } else {
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}