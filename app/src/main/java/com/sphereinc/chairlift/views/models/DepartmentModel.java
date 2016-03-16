package com.sphereinc.chairlift.views.models;


import com.sphereinc.chairlift.api.entity.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentModel implements TreeModel {
    private int departmentId;

    private Department department;

    private List<TreeModel> childDepartment;


    public DepartmentModel(Department department) {
        this.departmentId = department.getId();
        this.department = department;
        this.childDepartment = new ArrayList<TreeModel>();
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
}
