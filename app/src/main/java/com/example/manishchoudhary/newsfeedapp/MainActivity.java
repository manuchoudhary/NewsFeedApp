package com.example.manishchoudhary.newsfeedapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends Activity {

    static final String KEY_ITEM = "item"; // parent node
    static final String KEY_TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String PUB_DATE = "pubDate";
    static final String LINK = "link";

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView listView;
    private NewsFeedListAdapter listAdapter;
    private List<NewsFeedItem> feedItems;
    private String URL_FEED = "http://timesofindia.indiatimes.com/rssfeedstopstories.cms";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (RecyclerView) findViewById(R.id.list);
        listView.setLayoutManager(new LinearLayoutManager(this));
        feedItems = new ArrayList<NewsFeedItem>();
        callURLFeed();
    }

    private void callURLFeed() {

        StringRequest stringRequest = new StringRequest(URL_FEED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            parseFeed(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public org.w3c.dom.Document getDomElement(String xml){
        org.w3c.dom.Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);
        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    private void parseFeed(String response) {
        try {
            org.w3c.dom.Document doc = getDomElement(response);
            NodeList nl = doc.getElementsByTagName(KEY_ITEM);

            for (int i = 0; i < nl.getLength(); i++) {
                Element e = (Element) nl.item(i);
                NewsFeedItem item = new NewsFeedItem();
                item.setTitle(getValue(e, KEY_TITLE));
                item.setDescription(getValue(e, DESCRIPTION));
                item.setPubDate(getValue(e, PUB_DATE));
                item.setLink(getValue(e, LINK));
                feedItems.add(item);
            }

            listAdapter = new NewsFeedListAdapter(this, feedItems);
            listView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final String getElementValue( Node elem ) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
