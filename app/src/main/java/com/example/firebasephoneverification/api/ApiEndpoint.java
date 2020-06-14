package com.example.firebasephoneverification.api;

import com.example.firebasephoneverification.response.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiEndpoint {
    @FormUrlEncoded
    @POST("login")
    Observable<BaseResponse> loginUser(@Field("phone") String phone);
}
