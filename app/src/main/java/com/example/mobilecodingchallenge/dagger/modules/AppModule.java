package com.example.mobilecodingchallenge.dagger.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;

    private Application application;

    public AppModule(Context context, Application application)
    {
        this.context = context;
        this.application = application;
    }

    @Provides
    @Singleton
    Context providesApplicationContext()
    {
        return context;
    }

    @Provides
    @Singleton
    Application providesApplication()
    {
        return application;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
