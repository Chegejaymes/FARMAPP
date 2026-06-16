package com.example.farmapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PaymentpageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.paymentpage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get total amount from previous activity
        int totalToPay = getIntent().getIntExtra("TOTAL_AMOUNT", 0);
        TextView tvAmount = findViewById(R.id.tvPaymentAmount);
        tvAmount.setText("KSh " + totalToPay);

        Button btnComplete = findViewById(R.id.btnCompletePayment);
        btnComplete.setOnClickListener(v -> {
            if (totalToPay > 0) {
                Toast.makeText(this, "Payment Successful! Thank you for your order.", Toast.LENGTH_LONG).show();
                
                // Return to dashboard
                Intent intent = new Intent(PaymentpageActivity.this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Invalid payment amount.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}