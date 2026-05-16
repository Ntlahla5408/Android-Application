package com.example.imob301asssignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MechanicDashboardActivity extends AppCompatActivity {
    Button btnCreateBooking, btnViewBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mechanic_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnCreateBooking = findViewById(R.id.btnCreateBooking);
        btnViewBookings = findViewById(R.id.btnViewBookings);

        btnCreateBooking.setOnClickListener(v -> {
            Intent intent = new Intent(MechanicDashboardActivity.this, CreateBookingActivity.class);
            startActivity(intent);
        });

        btnViewBookings.setOnClickListener(v -> {
            Intent intent = new Intent(MechanicDashboardActivity.this, ViewBookingsActivity.class);
            startActivity(intent);
        });
    }
}