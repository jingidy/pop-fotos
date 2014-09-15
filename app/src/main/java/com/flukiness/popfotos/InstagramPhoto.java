package com.flukiness.popfotos;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jing Jin on 9/11/14.
 */
public class InstagramPhoto {
    public String caption;
    public String imageURL;
    public int imageHeight;
    public int numLikes;

    public User user;

    public InstagramPhoto(JSONObject photoJson) {
        try {
            JSONObject userJson = photoJson.getJSONObject("user");
            user = new User(userJson);

            JSONObject imageJson = photoJson.getJSONObject("images").getJSONObject("standard_resolution");
            imageURL = imageJson.getString("url");
            imageHeight = imageJson.getInt("height");
            numLikes = photoJson.getJSONObject("likes").getInt("count");

            if (!photoJson.isNull("caption")) {
                caption = photoJson.getJSONObject("caption").getString("text");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if (user == null) {
                user = new User();
            }
        }
    }

    public String htmlString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<b>").append(user.username).append("</b>")
                .append(" (").append(numLikes).append("\u2661").append(")");

        if (caption != null) {
            sb.append(" - ").append(caption);
        }
        return sb.toString();
    }

    public String toString() {
        return user.username + " \"" + caption + "\" (" + imageURL + ")";
    }
}
