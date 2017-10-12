package com.example.karumbi.moviedb.dependency_injection;

import com.example.karumbi.moviedb.dependency_injection.NetworkModule;
import com.example.karumbi.moviedb.network.NetworkManagerInterface;
import com.example.karumbi.moviedb.viewmodel.MovieDetailViewModel;
import com.example.karumbi.moviedb.viewmodel.MovieListViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {
    void inject(MovieListViewModel viewModel);

    void inject(MovieDetailViewModel viewModel);

    void inject(NetworkManagerInterface networkManager);
}
