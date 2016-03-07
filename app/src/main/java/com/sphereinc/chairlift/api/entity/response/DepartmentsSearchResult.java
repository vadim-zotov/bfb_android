package com.sphereinc.chairlift.api.entity.response;


import com.google.gson.annotations.SerializedName;
import com.sphereinc.chairlift.api.entity.Department;

import java.io.Serializable;
import java.util.List;

public class DepartmentsSearchResult implements Serializable {

    @SerializedName("total_count")
    private int totalCount;
    @SerializedName("departments")
    private List<Department> departments;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
