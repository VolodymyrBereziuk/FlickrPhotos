package org.vb.flickrdphotos.entity;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Photo {

    private static final String TAG = Photo.class.getSimpleName();

    private String uid;
    private String name;
    private String url;

    public Photo(JSONObject object) {
        try {
            this.uid = object.getString("id");
            this.name = object.getString("title");
            this.url = "http://farm" + object.getInt("farm") + ".staticflickr.com/" + object.getInt("server") + "/" + uid + "_" + object.getString("secret") + ".jpg";
        } catch (JSONException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
    }

    public String getUid() {
        return uid;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
