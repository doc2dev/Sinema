package com.example.karumbi.moviedb.network

import com.example.karumbi.moviedb.model.Movie
import com.example.karumbi.moviedb.model.MovieResult

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Karumbi on 11/10/2017.
 */

interface ApiService {

  @get:GET("movie/popular")
  val popularMovies: Call<MovieResult>

  @GET("movie/{movieId}")
  fun getMovie(@Path("movieId") movieId: String): Call<Movie>

  @GET("search/movie")
  fun searchMovies(@Query("query") query: String): Call<MovieResult>

}
