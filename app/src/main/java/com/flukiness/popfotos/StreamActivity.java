package com.flukiness.popfotos;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class StreamActivity extends Activity {
    private static final String CLIENT_ID="d0ef8282aa61438db297b019420ce2f9";
    private static final String POPULAR_PHOTOS_URL="https://api.instagram.com/v1/media/popular?client_id=";

    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter photosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        // Initialize members.
        photos = new ArrayList<InstagramPhoto>();
        photosAdapter = new InstagramPhotosAdapter(this, photos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(photosAdapter);

        fetchPopularPhotos();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stream, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchPopularPhotos() {
        String requestURL = POPULAR_PHOTOS_URL + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(requestURL, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                photos.clear();
                JSONArray photosJson = null;
                try {
                    photosJson = response.getJSONArray("data");
                    for (int i = 0; i < photosJson.length(); i++) {
                        JSONObject photoJson = photosJson.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto();
                        photo.username = photoJson.getJSONObject("user").getString("username");
                        photo.numLikes = photoJson.getJSONObject("likes").getInt("count");

                        JSONObject imageJson = photoJson.getJSONObject("images").getJSONObject("standard_resolution");
                        photo.imageURL = imageJson.getString("url");
                        photo.imageHeight = imageJson.getInt("height");
                        photos.add(photo);

                        JSONObject captionJson = photoJson.getJSONObject("caption");
                        if (captionJson != null) {
                            photo.caption =captionJson.getString("text");
                        }
                    }

                    photosAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
