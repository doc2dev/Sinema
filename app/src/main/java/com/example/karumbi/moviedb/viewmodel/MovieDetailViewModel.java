package com.example.karumbi.moviedb.viewmodel;

import com.example.karumbi.moviedb.App;
import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.network.NetworkManagerInterface;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Eston on 11/10/2017.
 */

public class MovieDetailViewModel {

    private static MovieDetailViewModel instance;

    @Inject
    NetworkManagerInterface networkManager;

    private MovieDetailViewModel() {
        App.INSTANCE.networkComponent.inject(this);
    }

    public static MovieDetailViewModel getInstance() {
        if (instance == null) {
            instance = new MovieDetailViewModel();
        }
        return instance;
    }

    public Observable<Movie> getMovieDetails(String movieId) {
        return networkManager.getMovieDetail(movieId);
    }
}
