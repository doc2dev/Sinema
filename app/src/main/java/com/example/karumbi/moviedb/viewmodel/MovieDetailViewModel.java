package com.example.karumbi.moviedb.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.network.ApiService;
import com.example.karumbi.moviedb.network.ApiServiceFactory;
import com.example.karumbi.moviedb.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by Eston on 11/10/2017.
 */

public class MovieDetailViewModel extends ViewModel {

    public MutableLiveData<Movie> movieObservable;

    private ApiService apiService;

    public MovieDetailViewModel() {
        movieObservable = new MutableLiveData<>();
        apiService = new ApiServiceFactory(Constants.BASE_URL)
                .getApiService();
    }

    public void getMovieDetails(String movieId) {
        apiService.getMovie(movieId)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        if (response.code() == 200) {
                            movieObservable.setValue(response.body());
                        } else {
                            Timber.d("Movie detail unknown error");
                            movieObservable.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        Timber.d("Movie detail error: %s", t.getMessage());
                        movieObservable.setValue(null);
                    }
                });
    }
}
