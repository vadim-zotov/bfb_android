package com.sphereinc.chairlift.api.facadeimpl;

import com.sphereinc.chairlift.api.DepartmentService;
import com.sphereinc.chairlift.api.ServiceGenerator;
import com.sphereinc.chairlift.api.entity.Department;
import com.sphereinc.chairlift.api.entity.User;
import com.sphereinc.chairlift.api.entity.response.DepartmentsSearchResult;
import com.sphereinc.chairlift.api.entity.response.UserSearchResult;
import com.sphereinc.chairlift.api.facade.BaseFacade;
import com.sphereinc.chairlift.api.facade.DepartmentFacade;
import com.sphereinc.chairlift.api.facade.UserFacade;

import retrofit2.Callback;

public class DepartmentFacadeImpl extends BaseFacade implements DepartmentFacade {

    DepartmentService departmentService = ServiceGenerator.createService(DepartmentService.class);

    @Override
    public void getDepartments(Callback<DepartmentsSearchResult> callback) {
        departmentService.getDepartments(1000, 0, getAuthorizationString()).enqueue(callback);
    }

    @Override
    public void getDepartmentsByName(String departmentName, Callback<DepartmentsSearchResult> callback) {
        departmentService.getDepartmentsByName(1000, 0, "", getAuthorizationString()).enqueue(callback);
    }

    public void getDepartment(int departmentId, Callback<Department> callback) {
        departmentService.getDepartment(departmentId, getAuthorizationString()).enqueue(callback);
    }


}
