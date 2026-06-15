package com.example.farmapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button signInButton = findViewById(R.id.button2);
        EditText etName = findViewById(R.id.editTextText);

        signInButton.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (name.isEmpty() || name.equals("Name")) {
                name = "Mkulima"; // Default if not entered
            }
            
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            intent.putExtra("USER_NAME", name);
            startActivity(intent);
        });

        TextView signUpText = findViewById(R.id.tvSignUp);
        signUpText.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }
}