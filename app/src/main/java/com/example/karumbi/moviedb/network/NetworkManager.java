package com.example.karumbi.moviedb.network;

import com.example.karumbi.moviedb.App;
import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.model.MovieResult;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class NetworkManager {

    @Inject
    ApiService apiService;

    public NetworkManager() {
        App.INSTANCE.networkComponent.inject(this);
    }

    public void getMovieDetail(String movieId, final ResultCallback<Movie> callback) {
        apiService.getMovie(movieId)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        if (response.code() == 200) {
                            callback.onResult(response.body());
                        } else {
                            Timber.d("Movie detail unknown error");
                            // movieObservable.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        Timber.d("Movie detail error: %s", t.getMessage());
                        //movieObservable.setValue(null);
                        callback.onError(t.getMessage());
                    }
                });
    }

    public void fetchMovies(final ResultCallback<MovieResult> callback) {
        apiService.getPopularMovies()
                .enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                        if (response.code() == 200) {
                            MovieResult movieResult = response.body();
                            Timber.d("Movie list count: %s", String.valueOf(movieResult.getMovieList().size()));
                            callback.onResult(movieResult);
                        } else {
                            Timber.d("Movie list error: %s", String.valueOf(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResult> call, Throwable t) {
                        Timber.d("Movie list error: %s", t.getMessage());
                        callback.onError(t.getMessage());
                    }
                });
    }

    public void searchMovies(String query, final ResultCallback<MovieResult> callback) {
        apiService.searchMovies(query)
                .enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                        if (response.code() == 200) {
                            callback.onResult(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResult> call, Throwable t) {

                    }
                });
    }
}
