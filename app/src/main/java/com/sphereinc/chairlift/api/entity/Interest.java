package com.sphereinc.chairlift.api.entity;


import com.google.gson.annotations.SerializedName;

public class Interest {
    @SerializedName("id")
    public int id;

    @SerializedName("title")
    public String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
