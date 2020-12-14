package com.example.mobilecodingchallenge.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.mobilecodingchallenge.api.Api;
import com.example.mobilecodingchallenge.model.BusinessResponse;
import com.example.mobilecodingchallenge.model.Businesses;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository {

    private final Api api;

    private Call<BusinessResponse> businessesResponseCall;

    private Call<Businesses> businessesByIdCall;

    public ApiRepository(Api api)
    {
        this.api = api;
    }

    public void getBusinessesByKeyTermList(String apiKey, String keyTerm, MutableLiveData<List<Businesses>> businessesMutableLiveData)
    {
        businessesResponseCall = api.getBusinessesBySearchTerm(apiKey, keyTerm, "toronto", 10);
        businessesResponseCall.enqueue(new Callback<BusinessResponse>() {
            @Override
            public void onResponse(Call<BusinessResponse> call, Response<BusinessResponse> response) {
                businessesMutableLiveData.postValue(response.body().getBusinesses());
            }

            @Override
            public void onFailure(Call<BusinessResponse> call, Throwable t) {
                Log.e("API Call", "Failed to retrieve business list by key term");
            }
        });
    }

    public void getBusinessesByLocationList(String apiKey, String location, MutableLiveData<List<Businesses>> businessesMutableLiveData)
    {
        businessesResponseCall = api.getBusinessesByLocation(apiKey, location,  10);
        businessesResponseCall.enqueue(new Callback<BusinessResponse>() {
            @Override
            public void onResponse(Call<BusinessResponse> call, Response<BusinessResponse> response) {
                businessesMutableLiveData.postValue(response.body().getBusinesses());

            }
            @Override
            public void onFailure(Call<BusinessResponse> call, Throwable t) {
                Log.e("API Call", "Failed to retrieve business list by key term");
            }
        });
    }

    public void getBusinessesById(String apiKey, String id, MutableLiveData<Businesses> businessesMutableLiveData)
    {
        businessesByIdCall = api.getBusinessesById(apiKey, id);
        businessesByIdCall.enqueue(new Callback<Businesses>() {
            @Override
            public void onResponse(Call<Businesses> call, Response<Businesses> response) {
                businessesMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Businesses> call, Throwable t) {
                Log.e("API Call", "Failed to retrieve business list by id");
            }
        });
    }
}
