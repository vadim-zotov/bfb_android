package com.sphereinc.chairlift.api.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JobRole implements Serializable {
    @SerializedName("description")
    public String description;

    @SerializedName("title")
    public String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
