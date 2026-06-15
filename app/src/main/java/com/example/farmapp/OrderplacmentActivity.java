package com.example.farmapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OrderplacmentActivity extends AppCompatActivity {

    private CheckBox cbMaize, cbMilk, cbEggs, cbCoffee;
    private EditText etMaizeQty, etMilkQty, etEggsQty, etCoffeeQty;
    private TextView tvTotalPrice;
    private int totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.orderplacment);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        cbMaize = findViewById(R.id.cbMaize);
        cbMilk = findViewById(R.id.cbMilk);
        cbEggs = findViewById(R.id.cbEggs);
        cbCoffee = findViewById(R.id.cbCoffee);
        
        etMaizeQty = findViewById(R.id.etMaizeQty);
        etMilkQty = findViewById(R.id.etMilkQty);
        etEggsQty = findViewById(R.id.etEggsQty);
        etCoffeeQty = findViewById(R.id.etCoffeeQty);

        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        // Set listeners for calculation
        cbMaize.setOnCheckedChangeListener((buttonView, isChecked) -> calculateTotal());
        cbMilk.setOnCheckedChangeListener((buttonView, isChecked) -> calculateTotal());
        cbEggs.setOnCheckedChangeListener((buttonView, isChecked) -> calculateTotal());
        cbCoffee.setOnCheckedChangeListener((buttonView, isChecked) -> calculateTotal());

        TextWatcher qtyWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                calculateTotal();
            }
        };

        etMaizeQty.addTextChangedListener(qtyWatcher);
        etMilkQty.addTextChangedListener(qtyWatcher);
        etEggsQty.addTextChangedListener(qtyWatcher);
        etCoffeeQty.addTextChangedListener(qtyWatcher);

        btnPlaceOrder.setOnClickListener(v -> {
            if (totalAmount > 0) {
                Toast.makeText(this, "Order Placed Successfully! Total: KSh " + totalAmount, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please select at least one item", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void calculateTotal() {
        totalAmount = 0;
        if (cbMaize.isChecked()) totalAmount += (4200 * getQty(etMaizeQty));
        if (cbMilk.isChecked()) totalAmount += (55 * getQty(etMilkQty));
        if (cbEggs.isChecked()) totalAmount += (450 * getQty(etEggsQty));
        if (cbCoffee.isChecked()) totalAmount += (800 * getQty(etCoffeeQty));

        tvTotalPrice.setText("KSh " + totalAmount);
    }

    private int getQty(EditText et) {
        String val = et.getText().toString().trim();
        if (val.isEmpty()) return 0;
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}