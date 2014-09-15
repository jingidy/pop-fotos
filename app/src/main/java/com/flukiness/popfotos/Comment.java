package com.flukiness.popfotos;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jing Jin on 9/15/14.
 */
public class Comment {
    User user;
    String text;

    Comment(JSONObject commentJson) {
        try {
            JSONObject userJson = commentJson.getJSONObject("from");
            user = new User(userJson);
            text = commentJson.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
            if (user == null)
                user = new User();
        }
    }
}
