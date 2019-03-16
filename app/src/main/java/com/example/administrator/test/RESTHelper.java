package com.example.administrator.test;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Administrator on 8/3/2017.
 */

public interface RESTHelper
{
    @FormUrlEncoded
    @POST("login")
    Call<JsonObject> login(@Field("email") String email, @Field("password") String password, @Field("device_token") String device_token);

    @FormUrlEncoded
    @POST("forgot-password")
    Call<JsonObject> forgetPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("register")
    Call<JsonObject> signup(@Field("email") String email, @Field("password") String password,
                            @Field("group_code") String group_code, @Field("date_of_birth") String date_of_birth,
                            @Field("gender") String gender,@Field("full_name") String full_name);


    @FormUrlEncoded
    @PUT("user")
    Call<JsonObject> updateProfile(@Field("email") String email, @Field("full_name") String full_name,
                                   @Field("date_of_birth") String date_of_birth, @Field("gender") String gender);

    @Headers("Accept:application/json")
    @POST("verification-email")
    Call<JsonObject> resendVerifyMail(@Body JsonObject emailObject);

    @GET("user")
    Call<JsonObject> getProfile();

    @GET("me")
    Call<JsonObject> getMyLatestProfileDetails();

    @Headers("Accept:application/json")
    @POST("logout")
    Call<JsonObject> logout(@Body JsonObject testObject);
}
