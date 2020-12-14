package com.example.mobilecodingchallenge.dagger.components;

import com.example.mobilecodingchallenge.Activity.SearchActivity;
import com.example.mobilecodingchallenge.dagger.modules.ApiModule;
import com.example.mobilecodingchallenge.dagger.modules.AppModule;
import com.example.mobilecodingchallenge.Activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface MobileCodingChallengeComponent
{
    void inject(MainActivity mainActivity);

    void inject(SearchActivity searchActivity);
}
