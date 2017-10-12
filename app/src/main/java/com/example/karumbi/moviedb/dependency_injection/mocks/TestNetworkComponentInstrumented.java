package com.example.karumbi.moviedb.dependency_injection.mocks;

import com.example.karumbi.moviedb.dependency_injection.NetworkComponent;
import com.example.karumbi.moviedb.network.NetworkManager;
import com.example.karumbi.moviedb.viewmodel.MovieDetailViewModel;
import com.example.karumbi.moviedb.viewmodel.MovieListViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestNetworkModuleInstrumented.class})
public interface TestNetworkComponentInstrumented extends NetworkComponent {
    @Override
    void inject(MovieListViewModel viewModel);

    @Override
    void inject(MovieDetailViewModel viewModel);

    @Override
    void inject(NetworkManager networkManager);
}
