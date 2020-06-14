package com.example.firebasephoneverification.api;

import com.example.firebasephoneverification.response.BaseResponse;
import com.example.firebasephoneverification.response.DivisionResponse;
import com.example.firebasephoneverification.response.UserResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiEndpoint {
    @FormUrlEncoded
    @POST("login")
    Observable<BaseResponse> loginUser(@Field("phone") String phone);

    @GET("divisions")
    Observable<DivisionResponse> getDivisionList(@Header("Authorization") String token);

    @GET("user")
    Observable<UserResponse> getLoggedInUserInfo(@Header("Authorization") String token);

}
