package com.example.karumbi.moviedb;

import android.app.Application;


import com.example.karumbi.moviedb.dependency_injection.DaggerNetworkComponent;
import com.example.karumbi.moviedb.dependency_injection.NetworkComponent;
import com.example.karumbi.moviedb.dependency_injection.NetworkModule;
import com.example.karumbi.moviedb.util.Constants;

import timber.log.Timber;

/**
 * Created by Karumbi on 11/10/2017.
 */

public class App extends Application {

    public static App INSTANCE;
    public NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        networkComponent = DaggerNetworkComponent
                .builder()
                .networkModule(new NetworkModule(Constants.INSTANCE.getBASE_URL()))
                .build();
    }
}
