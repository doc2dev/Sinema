package com.example.karumbi.moviedb.network;

import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.model.MovieResult;
import com.example.karumbi.moviedb.util.Constants;

import java.io.IOException;

import javax.inject.Inject;

import rx.Observable;

public class NetworkManagerImpl implements NetworkManager {

    @Inject
    ApiService apiService;

    public NetworkManagerImpl() {
        //App.INSTANCE.networkComponent.inject(this);
        apiService = new ApiServiceFactory(Constants.BASE_URL).getApiService();
    }

    @Override
    public Observable<Movie> getMovieDetail(final String movieId) {
        return Observable.create(subscriber -> {
            try {
                Movie movie = apiService.getMovie(movieId)
                        .execute()
                        .body();
                subscriber.onNext(movie);
                subscriber.onCompleted();
            } catch (IOException e) {
                e.printStackTrace();
                subscriber.onError(e);
            }
        });
    }

    @Override
    public Observable<MovieResult> fetchMovies() {
        return Observable.create(subscriber -> {
            try {
                MovieResult result = apiService.getPopularMovies()
                        .execute()
                        .body();
                subscriber.onNext(result);
                subscriber.onCompleted();
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onError(e);
            }
        });
    }

    @Override
    public Observable<MovieResult> searchMovies(final String query) {
        return Observable.create(subscriber -> {
            try {
                MovieResult result = apiService.searchMovies(query)
                        .execute()
                        .body();
                subscriber.onNext(result);
                subscriber.onCompleted();
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onCompleted();
            }
        });
    }
}
