package com.example.mihovil.weatherapp.model;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.mihovil.weatherapp.R;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.List;

/**
 * Created by Mihovil on 26/1/2017.
 */
public class YoutubeHelper {

    private YouTube youtube;
    private YouTube.Search.List list;
    private String videoID = "";

    Handler threadHandler;

    private static final String youtubeAPiKey = "AIzaSyAKTHAfYD4JALas4Upke6PazQYSeIkpWU0";

    public YoutubeHelper(Context context){
        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {}
        }).setApplicationName(context.getString(R.string.app_name)).build();

        threadHandler = new Handler();

        try{
            list = youtube.search().list("id,snippet");
            list.setKey(youtubeAPiKey);
            list.setType("video");
            list.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
        }catch(Exception ex){
            Log.d("Youtube", "Error occured: "+ ex.getMessage());
        }
    }

    public String getVideoID(){
        return videoID;
    }

    public void search(String keywords) {
        list.setQ(keywords);

        new Thread(){
            public void run(){
                try {
                    SearchListResponse response = list.execute();
                    List<SearchResult> results = response.getItems();
                    final SearchResult result = results.get(0);

                    videoID = result.getId().getVideoId();

                } catch (Exception ex) {
                    Log.d("Youtube", "Error while searching for the video: " + ex.getMessage());
                    videoID = null;
                }
            }
        }.start();
    }
}
