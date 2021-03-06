package com.example.manishchoudhary.newsfeedapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

/**
 * Created by manish.choudhary on 11/25/2016.
 */

public class NewsFeedListAdapter extends RecyclerView.Adapter<NewsFeedListAdapter.ViewHolder> {

    private Activity activity;
    private LayoutInflater inflater;
    private List<NewsFeedItem> feedItems;
    ImageLoader imageLoader;

    public NewsFeedListAdapter(Activity activity, List<NewsFeedItem> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
        imageLoader = AppController.getInstance().getImageLoader();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public class NewsFeedHolder extends ViewHolder {
        CardView card;
        TextView title,desc,time;
        //com.android.volley.toolbox.NetworkImageView profilePic;
        NewsImageView image;

        public NewsFeedHolder(View v) {
            super(v);
            this.title = (TextView) v.findViewById(R.id.txtTitle);
            this.desc = (TextView) v.findViewById(R.id.txtDesc);
            this.time = (TextView) v.findViewById(R.id.timestamp);
            this.card = (CardView) v.findViewById(R.id.newsCard);
            this.image = (NewsImageView) v.findViewById(R.id.newsImage);
        }
    }

    @Override
    public NewsFeedListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_feed_card, parent, false);
        return new NewsFeedHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        NewsFeedHolder holder = (NewsFeedHolder) viewHolder;
        setVisisbility(holder);
        if (!feedItems.get(position).getTitle().isEmpty()) {
            holder.title.setText(feedItems.get(position).getTitle());
            if (!feedItems.get(position).getDescription().isEmpty()) {
                holder.desc.setText(feedItems.get(position).getDescription());
            }else{
                holder.desc.setVisibility(View.GONE);
            }
            if (!feedItems.get(position).getPubDate().isEmpty()) {
                holder.time.setText(feedItems.get(position).getPubDate());
            }else{
                holder.time.setVisibility(View.GONE);
            }
            if (feedItems.get(position).getImageLink() != null) {
                holder.image.setImageUrl(feedItems.get(position).getImageLink(), imageLoader);
            }else {
                holder.image.setVisibility(View.GONE);
            }
        } else {
            holder.title.setVisibility(View.GONE);
            holder.desc.setVisibility(View.GONE);
            holder.time.setVisibility(View.GONE);
            holder.image.setVisibility(View.GONE);
            //holder.profilePic.setVisibility(View.GONE);
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra("News Feed", feedItems.get(position));
                activity.startActivity(intent);
            }
        });
    }

    public void setVisisbility(NewsFeedHolder holder){
        holder.title.setVisibility(View.VISIBLE);
        holder.desc.setVisibility(View.VISIBLE);
        holder.time.setVisibility(View.VISIBLE);
        holder.image.setVisibility(View.VISIBLE);
    }
}
