package com.example.lab7;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ android.Manifest.permission.CALL_PHONE }, 1);
        }

        Button b1 = findViewById(R.id.b1);
        Button b2 = findViewById(R.id.b2);
        Button b3 = findViewById(R.id.b3);
        Button b4 = findViewById(R.id.b4);
        Button b5 = findViewById(R.id.b5);
        Button b6 = findViewById(R.id.b6);
        Button b7 = findViewById(R.id.b7);
        Button b8 = findViewById(R.id.b8);
        Button b9 = findViewById(R.id.b9);
        Button b0 = findViewById(R.id.b0);
        Button b10 = findViewById(R.id.b10);
        Button b11 = findViewById(R.id.b11);

        Button btnClear = findViewById(R.id.btnClear);
        Button btnCall = findViewById(R.id.btnCall);
        Button btnSave = findViewById(R.id.btnSave);
        
        EditText phoneNumber = findViewById(R.id.phoneNumber);

        b1.setOnClickListener(v -> phoneNumber.setText(phoneNumber.getText().toString() + "1"));
        b2.setOnClickListener(v -> phoneNumber.setText(phoneNumber.getText().toString() + "2"));
        b3.setOnClickListener(v -> phoneNumber.setText(phoneNumber.getText().toString() + "3"));
        b4.setOnClickListener(v -> phoneNumber.setText(phoneNumber.getText().toString() + "4"));
        b5.setOnClickListener(v -> phoneNumber.setText(phoneNumber.getText().toString() + "5"));
        b6.setOnClickListener(v -> phoneNumber.setText(phoneNumber.getText().toString() + "6"));
        b7.setOnClickListener(v -> phoneNumber.setText(phoneNumber.getText().toString() + "7"));
        b8.setOnClickListener(v -> phoneNumber.setText(phoneNumber.getText().toString() + "8"));
        b9.setOnClickListener(v -> phoneNumber.setText(phoneNumber.getText().toString() + "9"));
        b0.setOnClickListener(v -> phoneNumber.setText(phoneNumber.getText().toString() + "0"));
        b10.setOnClickListener(v -> phoneNumber.setText(phoneNumber.getText().toString() + "*"));
        b11.setOnClickListener(v -> phoneNumber.setText(phoneNumber.getText().toString() + "#"));
        
        btnCall.setOnClickListener(v -> {
            if (phoneNumber.getText().length() > 0) {
                Toast.makeText(this, "Phone number is " + phoneNumber.getText().toString(), Toast.LENGTH_SHORT).show();
                String phStr = "tel:" + phoneNumber.getText().toString();
                makePhoneCall(phStr);
            } else {
                Toast.makeText(this, "No number", Toast.LENGTH_SHORT).show();
            }
        });

        btnClear.setOnClickListener(v -> {
            if (phoneNumber.getText().length() > 0) {
                CharSequence curText = phoneNumber.getText();
                phoneNumber.setText(curText.subSequence(0, curText.length() - 1));
            } else {
                phoneNumber.setText("");
            }
        });

        btnSave.setOnClickListener(v -> {
            if (!phoneNumber.getText().toString().isEmpty()) {
                String number = phoneNumber.getText().toString();
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, number);
                startActivity(intent);
            }
        });
    }

    private void makePhoneCall(String phStr) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(phStr));
        startActivity(callIntent);
    }
}