package com.dongxi.foodie.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.dongxi.foodie.R;

import co.mobiwise.library.MusicPlayerView;

public class MusicActivity extends AppCompatActivity {

    private MusicPlayerView mpv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        mpv = (MusicPlayerView) findViewById(R.id.mpv);

        mpv.setButtonColor(Color.DKGRAY);
        mpv.setCoverDrawable(R.drawable.mycover);
        mpv.setProgressEmptyColor(Color.GRAY);
        mpv.setProgressLoadedColor(Color.BLUE);
        mpv.setTimeColor(Color.WHITE);

        mpv.setCoverURL("https://upload.wikimedia.org/wikipedia/en/b/b3/MichaelsNumberOnes.JPG");

        mpv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mpv.isRotating()) {
                    mpv.stop();
                } else {
                    mpv.start();
                }
            }
        });
    }
}
