package com.sphereinc.chairlift.api.facade;

import com.sphereinc.chairlift.api.entity.Department;
import com.sphereinc.chairlift.api.entity.response.DepartmentsSearchResult;

import retrofit2.Callback;

public interface DepartmentFacade {

    void getDepartmentsByName(String departmentName, Callback<DepartmentsSearchResult> callback);

    void getDepartments(Callback<DepartmentsSearchResult> callback);

    void getDepartment(int departmentId, Callback<Department> callback);
}
