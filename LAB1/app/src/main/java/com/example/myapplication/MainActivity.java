package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//public class MainActivity extends AppCompatActivity {
//    int[] images;
//    Timer timer = new Timer();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
////        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.wallView), (v, insets) -> {
////            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
////            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
////            return insets;
////        });
//        Button wpButton = findViewById(R.id.button1);
////        Button stopButton = findViewById(R.id.button2);
//        images = new int[]{R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5};
//        wpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        View wallview = findViewById(R.id.wallView);
//                        int imageLength = images.length;
//                        Random random = new Random();
//                        int rNum = random.nextInt(imageLength);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                wallview.setBackground(ContextCompat.getDrawable(getApplicationContext(), images[rNum]));
//                            }
//                        });
//                    }
//                }, 0, 3000);
//            }
//        });
////        stopButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                View wallview = findViewById(R.id.wallView);
////                wallview.setBackgroundColor(01010101);
////                timer.cancel();
////            }
////        });
//    }
//}

public class MainActivity extends AppCompatActivity {
    int[] images;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button wpButton = findViewById(R.id.button1);
        images = new int[]{R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5};

        wpButton.setOnClickListener(v -> {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    View wallview = findViewById(R.id.wallView);
                    int imageLength = images.length;
                    Random random = new Random();
                    int rNum = random.nextInt(imageLength);
                    runOnUiThread(() -> {
                        wallview.setBackground(ContextCompat.getDrawable(getApplicationContext(), images[rNum]));
                    });
                }
            }, 0, 3000);
        });
    }
}

