package com.example.karumbi.moviedb.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.karumbi.moviedb.dependency_injection.NetworkComponent;
import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.model.MovieResult;
import com.example.karumbi.moviedb.network.NetworkManagerInterface;
import com.example.karumbi.moviedb.network.ResultCallback;

import java.util.List;

import javax.inject.Inject;

public class MovieListViewModel extends ViewModel {

    public MutableLiveData<List<Movie>> movieObservable;
    public MutableLiveData<List<Movie>> searchObservable;

    @Inject
    NetworkManagerInterface networkManager;

    public MovieListViewModel() {
        movieObservable = new MutableLiveData<>();
        searchObservable = new MutableLiveData<>();
    }

    public void inject(NetworkComponent component) {
        component.inject(this);
    }

    public void fetchMovies() {
        networkManager.fetchMovies(new ResultCallback<MovieResult>() {
            @Override
            public void onResult(MovieResult result) {
                movieObservable.setValue(result.getMovieList());
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    public void searchMovies(String query) {
        networkManager.searchMovies(query, new ResultCallback<MovieResult>() {
            @Override
            public void onResult(MovieResult result) {
                searchObservable.setValue(result.getMovieList());
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
