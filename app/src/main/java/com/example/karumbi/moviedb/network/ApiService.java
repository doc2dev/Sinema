package com.example.karumbi.moviedb.network;

import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.model.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Karumbi on 11/10/2017.
 */

public interface ApiService {

    @GET("movie/popular")
    Call<MovieResult> getPopularMovies();

    @GET("movie/{movieId}")
    Call<Movie> getMovie(@Path("movieId") String movieId);

}
