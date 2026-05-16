package com.example.imob301asssignment;

import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import android.database.sqlite.SQLiteDatabase;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class ViewBookingsActivity extends AppCompatActivity {

    ListView listViewBookings;
    ArrayList<String> bookingList;
    ArrayAdapter<String> adapter;
    ArrayList<String> bookingIDs = new ArrayList<>();
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_bookings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listViewBookings = findViewById(R.id.listViewBookings);
        btnBack = findViewById(R.id.btnBack);

        bookingList = new ArrayList<>();
        bookingIDs = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                bookingList
        );


        listViewBookings.setAdapter(adapter);
        listViewBookings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedBookingID = bookingIDs.get(position);

                Intent intent = new Intent(ViewBookingsActivity.this,
                        UpdateDeleteBookingActivity.class);

                intent.putExtra("bookingID", selectedBookingID);

                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(v -> {
            finish();
        });

        viewData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewData();
    }

    public void viewData() {

        try {

            SQLiteDatabase db = openOrCreateDatabase("VehicleServiceDB", MODE_PRIVATE, null);

            Cursor cursor = db.rawQuery("SELECT * FROM Bookings", null);

            int bookingID = cursor.getColumnIndex("bookingID");
            int serviceType = cursor.getColumnIndex("serviceType");
            int bookingDate = cursor.getColumnIndex("bookingDate");
            int serviceStatus = cursor.getColumnIndex("serviceStatus");
            int assignedMechanic = cursor.getColumnIndex("assignedMechanic");

            bookingList.clear();
            bookingIDs.clear();

            if (cursor.moveToFirst()) {

                do {

                    String booking =
                            "ID: " + cursor.getString(bookingID) + "\n" +
                                    "Service: " + cursor.getString(serviceType) + "\n" +
                                    "Date: " + cursor.getString(bookingDate) + "\n" +
                                    "Status: " + cursor.getString(serviceStatus) + "\n" +
                                    "Mechanic: " + cursor.getString(assignedMechanic);

                    bookingList.add(booking);
                    bookingIDs.add(cursor.getString(bookingID));

                } while (cursor.moveToNext());
            }

            adapter.notifyDataSetChanged();

            cursor.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}