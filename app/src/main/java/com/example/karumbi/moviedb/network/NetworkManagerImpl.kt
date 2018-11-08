package com.example.karumbi.moviedb.network

import com.example.karumbi.moviedb.model.Movie
import com.example.karumbi.moviedb.model.MovieResult
import com.example.karumbi.moviedb.util.Constants

import java.io.IOException

import javax.inject.Inject

import rx.Observable

class NetworkManagerImpl : NetworkManager {

  @Inject
  internal var apiService: ApiService = ApiServiceFactory(Constants.BASE_URL).apiService

  init {
    //App.INSTANCE.networkComponent.inject(this);
  }

  override fun getMovieDetail(movieId: String): Observable<Movie> {
    return Observable.create { subscriber ->
      try {
        val movie = apiService.getMovie(movieId)
            .execute()
            .body()
        subscriber.onNext(movie)
        subscriber.onCompleted()
      } catch (e: IOException) {
        e.printStackTrace()
        subscriber.onError(e)
      }
    }
  }

  override fun fetchMovies(): Observable<MovieResult> {
    return Observable.create { subscriber ->
      try {
        val result = apiService.popularMovies
            .execute()
            .body()
        subscriber.onNext(result)
        subscriber.onCompleted()
      } catch (e: Exception) {
        e.printStackTrace()
        subscriber.onError(e)
      }
    }
  }

  override fun searchMovies(query: String): Observable<MovieResult> {
    return Observable.create { subscriber ->
      try {
        val result = apiService.searchMovies(query)
            .execute()
            .body()
        subscriber.onNext(result)
        subscriber.onCompleted()
      } catch (e: Exception) {
        e.printStackTrace()
        subscriber.onCompleted()
      }
    }
  }
}
