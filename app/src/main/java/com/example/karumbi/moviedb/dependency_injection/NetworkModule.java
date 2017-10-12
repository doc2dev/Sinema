package com.example.karumbi.moviedb.dependency_injection;

import com.example.karumbi.moviedb.network.ApiService;
import com.example.karumbi.moviedb.network.ApiServiceFactory;
import com.example.karumbi.moviedb.network.NetworkManager;
import com.example.karumbi.moviedb.network.NetworkManagerInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    private String baseUrl;

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    NetworkManagerInterface provideNetworkManager() {
        return new NetworkManager();
    }

    @Provides
    @Singleton
    ApiService provideApiService() {
        return new ApiServiceFactory(baseUrl)
                .getApiService();
    }
}
