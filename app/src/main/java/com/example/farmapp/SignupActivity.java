package com.example.farmapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etName = findViewById(R.id.etName);
        EditText etPhone = findViewById(R.id.etPhone);
        EditText etLocation = findViewById(R.id.etLocation);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView tvBackToLogin = findViewById(R.id.tvBackToLogin);

        btnRegister.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || location.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
            } else {
                // Register with Firebase Auth
                mAuth.createUserWithEmailAndPassword(phone + "@farm.com", password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful() && mAuth.getCurrentUser() != null) {
                            String userId = mAuth.getCurrentUser().getUid();
                            
                            // Save extra details to Database
                            Map<String, Object> user = new HashMap<>();
                            user.put("name", name);
                            user.put("phone", phone);
                            user.put("location", location);

                            mDatabase.child("users").child(userId).setValue(user)
                                .addOnCompleteListener(dbTask -> {
                                    Toast.makeText(this, "Registration Successful, Jambo " + name + "!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(SignupActivity.this, DashboardActivity.class);
                                    intent.putExtra("USER_NAME", name);
                                    startActivity(intent);
                                    finish();
                                });
                        } else {
                            String errorMsg = (task.getException() != null) ? task.getException().getMessage() : "Unknown Error";
                            Toast.makeText(this, "Registration failed: " + errorMsg, Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });

        tvBackToLogin.setOnClickListener(v -> finish());
    }
}