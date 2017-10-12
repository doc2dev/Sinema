package com.example.karumbi.moviedb.dependency_injection.mocks;

import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.model.MovieResult;
import com.example.karumbi.moviedb.network.NetworkManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Observable;

@Module
public class TestNetworkModuleInstrumented {

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
        return null;
    }
}
