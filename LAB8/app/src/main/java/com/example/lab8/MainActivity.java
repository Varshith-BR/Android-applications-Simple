package com.example.lab8;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.widget.*;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.*;
import android.database.Cursor;

import java.time.LocalDate;
import java.time.LocalTime;

public class MainActivity extends AppCompatActivity {
    EditText medicineName, medicineDate;
    Spinner timeOfDaySpinner;
    Button saveBtn, checkAlarmBtn;
    TextView outputText;

    SQLiteDatabase db;

    final String[] timesOfDay = {"Morning", "Afternoon", "Evening", "Night"};

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicineName = findViewById(R.id.medName);
        medicineDate = findViewById(R.id.medDate);
        timeOfDaySpinner = findViewById(R.id.timeOfTheDay);
        saveBtn = findViewById(R.id.medSave);
        checkAlarmBtn = findViewById(R.id.medCheckAlarm);
        outputText = findViewById(R.id.medOutput);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timesOfDay);
        timeOfDaySpinner.setAdapter(adapter);

        db = openOrCreateDatabase("MedicinesDB", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS medicine_schedule (medicine_name TEXT, date TEXT, time_of_day TEXT)");

        saveBtn.setOnClickListener(v -> saveMedicine());
        checkAlarmBtn.setOnClickListener(v -> checkAlarm());
    }

    void saveMedicine() {
        String name = medicineName.getText().toString();
        String date = medicineDate.getText().toString();
        String timeOfDay = timeOfDaySpinner.getSelectedItem().toString();
        if (name.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        db.execSQL("INSERT INTO medicine_schedule (medicine_name, date, time_of_day) VALUES (?, ?, ?)",
                new Object[]{name, date, timeOfDay});
        Toast.makeText(this, "Medicine Saved", Toast.LENGTH_SHORT).show();
        medicineName.setText("");
        medicineDate.setText("");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    String getCurrentTimeOfDay() {
        int hour = LocalTime.now().getHour();
        if (hour >= 5 && hour <= 11) return "Morning";
        else if (hour >= 12 && hour <= 16) return "Afternoon";
        else if (hour >= 17 && hour <= 20) return "Evening";
        else return "Night";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void checkAlarm() {
        String today = LocalDate.now().toString();
        String timeOfDay = getCurrentTimeOfDay();
        Cursor cursor = db.rawQuery("SELECT medicine_name FROM medicine_schedule WHERE date=? AND time_of_day=?",
                new String[]{today, timeOfDay});
        if (cursor.getCount() == 0) {
            outputText.setText("No medicine scheduled for now.");
        } else {
            StringBuilder builder = new StringBuilder("🔔 It's " + timeOfDay + " on " + today + "\nTake:\n");
            while (cursor.moveToNext()) {
                builder.append("- ").append(cursor.getString(0)).append("\n");
            }
            outputText.setText(builder.toString());
        }
        cursor.close();
    }
}
