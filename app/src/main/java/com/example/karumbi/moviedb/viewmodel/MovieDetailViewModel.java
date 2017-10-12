package com.example.karumbi.moviedb.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.karumbi.moviedb.App;
import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.network.NetworkManager;
import com.example.karumbi.moviedb.network.NetworkManagerInterface;
import com.example.karumbi.moviedb.network.ResultCallback;

import javax.inject.Inject;

/**
 * Created by Eston on 11/10/2017.
 */

public class MovieDetailViewModel extends ViewModel {

    public MutableLiveData<Movie> movieObservable;

    @Inject
    NetworkManagerInterface networkManager;

    public MovieDetailViewModel() {
        movieObservable = new MutableLiveData<>();
        App.INSTANCE.networkComponent.inject(this);
    }

    public void getMovieDetails(String movieId) {
        networkManager.getMovieDetail(movieId, new ResultCallback<Movie>() {
            @Override
            public void onResult(Movie result) {
                movieObservable.setValue(result);
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
