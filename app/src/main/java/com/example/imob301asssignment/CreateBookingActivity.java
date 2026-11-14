package com.example.imob301asssignment;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateBookingActivity extends AppCompatActivity {

    private EditText txtServiceType;
    private EditText txtBookingDate;
    private EditText txtServiceStatus;
    private EditText txtAssignedMechanic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtServiceType = findViewById(R.id.txtServiceType);
        txtBookingDate = findViewById(R.id.txtBookingDate);
        txtServiceStatus = findViewById(R.id.txtServiceStatus);
        txtAssignedMechanic = findViewById(R.id.txtAssignedMechanic);

        Button btnAddBooking = findViewById(R.id.btnAddBooking);
        Button btnBack = findViewById(R.id.btnBack);

        btnAddBooking.setOnClickListener(view -> insertBooking());
        btnBack.setOnClickListener(view -> finish());
    }

    private void insertBooking() {
        String serviceType = txtServiceType.getText().toString().trim();
        String bookingDate = txtBookingDate.getText().toString().trim();
        String serviceStatus = txtServiceStatus.getText().toString().trim();
        String assignedMechanic = txtAssignedMechanic.getText().toString().trim();

        if (serviceType.isEmpty() || bookingDate.isEmpty()
                || serviceStatus.isEmpty() || assignedMechanic.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SQLiteDatabase database = BookingDatabase.getWritableDatabase(this);
            String sql = "INSERT INTO Bookings(serviceType, bookingDate, serviceStatus, assignedMechanic) " +
                    "VALUES(?, ?, ?, ?)";

            try (SQLiteStatement statement = database.compileStatement(sql)) {
                statement.bindString(1, serviceType);
                statement.bindString(2, bookingDate);
                statement.bindString(3, serviceStatus);
                statement.bindString(4, assignedMechanic);
                statement.executeInsert();
            }

            Toast.makeText(this, "Booking added successfully", Toast.LENGTH_SHORT).show();
            txtServiceType.setText("");
            txtBookingDate.setText("");
            txtServiceStatus.setText("");
            txtAssignedMechanic.setText("");
        } catch (Exception exception) {
            Toast.makeText(this, "Booking could not be added", Toast.LENGTH_SHORT).show();
        }
    }
}