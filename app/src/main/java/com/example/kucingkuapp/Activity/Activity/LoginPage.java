package com.example.kucingkuapp.Activity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kucingkuapp.Activity.Database.HelperClass;
import com.example.kucingkuapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {

    TextInputEditText logEmail, logPassword;
    Button loginButton;
    TextView signUpRedirect;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logEmail = findViewById(R.id.logEmail);
        logPassword = findViewById(R.id.logPass);
        loginButton = findViewById(R.id.login_btn);
        signUpRedirect = findViewById(R.id.regRedirect);

        loginButton.setOnClickListener(v -> {
            String email = logEmail.getText().toString().trim();
            String password = logPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginPage.this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            loginUser(email, password);
        });

        signUpRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(LoginPage.this, RegisterPage.class);
            startActivity(intent);
        });
    }

    private void loginUser(String email, String password) {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        reference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        HelperClass user = userSnapshot.getValue(HelperClass.class);

                        if (user != null) {
                            String dbPassword = user.getPassword();

                            if (dbPassword.equals(password)) {
                                Toast.makeText(LoginPage.this, "Login successful", Toast.LENGTH_SHORT).show();
                                // Redirect to main activity or dashboard
                                Intent intent = new Intent(LoginPage.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                            }
                        }
                    }
                    Toast.makeText(LoginPage.this, "Invattlid email or password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginPage.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("LoginPage", "Database error: " + databaseError.getMessage());
                Toast.makeText(LoginPage.this, "Login failed: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}