package com.example.mobilecodingchallenge.api;

import com.example.mobilecodingchallenge.model.BusinessResponse;
import com.example.mobilecodingchallenge.model.Businesses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Api {

    @GET("search")
    Call<BusinessResponse> getBusinessesBySearchTerm(@Header("Authorization") String apiKey, @Query("term") String searchTerm, @Query("location") String defaultLocation, @Query("limit") int limit);

    @GET("search")
    Call<BusinessResponse> getBusinessesByLocation(@Header("Authorization") String apiKey, @Query("location") String location, @Query("limit") int limit);

    @GET
    Call<Businesses> getBusinessesById(@Header("Authorization") String apiKey, @Url String url);
}
