package com.example.farmapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button signInButton = findViewById(R.id.button2);
        EditText etPhone = findViewById(R.id.editTextPhone);
        EditText etPassword = findViewById(R.id.editTextPassword);

        signInButton.setOnClickListener(v -> {
            String phone = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter phone and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Diagnostic: Ensure Firebase is initialized
            if (mAuth == null) {
                mAuth = FirebaseAuth.getInstance();
            }

            // Firebase Login using Phone as part of email for simplicity
            mAuth.signInWithEmailAndPassword(phone + "@farm.com", password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful() && mAuth.getCurrentUser() != null) {
                        String userId = mAuth.getCurrentUser().getUid();
                        
                        // Fetch name from Database
                        mDatabase.child("users").child(userId).child("name")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String name = snapshot.getValue(String.class);
                                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                    intent.putExtra("USER_NAME", name);
                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                    startActivity(intent);
                                }
                            });
                    } else {
                        String errorMsg = (task.getException() != null) ? task.getException().getMessage() : "Unknown Error";
                        android.util.Log.e("FARM_APP_AUTH", "Login Failed", task.getException());
                        Toast.makeText(MainActivity.this, "Login Failed: " + errorMsg, Toast.LENGTH_LONG).show();
                        
                        if (errorMsg != null && errorMsg.contains("API key not valid")) {
                            Toast.makeText(MainActivity.this, "Check Firebase Console: Enable Email/Password Auth & Add SHA-1", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        });

        TextView signUpText = findViewById(R.id.tvSignUp);
        signUpText.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }
}