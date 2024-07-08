package com.example.kucingkuapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kucingkuapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPage extends AppCompatActivity {

    EditText regName, regEmail, regPass, regConpass;
    TextView logRedirect;
    Button register_btn;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI elements
        regName = findViewById(R.id.regName);
        regEmail = findViewById(R.id.regEmail);
        regPass = findViewById(R.id.regPass);
        regConpass = findViewById(R.id.regConpass);
        logRedirect = findViewById(R.id.logRedirect);
        register_btn = findViewById(R.id.register_btn);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = regName.getText().toString().trim();
                String email = regEmail.getText().toString().trim();
                String password = regPass.getText().toString().trim();
                String confirmPassword = regConpass.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(RegisterPage.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterPage.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                HelperClass helperClass = new HelperClass(name, email, password);
                reference.child(name).setValue(helperClass);

                Toast.makeText(RegisterPage.this, "You have signed up successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                startActivity(intent);
            }
        });

        logRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                startActivity(intent);
            }
        });
    }
}
