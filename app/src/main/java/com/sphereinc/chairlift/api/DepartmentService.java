package com.sphereinc.chairlift.api;

import com.sphereinc.chairlift.api.entity.Department;
import com.sphereinc.chairlift.api.entity.response.DepartmentsSearchResult;
import com.sphereinc.chairlift.api.entity.response.UserSearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DepartmentService {

    @Headers("Content-Type: application/json")
    @GET("api/v3/department/{departmentId}")
    Call<Department> getDepartment(@Path("departmentId") int departmentId, @Header("Authorization") String header);

    @Headers("Content-Type: application/json")
    @GET("api/v3/departments")
    Call<DepartmentsSearchResult> getDepartments(
            @Query("limit") int limit, @Query("offset") int offset,
            @Header("Authorization") String header);

    @Headers("Content-Type: application/json")
    @GET("api/v3/departments")
    Call<DepartmentsSearchResult> getDepartmentsByName(
            @Query("limit") int limit, @Query("offset") int offset,
            @Query("name_contains") String filters,
            @Header("Authorization") String header);

}
