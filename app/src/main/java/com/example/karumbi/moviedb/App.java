package com.example.karumbi.moviedb;

import android.app.Application;

import com.example.karumbi.moviedb.dependency_injection.components.DaggerNetworkComponent;
import com.example.karumbi.moviedb.dependency_injection.components.NetworkComponent;
import com.example.karumbi.moviedb.dependency_injection.modules.NetworkModule;
import com.example.karumbi.moviedb.util.Constants;

import timber.log.Timber;

/**
 * Created by Karumbi on 11/10/2017.
 */

public class App extends Application {

    public NetworkComponent networkComponent;
    public static App INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        networkComponent = DaggerNetworkComponent
                .builder()
                .networkModule(new NetworkModule(Constants.BASE_URL))
                .build();
    }
}
