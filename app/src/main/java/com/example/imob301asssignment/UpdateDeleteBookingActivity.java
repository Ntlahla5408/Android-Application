package com.example.imob301asssignment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateDeleteBookingActivity extends AppCompatActivity {

    EditText txtBookingID, txtServiceType, txtBookingDate, txtServiceStatus, txtAssignedMechanic;
    Button btnUpdate, btnDelete;
    String selectedBookingID;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_delete_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtBookingID = findViewById(R.id.txtBookingID);
        txtServiceType = findViewById(R.id.txtServiceType);
        txtBookingDate = findViewById(R.id.txtBookingDate);
        txtServiceStatus = findViewById(R.id.txtServiceStatus);
        txtAssignedMechanic = findViewById(R.id.txtAssignedMechanic);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.btnBack);

        selectedBookingID = getIntent().getStringExtra("bookingID");

        loadBookingData();

        btnUpdate.setOnClickListener(v -> {
            updateBooking();
        });

        btnDelete.setOnClickListener(v -> {
            deleteBooking();
        });
        btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    public void loadBookingData() {
        try {
            SQLiteDatabase db = openOrCreateDatabase("VehicleServiceDB", MODE_PRIVATE, null);

            Cursor cursor = db.rawQuery(
                    "SELECT * FROM Bookings WHERE bookingID = ?",
                    new String[]{selectedBookingID}
            );

            if (cursor.moveToFirst()) {
                txtBookingID.setText(cursor.getString(cursor.getColumnIndexOrThrow("bookingID")));
                txtServiceType.setText(cursor.getString(cursor.getColumnIndexOrThrow("serviceType")));
                txtBookingDate.setText(cursor.getString(cursor.getColumnIndexOrThrow("bookingDate")));
                txtServiceStatus.setText(cursor.getString(cursor.getColumnIndexOrThrow("serviceStatus")));
                txtAssignedMechanic.setText(cursor.getString(cursor.getColumnIndexOrThrow("assignedMechanic")));
            }

            cursor.close();

        } catch (Exception ex) {
            Toast.makeText(this, "Could not load booking", Toast.LENGTH_SHORT).show();
        }
    }
    public void updateBooking() {

        try {

            SQLiteDatabase db = openOrCreateDatabase("VehicleServiceDB", MODE_PRIVATE, null);

            String sql = "UPDATE Bookings SET serviceType=?, bookingDate=?, serviceStatus=?, assignedMechanic=? WHERE bookingID=?";

            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindString(1, txtServiceType.getText().toString());
            statement.bindString(2, txtBookingDate.getText().toString());
            statement.bindString(3, txtServiceStatus.getText().toString());
            statement.bindString(4, txtAssignedMechanic.getText().toString());
            statement.bindString(5, txtBookingID.getText().toString());

            statement.execute();

            Toast.makeText(this, "Booking Updated", Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception ex) {

            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteBooking() {

        try {

            SQLiteDatabase db = openOrCreateDatabase("VehicleServiceDB", MODE_PRIVATE, null);

            String sql = "DELETE FROM Bookings WHERE bookingID=?";

            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindString(1, txtBookingID.getText().toString());

            statement.execute();

            Toast.makeText(this, "Booking Deleted", Toast.LENGTH_SHORT).show();

            finish();

        } catch (Exception ex) {

            Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show();
        }
    }
}