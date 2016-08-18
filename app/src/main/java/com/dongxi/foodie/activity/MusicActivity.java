package com.dongxi.foodie.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dongxi.foodie.R;

import java.io.IOException;

import co.mobiwise.library.MusicPlayerView;

public class MusicActivity extends AppCompatActivity {

    private MusicPlayerView mpv;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        mpv = (MusicPlayerView) findViewById(R.id.mpv);

//
        mediaPlayer = MediaPlayer.create(this, R.raw.taotai);

        mpv.setCoverURL("https://upload.wikimedia.org/wikipedia/en/b/b3/MichaelsNumberOnes.JPG");

        mpv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null)
                {
                    mediaPlayer.stop();
                }

                if (mpv.isRotating()) {
                    mpv.stop();
                    mediaPlayer.stop();
                } else {
                    mpv.start();
                    try {
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
