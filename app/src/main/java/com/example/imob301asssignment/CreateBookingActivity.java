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

    EditText txtServiceType, txtBookingDate, txtServiceStatus, txtAssignedMechanic;
    Button btnAddBooking, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtServiceType = findViewById(R.id.txtServiceType);
        txtBookingDate = findViewById(R.id.txtBookingDate);
        txtServiceStatus = findViewById(R.id.txtServiceStatus);
        txtAssignedMechanic = findViewById(R.id.txtAssignedMechanic);

        btnAddBooking = findViewById(R.id.btnAddBooking);
        btnBack = findViewById(R.id.btnBack);

        createDatabase();

        btnAddBooking.setOnClickListener(v -> {
            insertBooking();
        });

        btnBack.setOnClickListener(v -> {
            finish();
        });
    }
    public void createDatabase() {
        try {
            SQLiteDatabase db = openOrCreateDatabase("VehicleServiceDB", MODE_PRIVATE, null);

            db.execSQL("CREATE TABLE IF NOT EXISTS Bookings(" +
                    "bookingID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "serviceType VARCHAR, " +
                    "bookingDate VARCHAR, " +
                    "serviceStatus VARCHAR, " +
                    "assignedMechanic VARCHAR)");

            Toast.makeText(this, "Database and table created", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            Toast.makeText(this, "Database not created", Toast.LENGTH_SHORT).show();
        }
    }

    public void insertBooking() {
        try {
            SQLiteDatabase db = openOrCreateDatabase("VehicleServiceDB", MODE_PRIVATE, null);

            String sql = "INSERT INTO Bookings(serviceType, bookingDate, serviceStatus, assignedMechanic) VALUES(?, ?, ?, ?)";

            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindString(1, txtServiceType.getText().toString());
            statement.bindString(2, txtBookingDate.getText().toString());
            statement.bindString(3, txtServiceStatus.getText().toString());
            statement.bindString(4, txtAssignedMechanic.getText().toString());

            if(txtServiceType.getText().toString().isEmpty() ||
                    txtBookingDate.getText().toString().isEmpty() ||
                    txtServiceStatus.getText().toString().isEmpty() ||
                    txtAssignedMechanic.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            statement.execute();

            Toast.makeText(this, "Booking added successfully", Toast.LENGTH_SHORT).show();

            txtServiceType.setText("");
            txtBookingDate.setText("");
            txtServiceStatus.setText("");
            txtAssignedMechanic.setText("");

        } catch (Exception ex) {
            Toast.makeText(this, "Booking not added", Toast.LENGTH_SHORT).show();
        }
    }
}