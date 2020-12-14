package com.example.mobilecodingchallenge.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobilecodingchallenge.model.Businesses;
import com.example.mobilecodingchallenge.repository.ApiRepository;

import java.util.List;

public class ApiViewModel extends ViewModel {

    private ApiRepository apiRepository;

    private MutableLiveData<List<Businesses>> businessesResponseMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<Businesses> businessesMutableLiveData = new MutableLiveData<>();

    public ApiViewModel(ApiRepository apiRepository)
    {
        this.apiRepository = apiRepository;
    }

    public LiveData<List<Businesses>> getBusinessesListResponse()
    {
        return businessesResponseMutableLiveData;
    }

    public void getBusinessesByKeyTermList(String apiKey, String keyTerm)
    {
        apiRepository.getBusinessesByKeyTermList(apiKey, keyTerm, businessesResponseMutableLiveData);
    }

    public void getBusinessesByLocationList(String apiKey, String location)
    {
        apiRepository.getBusinessesByLocationList(apiKey, location, businessesResponseMutableLiveData);
    }

    public LiveData<Businesses> getBusinessByIdResponse()
    {
        return businessesMutableLiveData;
    }

    public void getBusinessesById(String apiKey, String id)
    {
        apiRepository.getBusinessesById(apiKey, id, businessesMutableLiveData);
    }
}
