//package com.example.administrator.rssreader;
//
//import android.util.Log;
//
//import com.example.administrator.rssreader.view.utils.ProposedFeedItemLoader;
//
//import org.simpleframework.xml.Attribute;
//import org.simpleframework.xml.Path;
//import org.simpleframework.xml.Root;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
//import retrofit2.http.GET;
//
//public static final String LOG_TAG = ProposedFeedItemLoader.class.getName();
//
//public ProposedFeedItemLoader() {
//        Log.d(LOG_TAG, "ProposedFeedItemLoader");
//        }
//
//public interface ProposedFeedService{
//
//    @GET(".")
//    Call<Void> fetchProposedFeedItems();
//}
//
//public interface ProposedFeedsListener {
//    void onLoaded(List<ProposedFeedItem> items);
//}
//
//    public void loadProposedFeedItems(String url, final ProposedFeedItemLoader.ProposedFeedsListener listener){
//        Log.d(LOG_TAG, "loadProposedFeedItems()");
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(url)
//                .addConverterFactory(SimpleXmlConverterFactory.create())
//                .build();
//
//        ProposedFeedItemLoader.ProposedFeedService proposedFeedService = retrofit.create(ProposedFeedItemLoader.ProposedFeedService.class);
//        Call<Void> call = proposedFeedService.fetchProposedFeedItems();
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if(response.isSuccessful()){
//                    Log.d(LOG_TAG, "  response from server" + response.body());
//                    List<ProposedFeedItem> items = new ArrayList<>();
//                    listener.onLoaded(items);
//                }else{
//                    Log.d(LOG_TAG, " Some error occurred ");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Log.d(LOG_TAG, " Web service failure, exception " + t.toString());
//            }
//        });
//    }