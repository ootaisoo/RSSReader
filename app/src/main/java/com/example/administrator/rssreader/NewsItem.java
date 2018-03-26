package com.example.administrator.rssreader;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Root(name="item", strict = false)
public class NewsItem {

    @Path("title")
    @Text(required=false)
    private String title;

    @Element(name = "description", required = false)
    private String description;

    @Element(name = "pubDate", required = false)
    private String time;

    @Element(name = "link", required = false)
    private String url;

    @Path("media:content")
    @Attribute(name = "url", required = false)
    private String mediaContentImageUrl;

    @Path("enclosure")
    @Attribute(name = "url", required = false)
    private String enclosureImageUrl;

    @Path("image")
    @Attribute(name = "url", required = false)
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
        DateFormat sdfToDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZZZ", Locale.ENGLISH);
        DateFormat sdfToString = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

        String newsPublishingDate = null;
        Date pubDate;
        try {
            pubDate = sdfToDate.parse(time);
            newsPublishingDate = sdfToString.format(pubDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newsPublishingDate;
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

