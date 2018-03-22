package com.example.kratos.final_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kratos.final_project.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class youtubeDetails extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private TextView titleView;
    private TextView descriptionView;
    private String idValue;
    private YouTubePlayerView youTubeView;

    private static final int RECOVERY_REQUEST = 1;
    private static final String YOUTUBE_API_KEY = "AIzaSyCAX9MJfZ-ks_myDsiK_I_6u7Lez4aydlA";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_details);

        Intent intent = getIntent();
        idValue = intent.getStringExtra("video_id");
        String title = intent.getStringExtra("video_title");
        String desc = intent.getStringExtra("video_desc");

        titleView = (TextView) findViewById(R.id.titleDetailsTextView);
        descriptionView = (TextView) findViewById(R.id.descriptionDetailsTextView);

        titleView.setText(title);
        descriptionView.setText(desc);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(YOUTUBE_API_KEY, this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(idValue);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }
}
