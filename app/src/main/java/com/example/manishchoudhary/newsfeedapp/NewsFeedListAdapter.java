package com.example.manishchoudhary.newsfeedapp;

import android.app.Activity;
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
        TextView title,desc,link;

        public NewsFeedHolder(View v) {
            super(v);
            this.title = (TextView) v.findViewById(R.id.txtTitle);
            this.desc = (TextView) v.findViewById(R.id.txtDesc);
            this.link = (TextView) v.findViewById(R.id.txtLink);
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
        if (!TextUtils.isEmpty(feedItems.get(position).getTitle())) {
            holder.title.setText(feedItems.get(position).getTitle());
            holder.desc.setText(feedItems.get(position).getDescription());
            holder.link.setText(feedItems.get(position).getLink());
        } else {
            holder.title.setVisibility(View.GONE);
            holder.desc.setVisibility(View.GONE);
            holder.link.setVisibility(View.GONE);
        }
    }
}
