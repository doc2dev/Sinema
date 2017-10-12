package com.example.karumbi.moviedb.dependency_injection.mocks;

import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.model.MovieResult;
import com.example.karumbi.moviedb.network.NetworkManager;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Observable;

/**
 * Created by Eston on 13/10/2017.
 */

@Module
public class TestNetworkModuleJvm {

    @Provides
    @Singleton
    NetworkManager provideNetworkManager() {
        return new NetworkManager() {
            @Override
            public Observable<Movie> getMovieDetail(String movieId) {
                return Observable.just(getById(movieId));
            }

            @Override
            public Observable<MovieResult> fetchMovies() {
                return Observable.just(getSample());
            }

            @Override
            public Observable<MovieResult> searchMovies(String query) {
                return Observable.just(search(query));
            }
        };
    }

    private Movie getById(String movieId) {
        for (Movie m : getSample().getMovieList()) {
            if (movieId.contentEquals(String.valueOf(m.getId()))) {
                return m;
            }
        }
        return null;
    }

    private MovieResult search(String query) {
        List<Movie> results = new ArrayList<>();
        List<Movie> existing = getSample().getMovieList();
        results.addAll(existing.stream().filter(m -> m.getTitle().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList()));
        MovieResult newResult = new MovieResult();
        newResult.setMovieList(results);
        return newResult;
    }

    private MovieResult getSample() {
        try {
            InputStream is = new FileInputStream("test_list.json");
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
