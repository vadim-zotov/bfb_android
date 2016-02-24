package com.sphereinc.chairlift.api.entity;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UserSearchResult implements Serializable {

    @SerializedName("emergency_role")
    private int totalCount;
    @SerializedName("users")
    private List<User> users;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
