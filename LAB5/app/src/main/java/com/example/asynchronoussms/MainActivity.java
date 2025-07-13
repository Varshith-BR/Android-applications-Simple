//package com.example.asynchronoussms;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.telephony.SmsManager;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class MainActivity extends AppCompatActivity {
//    private ExecutorService executorService;
//    private Handler mainHandler;
//    private EditText phoneNumber, messageContent;
//    private Button sendButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        executorService = Executors.newSingleThreadExecutor();
//        mainHandler = new Handler(Looper.getMainLooper());
//        phoneNumber = findViewById(R.id.phoneNumber);
//        messageContent = findViewById(R.id.messageContent);
//        sendButton = findViewById(R.id.sendMessage);
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.SEND_SMS }, 1);
//        }
//
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String phone = phoneNumber.getText().toString().trim();
//                String message = messageContent.getText().toString().trim();
//                sendSmsAsync(phone, message);
//            }
//        });
//    }
//
//    private void sendSmsAsync(String phone, String message) {
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    SmsManager sms = SmsManager.getDefault();
//                    sms.sendTextMessage(phone, null, message, null, null);
//                    mainHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(MainActivity.this, "SMS sent successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    mainHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(MainActivity.this, "SMS failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        });
//    }
//}


package com.example.asynchronoussms;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private ExecutorService executorService;
    private EditText phoneNumber, messageContent;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executorService = Executors.newSingleThreadExecutor();
        phoneNumber = findViewById(R.id.phoneNumber);
        messageContent = findViewById(R.id.messageContent);
        sendButton = findViewById(R.id.sendMessage);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
        sendButton.setOnClickListener(v -> {
            String phone = phoneNumber.getText().toString().trim();
            String message = messageContent.getText().toString().trim();
            if (phone.isEmpty() || message.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter both phone number and message", Toast.LENGTH_SHORT).show();
                return;
            }
            sendSmsAsync(phone, message);
        });
    }

    private void sendSmsAsync(String phone, String message) {
        executorService.execute(() -> {
            try {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(phone, null, message, null, null);
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "SMS sent successfully", Toast.LENGTH_SHORT).show();
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "SMS failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}
