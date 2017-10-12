package com.example.karumbi.moviedb.viewmodel;

import com.example.karumbi.moviedb.dependency_injection.NetworkComponent;
import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.network.NetworkManagerInterface;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Eston on 11/10/2017.
 */

public class MovieDetailViewModel {

    private static MovieDetailViewModel instance;

    public Movie movie;

    @Inject
    NetworkManagerInterface networkManager;

    private MovieDetailViewModel() {
    }

    public static MovieDetailViewModel getInstance() {
        if (instance == null) {
            instance = new MovieDetailViewModel();
        }
        return instance;
    }

    public void inject(NetworkComponent component) {
        if (instance != null) {
            component.inject(instance);
        }
    }

    public Observable<Movie> getMovieDetails(String movieId) {
        return networkManager.getMovieDetail(movieId);
    }
}
