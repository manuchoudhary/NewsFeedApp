package com.example.manishchoudhary.newsfeedapp;

import java.net.URL;

/**
 * Created by manish.choudhary on 11/25/2016.
 */

public class NewsFeedItem {
    private int id;
    private String title, description, pubDate;
    private String link;

    public NewsFeedItem() {
    }

    public NewsFeedItem(int id, String title, String description, String link,
                    String pubDate) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubDate = pubDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String status) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}