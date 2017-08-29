package com.example.mihovil.weatherapp;

import com.example.mihovil.weatherapp.model.YoutubeHelper;
import com.google.android.youtube.player.YouTubeBaseActivity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.mock;

/**
 * Created by Mihovil on 16/08/2017.
 */

public class YoutubeTest implements TestStrategy {

    @Mock
    YouTubeBaseActivity youBaseActivity;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup(){
        youBaseActivity = mock(YouTubeBaseActivity.class);
    }

    @Test
    public void runTest(){
        YoutubeHelper helper = new YoutubeHelper(youBaseActivity.getApplicationContext());
        helper.search("clear sky");
        String videoId = helper.getVideoID();
        Assert.assertTrue(!videoId.equals(""));
    }
}
