package com.example.kucingkuapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kucingkuapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginPage extends AppCompatActivity {
    EditText logEmail, logPass;
    Button login_btn;
    TextView regRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logEmail = findViewById(R.id.logEmail);
        logPass = findViewById(R.id.logPass);
        regRedirect = findViewById(R.id.regRedirect);
        login_btn = findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail() | !validatePassword()) {
                    // Do nothing if validation fails
                } else {
                    checkUser();
                }
            }
        });

        regRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(intent);
            }
        });
    }

    public Boolean validateEmail() {
        String val = logEmail.getText().toString();
        if (val.isEmpty()) {
            logEmail.setError("Email cannot be empty");
            return false;
        } else {
            logEmail.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = logPass.getText().toString();
        if (val.isEmpty()) {
            logPass.setError("Password cannot be empty");
            return false;
        } else {
            logPass.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String userEmail = logEmail.getText().toString().trim();
        String userPass = logPass.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("email").equalTo(userEmail);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    logEmail.setError(null);
                    String passwordFromDB = snapshot.child(userEmail).child("password").getValue(String.class);

                    if (Objects.equals(passwordFromDB, userPass)) {
                        logEmail.setError(null);
                        Intent intent = new Intent(LoginPage.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        logEmail.setError("Invalid Credentials");
                        logPass.requestFocus();
                    }
                } else {
                    logEmail.setError("Email doesn't exist");
                    logEmail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
