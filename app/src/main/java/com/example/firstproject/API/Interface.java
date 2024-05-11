package com.example.firstproject.API;


import com.example.firstproject.APIResponse.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Interface {


    @POST("/login/")
    Call<LoginResponse> login(@Query("email") String email, @Query("password")String password);


    @POST("/register/")
    Call<LoginResponse> register(@Query("name") String name,@Query("email")String email,@Query("password")String password);

//    @Multipart
//    @POST("e")
//    Call<LoginResponse> response(@Field(""))
}
