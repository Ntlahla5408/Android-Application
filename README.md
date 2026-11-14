# Vehicle Service Booking App

A native Android application developed as an academic mobile-development project. It demonstrates a basic vehicle-service booking workflow, with booking records stored locally on the device.

## Features

- Create a vehicle-service booking.
- Record the service type, booking date, service status and assigned mechanic.
- View saved bookings in a list.
- Select an existing booking to update or delete it.
- Store booking data in a local SQLite database named `VehicleServiceDB`.

## Technology

- Java
- Android SDK
- Android Studio / Gradle
- SQLite
- Android XML layouts

## Project structure

The main application screens are implemented in:

- `MainActivity.java` – application entry/navigation support.
- `MechanicDashboardActivity.java` – dashboard navigation.
- `CreateBookingActivity.java` – creates booking records.
- `ViewBookingsActivity.java` – displays saved bookings.
- `UpdateDeleteBookingActivity.java` – loads, updates and deletes a selected booking.

## Run locally

1. Clone the repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync.
4. Run the app on an Android emulator or compatible Android device.

The project uses a local SQLite database, so booking data is stored on the device running the app rather than in a shared cloud database.

## Scope

This is an academic project demonstrating Android activities, XML-based interfaces, local persistence and CRUD operations. It is not presented as a production-ready vehicle-service platform.

## Repository

[View the source code](https://github.com/Ntlahla5408/Android-Application)
