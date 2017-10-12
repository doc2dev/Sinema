package com.example.karumbi.moviedb.viewmodel;

import com.example.karumbi.moviedb.dependency_injection.NetworkComponent;
import com.example.karumbi.moviedb.model.MovieResult;
import com.example.karumbi.moviedb.network.NetworkManagerInterface;

import javax.inject.Inject;

import rx.Observable;

public class MovieListViewModel {

    private static MovieListViewModel instance;

    @Inject
    NetworkManagerInterface networkManager;

    private MovieListViewModel() {
    }

    public static MovieListViewModel getInstance(NetworkComponent component) {
        if (instance == null) {
            instance = new MovieListViewModel();
        }
        component.inject(instance);
        return instance;
    }

    public Observable<MovieResult> fetchPopularMovies() {
        return networkManager.fetchMovies();
    }

    public Observable<MovieResult> searchMovies(String query) {
        return networkManager.searchMovies(query);
    }
}
