package org.vb.flickrdphotos.entity;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photos {

    @SerializedName("photo")
    private List<Photo> list;

    public List<Photo> getList() {
        return list;
    }

    public void setList(List<Photo> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Photos{" +
                "list=" + list +
                '}';
    }
}
