package com.example.administrator.rssreader;


import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name="channel", strict = false)
public class Channel {

    @ElementList(inline = true, name = "item")
    private List<NewsItem> newsItems;

    public List<NewsItem> getNewsItems() {
        return newsItems;
    }
}
