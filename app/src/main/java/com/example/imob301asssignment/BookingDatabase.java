package com.example.imob301asssignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Provides the shared local database and ensures the bookings table exists
 * before any screen attempts to read or write bookings.
 */
public final class BookingDatabase {

    private static final String DATABASE_NAME = "VehicleServiceDB";

    private BookingDatabase() {
        // Utility class.
    }

    public static SQLiteDatabase getWritableDatabase(Context context) {
        SQLiteDatabase database = context.openOrCreateDatabase(
                DATABASE_NAME,
                Context.MODE_PRIVATE,
                null
        );

        database.execSQL(
                "CREATE TABLE IF NOT EXISTS Bookings (" +
                        "bookingID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "serviceType TEXT NOT NULL, " +
                        "bookingDate TEXT NOT NULL, " +
                        "serviceStatus TEXT NOT NULL, " +
                        "assignedMechanic TEXT NOT NULL)"
        );

        return database;
    }
}
