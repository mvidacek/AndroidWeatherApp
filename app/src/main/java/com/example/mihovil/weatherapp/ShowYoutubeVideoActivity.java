package com.example.mihovil.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mihovil.weatherapp.model.YoutubeHelper;
import com.example.mylibrary.DebugTrace;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class ShowYoutubeVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final String youtubeApiKey = "AIzaSyAKTHAfYD4JALas4Upke6PazQYSeIkpWU0";
    private String videoID = "";

    private YoutubeHelper searchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_youtube_video);

        searchHelper = new YoutubeHelper(getApplicationContext());

        Intent i = getIntent();

        searchHelper.search(i.getStringExtra("keywords"));

        YouTubePlayerView youtube = (YouTubePlayerView)findViewById(R.id.youtubePlayerView);
        while(videoID.equals("")){
            videoID = searchHelper.getVideoID();
        }
        youtube.initialize(youtubeApiKey, this);
    }

    @DebugTrace
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(youTubePlayer == null){
            return;
        }
        if(!b){
            youTubePlayer.cueVideo(videoID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(getApplicationContext(), "Failed to load youtube video", Toast.LENGTH_LONG);
    }
}
