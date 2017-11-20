package org.vb.flickrdphotos.models;

public class CountManager {

    private int dislikesCounter = 0;
    private int likesCounter = 0;

    public CountManager() {

    }

    public int getLikes() {
        return ++likesCounter;
    }

    public int getDislikes() {
        return ++dislikesCounter;
    }


}
