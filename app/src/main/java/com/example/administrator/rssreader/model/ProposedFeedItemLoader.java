package com.example.administrator.rssreader.model;

import android.util.Log;

import com.example.administrator.rssreader.ProposedFeedItem;
import com.example.administrator.rssreader.view.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class ProposedFeedItemLoader implements IProposedFeedItemLoader {

    public static final String LOG_TAG = ProposedFeedItemLoader.class.getName();

    public interface ProposedFeedsListener {
        void onLoaded(List<ProposedFeedItem> items);
    }

    public interface ProposedFeedService {

        @GET("/")
        Call<ResponseBody> fetchProposedFeedItem();
    }

    public void loadProposedFeedItems(final String url, final ProposedFeedsListener listener){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .build();

        ProposedFeedService proposedFeedService = retrofit.create(ProposedFeedService.class);
        Call<ResponseBody> call = proposedFeedService.fetchProposedFeedItem();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    listener.onLoaded(parseHtml(url, response.body().string()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(LOG_TAG, t.toString());
            }
        });
    }

    private List<ProposedFeedItem> parseHtml(String url, String html) {
        List<ProposedFeedItem> proposedFeedItemList = new ArrayList<>();

        //extract attributes via jsoup and put them in proposedFeedItemList
        try {
            Document doc = Jsoup.parse(html);
            Elements links = doc.select("a[href]");
            links.addAll(doc.select("link[href]"));

            for (Element link : links) {
                if (link.attr("href").contains("rss")) {
                    String title = link.attr("title");
                    String feedUrl = link.attr("href");
                    if (feedUrl.startsWith("/")){
                        feedUrl = url + feedUrl;
                    }
                    String imageUrl =  doc.select("link[rel*=icon]").attr("href");
                    if (imageUrl.startsWith("/")){
                        imageUrl = url + imageUrl;
                    }
                    if (title.equals("") || title.equals("RSS")){
                        if (doc.title() != null) {
                            title = doc.title();
                        } else {
                            Elements titleLinks = doc.select("title");
                            title = titleLinks.get(0).ownText();
                        }
                    }
                    proposedFeedItemList.add(new ProposedFeedItem(title,
                            feedUrl,
                            imageUrl));
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return proposedFeedItemList;
    }
}