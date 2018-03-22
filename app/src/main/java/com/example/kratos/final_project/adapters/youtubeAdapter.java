package com.example.kratos.final_project.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.kratos.final_project.R;
import com.example.kratos.final_project.activities.MainActivity;
import com.example.kratos.final_project.viewHolders.youtubeViewHolder;

import java.util.List;

public class youtubeAdapter extends RecyclerView.Adapter<youtubeViewHolder>{
    private final List<MainActivity.YoutubeVideoItem> videos;

    public youtubeAdapter(List<MainActivity.YoutubeVideoItem> videos) {
        this.videos = videos;
    }

    @Override
    public youtubeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new youtubeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_youtube, parent, false));
    }

    @Override
    public void onBindViewHolder(youtubeViewHolder holder, int position) {
        holder.bind(videos.get(position));
    }

    @Override
    public int getItemCount() {
        return videos != null ? videos.size() : 0;
    }
}
