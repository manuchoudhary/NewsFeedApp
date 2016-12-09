package com.example.manishchoudhary.newsfeedapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;

/**
 * Created by manish.choudhary on 12/8/2016.
 */

public class DetailActivity extends Activity {

    NewsFeedItem newsFeed;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        webView = (WebView) findViewById(R.id.webView);

        Intent intent = getIntent();
        newsFeed = (NewsFeedItem) intent.getSerializableExtra("News Feed");
        MyWebViewClient myWebViewClient = new MyWebViewClient();
        webView.setWebViewClient(myWebViewClient);
        webView.loadUrl(newsFeed.getNewsLink());
        //getDetails();
    }

    public class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public void getDetails(){
        StringRequest stringRequest = new StringRequest(newsFeed.getNewsLink(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            getNewsContent(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void getNewsContent(String response){
        org.jsoup.nodes.Document doc = Jsoup.parse(response);
        String content = doc.body().text();
    }
}
