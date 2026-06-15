package com.example.farmapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProduceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce_detail);

        String produceName = getIntent().getStringExtra("PRODUCE_NAME");
        String produceDescription = getIntent().getStringExtra("PRODUCE_DESC");

        TextView tvName = findViewById(R.id.tvProduceName);
        TextView tvDesc = findViewById(R.id.tvDescription);
        Button btnBack = findViewById(R.id.btnBack);

        if (produceName != null) {
            tvName.setText(produceName);
        }
        
        if (produceDescription != null) {
            tvDesc.setText(produceDescription);
        }

        btnBack.setOnClickListener(v -> finish());
    }
}