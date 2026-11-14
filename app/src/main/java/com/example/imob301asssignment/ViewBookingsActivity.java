package com.example.imob301asssignment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ViewBookingsActivity extends AppCompatActivity {

    private ListView listViewBookings;
    private final ArrayList<String> bookingList = new ArrayList<>();
    private final ArrayList<String> bookingIDs = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_bookings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listViewBookings = findViewById(R.id.listViewBookings);
        Button btnBack = findViewById(R.id.btnBack);

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                bookingList
        );
        listViewBookings.setAdapter(adapter);

        listViewBookings.setOnItemClickListener((parent, view, position, id) -> {
            String selectedBookingID = bookingIDs.get(position);
            Intent intent = new Intent(ViewBookingsActivity.this, UpdateDeleteBookingActivity.class);
            intent.putExtra("bookingID", selectedBookingID);
            startActivity(intent);
        });

        btnBack.setOnClickListener(view -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewData();
    }

    private void viewData() {
        bookingList.clear();
        bookingIDs.clear();

        try {
            SQLiteDatabase database = BookingDatabase.getWritableDatabase(this);
            try (Cursor cursor = database.rawQuery(
                    "SELECT bookingID, serviceType, bookingDate, serviceStatus, assignedMechanic " +
                            "FROM Bookings ORDER BY bookingID DESC",
                    null
            )) {
                while (cursor.moveToNext()) {
                    String bookingID = cursor.getString(cursor.getColumnIndexOrThrow("bookingID"));
                    String booking = "ID: " + bookingID + "\n" +
                            "Service: " + cursor.getString(cursor.getColumnIndexOrThrow("serviceType")) + "\n" +
                            "Date: " + cursor.getString(cursor.getColumnIndexOrThrow("bookingDate")) + "\n" +
                            "Status: " + cursor.getString(cursor.getColumnIndexOrThrow("serviceStatus")) + "\n" +
                            "Mechanic: " + cursor.getString(cursor.getColumnIndexOrThrow("assignedMechanic"));

                    bookingList.add(booking);
                    bookingIDs.add(bookingID);
                }
            }

            adapter.notifyDataSetChanged();

            if (bookingList.isEmpty()) {
                Toast.makeText(this, "No bookings have been created yet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception exception) {
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Could not load bookings", Toast.LENGTH_SHORT).show();
        }
    }
}