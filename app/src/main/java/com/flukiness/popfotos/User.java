package com.flukiness.popfotos;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jing Jin on 9/14/14.
 */
public class User {
    public String id;
    public String username;
    public String imageURL;

    public User() {
        // Nothing to do here.
    }

    public User(JSONObject userJson) {
        try {
            id = userJson.getString("id");
            username = userJson.getString("username");
            imageURL = userJson.getString("profile_picture");
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
}
