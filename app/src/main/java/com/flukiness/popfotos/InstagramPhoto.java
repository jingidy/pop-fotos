package com.flukiness.popfotos;

/**
 * Created by Jing Jin on 9/11/14.
 */
public class InstagramPhoto {
    public String caption;
    public String imageURL;
    public int imageHeight;
    public int numLikes;

    public User user;

    public InstagramPhoto() {
        user = new User();
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
