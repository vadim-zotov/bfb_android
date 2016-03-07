package com.sphereinc.chairlift.views.models;


import com.sphereinc.chairlift.api.entity.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentModel {
    private int departmentId;

    private Department department;

    private List<DepartmentModel> childDepartment;


    public DepartmentModel(Department department) {
        this.departmentId = department.getId();
        this.department = department;
        this.childDepartment = new ArrayList<DepartmentModel>();
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public List<DepartmentModel> getChildDepartment() {
        return childDepartment;
    }

    public void setChildDepartment(List<DepartmentModel> childDepartment) {
        this.childDepartment = childDepartment;
    }
}
