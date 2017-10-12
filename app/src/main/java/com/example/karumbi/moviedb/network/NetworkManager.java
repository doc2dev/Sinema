package com.example.karumbi.moviedb.network;

import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.model.MovieResult;

import rx.Observable;

/**
 * Created by Eston on 12/10/2017.
 */

public interface NetworkManager {
    Observable<Movie> getMovieDetail(String movieId);

    Observable<MovieResult> fetchMovies();

    Observable<MovieResult> searchMovies(String query);
}
