package com.sphereinc.chairlift.api.entity;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Department implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("company_id")
    public int companyId;

    @SerializedName("parent_department_id")
    public int parentDepartmentId;

    @SerializedName("users_count")
    public String usersCount;

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

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setParentDepartmentId(int parentDepartmentId) {
        this.parentDepartmentId = parentDepartmentId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public int getParentDepartmentId() {
        return parentDepartmentId;
    }

    public String getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(String usersCount) {
        this.usersCount = usersCount;
    }
}
