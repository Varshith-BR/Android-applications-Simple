package com.ghoul.lab9;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView audioName;
    Button btnPlay, btnPause, btnForward, btnBackward;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    Handler handler;
    Runnable autoUpdateSeekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioName = findViewById(R.id.audioName);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnForward = findViewById(R.id.btnForward);
        btnBackward = findViewById(R.id.btnBackward);
        seekBar = findViewById(R.id.seekBar);
        handler = new Handler(Looper.getMainLooper());
        mediaPlayer = MediaPlayer.create(this, R.raw.sample_audio);

        audioName.setText(getResources().getResourceEntryName(R.raw.sample_audio));
        seekBar.setMax(mediaPlayer.getDuration());

        autoUpdateSeekbar = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 1000);
                }
            }
        };

        btnPlay.setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                handler.post(autoUpdateSeekbar);
            }
        });

        btnPause.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                handler.removeCallbacks(autoUpdateSeekbar);
            }
        });

        btnForward.setOnClickListener(v -> {
            int curPos = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();
            mediaPlayer.seekTo(Math.min(curPos + 5000, duration));
        });

        btnBackward.setOnClickListener(v -> {
            int curPos = mediaPlayer.getCurrentPosition();
            mediaPlayer.seekTo(Math.min(curPos - 5000, 0));
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            handler.removeCallbacks(autoUpdateSeekbar);
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}