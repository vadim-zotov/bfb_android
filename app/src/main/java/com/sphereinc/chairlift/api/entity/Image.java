package com.sphereinc.chairlift.api.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Image implements Serializable {

    @SerializedName("url")
    private String url;

    @SerializedName("original")
    private ImageLocation original;

    @SerializedName("thumb")
    private ImageLocation thumb;

    @SerializedName("icon")
    private ImageLocation icon;

    @SerializedName("small")
    private ImageLocation small;

    @SerializedName("medium")
    private ImageLocation medium;

    @SerializedName("dashboard")
    private ImageLocation dashboard;

    @SerializedName("large")
    private ImageLocation large;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageLocation getOriginal() {
        return original;
    }

    public void setOriginal(ImageLocation original) {
        this.original = original;
    }

    public ImageLocation getThumb() {
        return thumb;
    }

    public void setThumb(ImageLocation thumb) {
        this.thumb = thumb;
    }

    public ImageLocation getIcon() {
        return icon;
    }

    public void setIcon(ImageLocation icon) {
        this.icon = icon;
    }

    public ImageLocation getSmall() {
        return small;
    }

    public void setSmall(ImageLocation small) {
        this.small = small;
    }

    public ImageLocation getMedium() {
        return medium;
    }

    public void setMedium(ImageLocation medium) {
        this.medium = medium;
    }

    public ImageLocation getDashboard() {
        return dashboard;
    }

    public void setDashboard(ImageLocation dashboard) {
        this.dashboard = dashboard;
    }

    public ImageLocation getLarge() {
        return large;
    }

    public void setLarge(ImageLocation large) {
        this.large = large;
    }
}
