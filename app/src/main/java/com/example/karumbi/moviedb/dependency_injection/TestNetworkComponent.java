package com.example.karumbi.moviedb.dependency_injection;

import com.example.karumbi.moviedb.network.NetworkManagerInterface;
import com.example.karumbi.moviedb.viewmodel.MovieDetailViewModel;
import com.example.karumbi.moviedb.viewmodel.MovieListViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestNetworkModule.class})
public interface TestNetworkComponent extends NetworkComponent {

    @Override
    void inject(MovieListViewModel viewModel);

    @Override
    void inject(MovieDetailViewModel viewModel);

    @Override
    void inject(NetworkManagerInterface networkManager);

}
