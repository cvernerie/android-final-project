package com.example.kratos.final_project.viewHolders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kratos.final_project.R;
import com.example.kratos.final_project.activities.MainActivity;
import com.squareup.picasso.Picasso;

import com.example.kratos.final_project.activities.youtubeDetails;


public class youtubeViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView description;
    private ImageView thumbnail;
    private TextView id;

    public youtubeViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.titleTextView);
        description = (TextView) itemView.findViewById(R.id.descriptionTextView);
        thumbnail = (ImageView) itemView.findViewById(R.id.thumbnailImageView);
        id = (TextView) itemView.findViewById(R.id.urlTextView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), youtubeDetails.class);
                intent.putExtra("video_title", title.getText());
                intent.putExtra("video_desc", description.getText());
                intent.putExtra("video_id", id.getText());
                v.getContext().startActivity(intent);
            }
        });

    }

    public void bind(MainActivity.YoutubeVideoItem videoItem) {
        title.setText(videoItem.getTitle());
        description.setText(videoItem.getDescription());
        id.setText(videoItem.getVideoId());
        Picasso.get().load(videoItem.getThumbnail()).into(thumbnail);

    }
}
