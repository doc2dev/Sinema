package com.example.karumbi.moviedb.network;

import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.model.MovieResult;

/**
 * Created by Eston on 12/10/2017.
 */

public interface NetworkManagerInterface {
    void getMovieDetail(String movieId, ResultCallback<Movie> callback);

    void fetchMovies(ResultCallback<MovieResult> callback);

    void searchMovies(String query, ResultCallback<MovieResult> callback);
}
