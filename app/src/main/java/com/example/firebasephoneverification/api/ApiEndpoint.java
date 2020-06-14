package com.example.firebasephoneverification.api;

import com.example.firebasephoneverification.response.BaseResponse;
import com.example.firebasephoneverification.response.CollegeResponse;
import com.example.firebasephoneverification.response.DivisionResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndpoint {
    @FormUrlEncoded
    @POST("login")
    Observable<BaseResponse> loginUser(@Field("phone") String phone);

    @GET("divisions")
    Observable<DivisionResponse> getDivisionList(@Header("Authorization") String token);

    @GET("colleges")
    Observable<CollegeResponse> getCollegeListByDivisionId(@Header("Authorization") String token, @Query("division_id") int division_id);
}
