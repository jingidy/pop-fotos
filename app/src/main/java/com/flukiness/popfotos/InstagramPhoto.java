package com.flukiness.popfotos;

/**
 * Created by Jing Jin on 9/11/14.
 */
public class InstagramPhoto {
    public String username;
    public String caption;
    public String imageURL;
    public int imageHeight;
    public int numLikes;

    public String toString() {
        return username + " \"" + caption + "\" (" + imageURL + ")";
    }
}
