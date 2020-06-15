package com.decimalab.sff.api;

import com.decimalab.sff.request.StudentRequest;
import com.decimalab.sff.response.BaseResponse;
import com.decimalab.sff.response.DivisionResponse;
import com.decimalab.sff.response.StudentResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiEndpoint {
    @FormUrlEncoded
    @POST("login")
    Observable<BaseResponse> loginUser(@Field("phone") String phone);

    @GET("divisions")
    Observable<DivisionResponse> getDivisionList(@Header("Authorization") String token);

    @GET("user")
    Observable<StudentResponse> getLoggedInUserInfo(@Header("Authorization") String token);

    @PUT("user")
    Observable<StudentResponse> updateLoggedInUserInfo(@Header("Authorization") String token, @Body StudentRequest studentRequest);
}
