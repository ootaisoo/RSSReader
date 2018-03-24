package com.example.administrator.rssreader;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict=false)
public class NewsItem {

    @Element(name = "title")
    private String title;

    @Element(name = "description")
    private String description;

    @Element(name = "pubDate")
    private String time;

    @Element(name = "link")
    private String url;

    @Element(name = "media:content")
    private String mediaContentImageUrl;

    @Element(name = "enclosure")
    private String enclosureImageUrl;

    @Element(name = "image")
    private String imageUrl;

    public NewsItem() {
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

    public String getImageUrl() {
        if (mediaContentImageUrl != null) {
            return mediaContentImageUrl;
        } else if (enclosureImageUrl != null) {
            return enclosureImageUrl;
        } else if (imageUrl != null) {
            return imageUrl;
        } else {
            return null;
        }
    }
}

