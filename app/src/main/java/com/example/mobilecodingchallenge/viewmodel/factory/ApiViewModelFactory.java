package com.example.mobilecodingchallenge.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobilecodingchallenge.repository.ApiRepository;
import com.example.mobilecodingchallenge.viewmodel.ApiViewModel;

public class ApiViewModelFactory implements ViewModelProvider.Factory{
    private ApiRepository apiRepository;

    public ApiViewModelFactory(ApiRepository apiRepository)
    {
        this.apiRepository = apiRepository;
    }

    @NonNull
    @Override
    public ApiViewModel create(@NonNull Class modelClass) {
        return new ApiViewModel(apiRepository);
    }
}
