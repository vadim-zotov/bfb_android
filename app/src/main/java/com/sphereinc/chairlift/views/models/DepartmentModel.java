package com.sphereinc.chairlift.views.models;


import com.sphereinc.chairlift.api.entity.Department;
import com.sphereinc.chairlift.api.entity.User;

import java.util.ArrayList;
import java.util.List;

public class DepartmentModel implements TreeModel {
    private int departmentId;

    private Department department;

    private List<TreeModel> childDepartment = new ArrayList<>();

    private boolean childsAreLoaded = false;

    public DepartmentModel(Department department) {
        this.departmentId = department.getId();
        this.department = department;
        this.childDepartment = new ArrayList<>();
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    public int getId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public List<TreeModel> getChilds() {
        return childDepartment;
    }

    public void setChildDepartment(List<TreeModel> childDepartment) {
        this.childDepartment = childDepartment;
    }

    public boolean isChildsAreLoaded() {
        return childsAreLoaded;
    }

    public void setChildsAreLoaded(boolean childsAreLoaded) {
        this.childsAreLoaded = childsAreLoaded;
    }
}
