package com.example.karumbi.moviedb.dependency_injection;

import android.content.Context;

import com.example.karumbi.moviedb.App;
import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.model.MovieResult;
import com.example.karumbi.moviedb.network.NetworkManagerInterface;
import com.example.karumbi.moviedb.network.ResultCallback;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestNetworkModule {

    @Provides
    @Singleton
    NetworkManagerInterface provideNetworkManager() {
        return new NetworkManagerInterface() {
            @Override
            public void getMovieDetail(String movieId, ResultCallback<Movie> callback) {
                callback.onResult(getSample().getMovieList().get(0));
            }

            @Override
            public void fetchMovies(ResultCallback<MovieResult> callback) {
                MovieResult result = getSample();
                callback.onResult(result);
            }

            @Override
            public void searchMovies(String query, ResultCallback<MovieResult> callback) {
                callback.onResult(getSample());
            }
        };
    }

    private MovieResult getSample() {
        try {
            Context context = App.INSTANCE;
            InputStream is = context.getAssets().open("test_list.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            String textLine = bufferedReader.readLine();
            while (textLine != null) {
                builder.append(textLine);
                textLine = bufferedReader.readLine();
            }
            return new Gson().fromJson(builder.toString(), MovieResult.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
