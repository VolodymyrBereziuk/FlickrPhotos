package org.vb.flickrdphotos.entity;

import com.google.gson.annotations.SerializedName;


public class ResultData {

    @SerializedName("photos")
    private Photos photos;

    public Photos getPhotos() {
        return photos;
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "photos=" + photos +
                '}';
    }
}
