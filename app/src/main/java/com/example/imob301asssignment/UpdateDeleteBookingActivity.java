package com.example.imob301asssignment;

import android.database.Cursor;
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

public class UpdateDeleteBookingActivity extends AppCompatActivity {

    private EditText txtBookingID;
    private EditText txtServiceType;
    private EditText txtBookingDate;
    private EditText txtServiceStatus;
    private EditText txtAssignedMechanic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_delete_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtBookingID = findViewById(R.id.txtBookingID);
        txtServiceType = findViewById(R.id.txtServiceType);
        txtBookingDate = findViewById(R.id.txtBookingDate);
        txtServiceStatus = findViewById(R.id.txtServiceStatus);
        txtAssignedMechanic = findViewById(R.id.txtAssignedMechanic);

        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnBack = findViewById(R.id.btnBack);

        String selectedBookingID = getIntent().getStringExtra("bookingID");
        if (selectedBookingID == null || selectedBookingID.trim().isEmpty()) {
            Toast.makeText(this, "No booking was selected", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        txtBookingID.setText(selectedBookingID);
        txtBookingID.setEnabled(false);
        loadBookingData(selectedBookingID);

        btnUpdate.setOnClickListener(view -> updateBooking());
        btnDelete.setOnClickListener(view -> deleteBooking());
        btnBack.setOnClickListener(view -> finish());
    }

    private void loadBookingData(String bookingID) {
        try {
            SQLiteDatabase database = BookingDatabase.getWritableDatabase(this);
            try (Cursor cursor = database.rawQuery(
                    "SELECT bookingID, serviceType, bookingDate, serviceStatus, assignedMechanic " +
                            "FROM Bookings WHERE bookingID = ?",
                    new String[]{bookingID}
            )) {
                if (!cursor.moveToFirst()) {
                    Toast.makeText(this, "Booking not found", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                txtBookingID.setText(cursor.getString(cursor.getColumnIndexOrThrow("bookingID")));
                txtServiceType.setText(cursor.getString(cursor.getColumnIndexOrThrow("serviceType")));
                txtBookingDate.setText(cursor.getString(cursor.getColumnIndexOrThrow("bookingDate")));
                txtServiceStatus.setText(cursor.getString(cursor.getColumnIndexOrThrow("serviceStatus")));
                txtAssignedMechanic.setText(cursor.getString(cursor.getColumnIndexOrThrow("assignedMechanic")));
            }
        } catch (Exception exception) {
            Toast.makeText(this, "Could not load booking", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean hasValidBookingDetails() {
        boolean valid = true;

        EditText[] requiredFields = {
                txtServiceType, txtBookingDate, txtServiceStatus, txtAssignedMechanic
        };

        for (EditText field : requiredFields) {
            if (field.getText().toString().trim().isEmpty()) {
                field.setError("This field is required");
                valid = false;
            }
        }

        return valid;
    }

    private void updateBooking() {
        if (!hasValidBookingDetails()) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SQLiteDatabase database = BookingDatabase.getWritableDatabase(this);
            String sql = "UPDATE Bookings SET serviceType = ?, bookingDate = ?, " +
                    "serviceStatus = ?, assignedMechanic = ? WHERE bookingID = ?";

            try (SQLiteStatement statement = database.compileStatement(sql)) {
                statement.bindString(1, txtServiceType.getText().toString().trim());
                statement.bindString(2, txtBookingDate.getText().toString().trim());
                statement.bindString(3, txtServiceStatus.getText().toString().trim());
                statement.bindString(4, txtAssignedMechanic.getText().toString().trim());
                statement.bindString(5, txtBookingID.getText().toString());
                int updatedRows = statement.executeUpdateDelete();

                if (updatedRows == 0) {
                    Toast.makeText(this, "Booking was not found", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            Toast.makeText(this, "Booking updated", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception exception) {
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteBooking() {
        try {
            SQLiteDatabase database = BookingDatabase.getWritableDatabase(this);
            try (SQLiteStatement statement = database.compileStatement(
                    "DELETE FROM Bookings WHERE bookingID = ?")) {
                statement.bindString(1, txtBookingID.getText().toString());
                int deletedRows = statement.executeUpdateDelete();

                if (deletedRows == 0) {
                    Toast.makeText(this, "Booking was not found", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            Toast.makeText(this, "Booking deleted", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception exception) {
            Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
        }
    }
}