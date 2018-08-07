package com.adwait.crud.data.net;


import com.adwait.crud.data.entity.CommonEntity;
import com.adwait.crud.data.entity.SampleUserListEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by adwait on 18/09/17.
 */

interface CRUDService {



    @GET("/user")
    Call<SampleUserListEntity> getSampleUserList(@QueryMap Map<String, String> params);

    @DELETE("/user/{id}")
    Call<CommonEntity> deleteSampleUserFromApi(@Path("id") String params);

    @POST("/user")
    @Headers("Content-Type: application/json")
    Call<CommonEntity> addSampleUserToApi(@Body SampleUserListEntity.SampleUser user);
}
