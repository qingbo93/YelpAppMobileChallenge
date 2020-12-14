package com.example.mobilecodingchallenge;

import android.app.Application;

import com.example.mobilecodingchallenge.dagger.components.DaggerMobileCodingChallengeComponent;
import com.example.mobilecodingchallenge.dagger.components.MobileCodingChallengeComponent;
import com.example.mobilecodingchallenge.dagger.modules.ApiModule;
import com.example.mobilecodingchallenge.dagger.modules.AppModule;

public class MobileCodingChallengeApplication extends Application {
    private MobileCodingChallengeComponent mobileCodingChallengeComponent;

    public MobileCodingChallengeComponent getMobileCodingChallengeComponent() {
        return this.mobileCodingChallengeComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.mobileCodingChallengeComponent = DaggerMobileCodingChallengeComponent.builder()
                .appModule(new AppModule(this.getApplicationContext(), this))
                .apiModule(new ApiModule())
                .build();
    }
}
