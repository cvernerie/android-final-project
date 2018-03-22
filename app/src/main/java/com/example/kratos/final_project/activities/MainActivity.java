package com.example.kratos.final_project.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SearchView;

import com.example.kratos.final_project.R;
import com.example.kratos.final_project.adapters.youtubeAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchView youtubeSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeRecyclerView();
        initializeAdapter(null);

        youtubeSearchView = (SearchView) findViewById(R.id.youtubeSearchView);
        youtubeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String apiKey = "AIzaSyCAX9MJfZ-ks_myDsiK_I_6u7Lez4aydlA";
                String part = "snippet";
                int maxResults = 30;

                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.googleapis.com/youtube/v3/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                YouTubeService service = retrofit.create(YouTubeService.class);
                Call<YoutubeVideoResult> videos = service.getVideos(apiKey, part, query, maxResults);

                videos.enqueue(new Callback<YoutubeVideoResult>() {
                    @Override
                    public void onResponse(Call<YoutubeVideoResult> call, Response<YoutubeVideoResult> response) {
                        if (response.isSuccessful()) {
                            Log.i("response", response.body().toString());
                            initializeAdapter(response.body().items);
                        }
                    }

                    @Override
                    public void onFailure(Call<YoutubeVideoResult> call, Throwable t) {
                        Log.e("http_error", t.toString());
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void initializeRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeAdapter(List<YoutubeVideoItem> list) {
        youtubeAdapter adapter = new youtubeAdapter(list);
        recyclerView.setAdapter(adapter);
    }



    public interface YouTubeService {
        @GET("search")
        Call<YoutubeVideoResult> getVideos(@Query("key") String apiKey,
                                           @Query("part") String part,
                                           @Query("q") String query,
                                           @Query("maxResults") int maxResults);
    }

    public class YoutubeVideoResult {
        private List<YoutubeVideoItem> items;

        @Override
        public String toString(){
            return "YoutubeVideo{" +
                    "items : " + items +
                    "}";
        }
    }

    public class YoutubeVideoItem {
        private YoutubeVideoSnippet snippet;
        private YoutubeVideoId id;

        @Override
        public String toString(){
            return "YoutubeItem{" +
                    "snippet : " + snippet +
                    "}";
        }

        public String getTitle(){
            return snippet.title;
        }

        public String getDescription(){
            return snippet.description;
        }

        public String getThumbnail(){ return snippet.getThumbnailUrl(); }

        public String getVideoId(){ return id.getVideoId(); }

    }

    private class YoutubeVideoId {
        private String videoId;

        @Override
        public String toString(){
            return "YoutubeVideoId{" + "videoId : " + videoId + "}";
        }

        public String getVideoId(){return videoId;}
    }

    public class YoutubeVideoSnippet {
        private String title;
        private String description;
        private YoutubeVideoThumbnail thumbnails;

        @Override
        public String toString(){
            return "YoutubeVideoSnippet{" +
                    "title : " + title +
                    ", desc : " + description +
                    ", thumbnail : " + thumbnails +
                    "}";
        }

        public String getThumbnailUrl() {
            return thumbnails.getDefaultUrl();
        }

    }

    public class YoutubeVideoThumbnail {
        public YoutubeVideoThumbnailMedium medium;

        @Override
        public String toString(){
            return "YoutubeVideoThumbnails{" + "medium : " + medium + "}";
        }

        public String getDefaultUrl() {
            return medium.getUrl();
        }
    }

    public class YoutubeVideoThumbnailMedium {
        private String url;
        private int width;
        private int height;

        @Override
        public String toString(){
            return "YoutubeVideoThumbnail{" +
                    "url : " + url +
                    ", width : " + width +
                    ", height : " + height +
                    "}";
        }

        public String getUrl(){return url;}
    }
}
