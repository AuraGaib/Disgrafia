package com.indomi.disgrafia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class activity_yutub extends AppCompatActivity {
    YouTubePlayerView liatYutupView;
    Button tombolPutar;
    YouTubePlayer.OnInitializedListener insiasiYutub;
    private static final String TAG = "activity_yutub";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yutub);
        Log.d(TAG, "onCreate: Starting.");
        tombolPutar = (Button) findViewById(R.id.tombol_putar);
        liatYutupView = (YouTubePlayerView) findViewById(R.id.yutubPlay);

        insiasiYutub = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG, "onClick: Done Initializing");
                youTubePlayer.loadVideo("TYuFwUSx8zo");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "onClick: Failed Initializing");
            }
        };
        tombolPutar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Initializing Youtube Player");
                liatYutupView.initialize(YouTubeActivity.getApiKey(), insiasiYutub);
            }
        });

    }
}
