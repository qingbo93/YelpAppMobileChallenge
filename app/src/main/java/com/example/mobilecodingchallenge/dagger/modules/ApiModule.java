package com.example.mobilecodingchallenge.dagger.modules;

import android.content.Context;

import com.example.mobilecodingchallenge.R;
import com.example.mobilecodingchallenge.api.Api;
import com.example.mobilecodingchallenge.repository.ApiRepository;
import com.example.mobilecodingchallenge.viewmodel.factory.ApiViewModelFactory;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class ApiModule
{
    @Singleton
    @Provides
    Api providesApiCall(Context context)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_base_url))
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Api.class);
    }

    @Singleton
    @Provides
    ApiRepository providesApiRepository(Api api)
    {
        return new ApiRepository(api);
    }

    @Singleton
    @Provides
    ApiViewModelFactory providesApiViewModelFactory(ApiRepository apiRepository)
    {
        return new ApiViewModelFactory(apiRepository);
    }
}
