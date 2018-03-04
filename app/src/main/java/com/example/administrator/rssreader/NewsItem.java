package com.example.administrator.rssreader;

public class NewsItem {

    private String title;
    private String description;
    private String time;
    private String url;

    public NewsItem(String title, String description, String time, String url) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String getUrl() {
        return url;
    }
}
