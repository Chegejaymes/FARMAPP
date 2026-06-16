package com.example.farmapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the name passed from Login/Signup to 'fill out the form'
        String userName = getIntent().getStringExtra("USER_NAME");
        if (userName != null && !userName.isEmpty()) {
            TextView tvUser = findViewById(R.id.tvUserName);
            tvUser.setText(userName);
        }

        // Setup Order Page Navigation
        Button btnOrder = findViewById(R.id.btnGoToOrder);
        btnOrder.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, OrderplacmentActivity.class);
            startActivity(intent);
        });

        // Logout logic
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Setup Crop Click Listeners
        setupProduceCard(R.id.cardMaize, "Maize", "Best time to sell in Nairobi market. Current price KSh 4,200 per 90kg bag.");
        setupProduceCard(R.id.cardCoffee, "Coffee", "Harvesting season approaching. Ensure proper drying for grade AA quality.");
        setupProduceCard(R.id.cardTea, "Tea", "Global prices are steady. Next collection day: Wednesday.");

        // Setup Animal Click Listeners
        setupProduceCard(R.id.cardMilk, "Milk", "Daily production is up by 15%. Current collection price KSh 55 per Liter.");
        setupProduceCard(R.id.cardEggs, "Eggs", "Tray price KSh 450. Ensure poultry are kept warm during the cold season.");
        setupProduceCard(R.id.cardBeef, "Beef", "Local butchery demand is high. Quality pasture leads to better weight gain.");
    }

    private void setupProduceCard(int cardId, String name, String description) {
        MaterialCardView card = findViewById(cardId);
        if (card != null) {
            card.setOnClickListener(v -> {
                Intent intent = new Intent(this, ProduceDetailActivity.class);
                intent.putExtra("PRODUCE_NAME", name);
                intent.putExtra("PRODUCE_DESC", description);
                startActivity(intent);
            });
        }
    }
}