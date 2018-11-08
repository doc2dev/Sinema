package com.example.karumbi.moviedb.network

import com.example.karumbi.moviedb.model.Movie
import com.example.karumbi.moviedb.model.MovieResult

import rx.Observable

/**
 * Created by Eston on 12/10/2017.
 */

interface NetworkManager {
  fun getMovieDetail(movieId: String): Observable<Movie>

  fun fetchMovies(): Observable<MovieResult>

  fun searchMovies(query: String): Observable<MovieResult>
}
