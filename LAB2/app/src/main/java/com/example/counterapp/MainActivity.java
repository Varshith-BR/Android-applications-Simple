package com.example.counterapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView counterValue;
    Button startButton, stopButton;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counterValue = findViewById(R.id.counter);
        startButton = findViewById(R.id.start);
        stopButton = findViewById(R.id.stop);

        startButton.setOnClickListener(v -> {
            new Thread(() -> {
                while (!stopButton.isPressed()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                    runOnUiThread(() -> {
                        counterValue.setText("" + counter++);
                    });
                }
            }).start();
        });
        stopButton.setOnClickListener(v -> {
            try {
                Thread.sleep(1000);
                Thread.interrupted();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        });
    }
}
