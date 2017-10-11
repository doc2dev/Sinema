package com.example.karumbi.moviedb.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.model.MovieResult;
import com.example.karumbi.moviedb.network.ApiService;
import com.example.karumbi.moviedb.network.ApiServiceFactory;
import com.example.karumbi.moviedb.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by Karumbi on 11/10/2017.
 */

public class MovieListViewModel extends ViewModel {

  private ApiService apiService;

  public MutableLiveData<List<Movie>> movieObservable;

  public MovieListViewModel() {
    apiService = new ApiServiceFactory(Constants.BASE_URL)
        .getApiService();
    movieObservable = new MutableLiveData<>();
  }

  public void fetchMovies() {
    apiService.getPopularMovies()
        .enqueue(new Callback<MovieResult>() {
          @Override
          public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
            if (response.code() == 200) {
              MovieResult movieResult = response.body();
              Timber.d("Movie list count: %s", String.valueOf(movieResult.getMovieList().size()));
              movieObservable.setValue(movieResult.getMovieList());
            } else {
              Timber.d("Movie list error: %s", String.valueOf(response.code()));
            }
          }

          @Override
          public void onFailure(Call<MovieResult> call, Throwable t) {
            Timber.d("Movie list error: %s", t.getMessage());
          }
        });
  }
}
