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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowYoutubeVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final String youtubeApiKey = "AIzaSyAKTHAfYD4JALas4Upke6PazQYSeIkpWU0";
    private String videoID = "";

    @BindView(R.id.youtubePlayerView)
    YouTubePlayerView youtubeVideo;

    private YoutubeHelper searchHelper = new YoutubeHelper(getApplicationContext());;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_youtube_video);

        ButterKnife.bind(this);

        Intent i = getIntent();

        searchHelper.search(i.getStringExtra("keywords"));

        while(videoID.equals("")){
            videoID = searchHelper.getVideoID();
        }
        youtubeVideo.initialize(youtubeApiKey, this);
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
        finish();
    }
}
