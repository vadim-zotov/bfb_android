package com.sphereinc.chairlift.api.entity.response;


import com.google.gson.annotations.SerializedName;
import com.sphereinc.chairlift.api.entity.User;

import java.io.Serializable;
import java.util.List;

public class UserSearchResult implements Serializable {

    @SerializedName("total_count")
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
