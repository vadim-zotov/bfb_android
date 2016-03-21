package com.sphereinc.chairlift.api;

import com.sphereinc.chairlift.api.entity.User;
import com.sphereinc.chairlift.api.entity.response.UserSearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @Headers("Content-Type: application/json")
    @GET("api/v3/users/{userId}")
    Call<User> getUser(@Path("userId") int userId, @Query("related_data") String relatedData, @Header("Authorization") String header);

    @Headers("Content-Type: application/json")
    @GET("api/v3/users/me")
    Call<User> getMe(@Query("related_data") String relatedData, @Header("Authorization") String header);

    @Headers("Content-Type: application/json")
    @GET("api/v3/users")
    Call<UserSearchResult> search(@Query("search_type") String searchType, @Query("search_value") String searchValue,
                                  @Query("sort_column") String sortColumn,
//                      @Query("sort_direction") String sortDirections,
//                      @Query("show_deactivated") boolean showDeactivated,
                                  @Query("limit") int limit, @Query("offset") int offset,
                                  @Query("filters") String filters,
                                  @Header("Authorization") String header);

}
