package org.vb.flickrdphotos.entity;

import android.content.Context;

import com.codepath.apps.restclienttemplate.R;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("id")
    private String uid;
    @SerializedName("title")
    private String name;
    @SerializedName("farm")
    private int farm;
    @SerializedName("server")
    private int server;
    @SerializedName("secret")
    private String secret;

    private String url;

    public Photo() {
    }

    public String getUid() {
        return uid;
    }

    public String getUrl(Context context) {
        return context.getString(R.string.image_load_url, farm, server, uid, secret);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Item{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
