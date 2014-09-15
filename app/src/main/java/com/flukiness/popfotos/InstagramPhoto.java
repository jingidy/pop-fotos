package com.flukiness.popfotos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Jing Jin on 9/11/14.
 */
public class InstagramPhoto {
    public String caption;
    public String imageURL;
    public int imageHeight;
    public int numLikes;
    public Date time;

    public User user;
    public ArrayList<Comment> comments; // From newest to oldest.

    public InstagramPhoto(JSONObject photoJson) {
        try {
            JSONObject userJson = photoJson.getJSONObject("user");
            user = new User(userJson);

            JSONObject imageJson = photoJson.getJSONObject("images").getJSONObject("standard_resolution");
            imageURL = imageJson.getString("url");
            imageHeight = imageJson.getInt("height");
            numLikes = photoJson.getJSONObject("likes").getInt("count");
            time = new Date(Long.parseLong(photoJson.getString("created_time"))*1000);

            if (!photoJson.isNull("caption")) {
                caption = photoJson.getJSONObject("caption").getString("text");
            }

            if (!photoJson.isNull("comments")) {
                comments = commentsFromJson(photoJson.getJSONObject("comments"));
            } else {
                comments = new ArrayList<Comment>();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            user = new User();
            comments = new ArrayList<Comment>();
        }
    }

    public String toString() {
        return user.username + " \"" + caption + "\" (" + imageURL + ")";
    }

    private ArrayList<Comment> commentsFromJson(JSONObject commentsJson) {
        if (commentsJson.isNull("data")) {
            return new ArrayList<Comment>();
        }

        ArrayList<Comment> comments = new ArrayList<Comment>();
         try {
             JSONArray commentsArray = commentsJson.getJSONArray("data");
             int length = commentsArray.length();
             comments.ensureCapacity(length);

             // The JSON list is oldest to newest, so reverse it.
             for(int i = length - 1; i >= 0; i--)
                 comments.add(new Comment(commentsArray.getJSONObject(i)));
        } catch(JSONException e) {
            e.printStackTrace();
        }

        return comments;
    }
}
