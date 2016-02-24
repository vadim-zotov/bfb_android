package com.sphereinc.chairlift.api.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageLocation implements Serializable {
    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
