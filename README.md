# Vehicle Service Booking App

A native Android application built with Java that allows a mechanic to create, view, update and delete vehicle service bookings. Booking records are stored locally on the device using SQLite.

## Features

- Create a vehicle service booking with a service type, booking date, service status and assigned mechanic.
- View saved bookings.
- Select a booking to update or delete it.
- Store booking data locally in an SQLite database.

## Technology

- Java
- Android SDK
- AndroidX AppCompat and Activity
- Material Components
- SQLite

## Requirements

- Android Studio with Android SDK support for API 36.
- JDK 17 or a compatible JDK configured by Android Studio.
- Android Gradle Plugin and Gradle versions declared in the project version catalog and wrapper.

The app currently sets a minimum Android version of API 30.

## Run locally

1. Clone this repository.
2. Open the project root in Android Studio.
3. Allow Gradle to sync and install any requested Android SDK components.
4. Select an emulator or connect an Android device running Android 11 (API 30) or later.
5. Run the `app` configuration.

You can also build a debug APK from the project root with:

```bash
./gradlew assembleDebug
```

On Windows:

```bat
gradlew.bat assembleDebug
```

The debug APK is generated at `app/build/outputs/apk/debug/app-debug.apk`.

## Data storage

The application creates a local database named `VehicleServiceDB` and a `Bookings` table when a booking screen first accesses the database. This shared initialization means the View Bookings screen also works before the user has created their first booking.

The database is local to the app installation. Uninstalling the app or clearing its storage removes the saved bookings. There is currently no cloud synchronization, account system or remote backup.

## Current scope and limitations

This is a small Android CRUD project, not a production vehicle-service management platform. It does not currently include authentication, mechanic accounts, appointment conflict detection, notifications, server-side storage or automated UI tests. Booking dates and status values are stored as text and are not yet constrained to a specific format or set of allowed values.

## Repository structure

```text
app/
  src/main/
    java/com/example/imob301asssignment/
      BookingDatabase.java
      CreateBookingActivity.java
      MechanicDashboardActivity.java
      UpdateDeleteBookingActivity.java
      ViewBookingsActivity.java
    res/
      layout/
      values/
```

