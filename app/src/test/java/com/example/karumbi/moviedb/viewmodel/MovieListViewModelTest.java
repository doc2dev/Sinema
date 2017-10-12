package com.example.karumbi.moviedb.viewmodel;

import com.example.karumbi.moviedb.dependency_injection.NetworkComponent;
import com.example.karumbi.moviedb.dependency_injection.mocks.DaggerTestNetworkComponentJvm;
import com.example.karumbi.moviedb.dependency_injection.mocks.TestNetworkModuleJvm;
import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.model.MovieResult;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import rx.schedulers.Schedulers;

import static org.assertj.core.api.Assertions.assertThat;

public class MovieListViewModelTest {

    private MovieListViewModel viewModel;
    private MovieResult popularMovieResult;
    private MovieResult searchMovieResult;

    @Before
    public void setup() {
        NetworkComponent networkComponent = DaggerTestNetworkComponentJvm.builder()
                .testNetworkModuleJvm(new TestNetworkModuleJvm())
                .build();
        viewModel = MovieListViewModel.getInstance(networkComponent);
    }

    @Test
    public void testFetchPopularMovies() throws Exception {
        popularMovieResult = null;
        CountDownLatch latch = new CountDownLatch(1);
        viewModel.fetchPopularMovies()
                .observeOn(Schedulers.io())
                .subscribe(testResult -> {
                    popularMovieResult = testResult;
                    latch.countDown();
                });
        latch.await();
        assertThat(popularMovieResult).isNotNull();
        List<Movie> movies = popularMovieResult.getMovieList();
        assertThat(movies.size()).isEqualTo(20);
    }

    @Test
    public void testSearchMovies() throws Exception {
        String query = "man";
        searchMovieResult = null;
        CountDownLatch latch = new CountDownLatch(1);
        viewModel.searchMovies(query)
                .observeOn(Schedulers.io())
                .subscribe(testResult -> {
                    searchMovieResult = testResult;
                    latch.countDown();
                });
        latch.await();
        assertThat(searchMovieResult).isNotNull();
        List<Movie> movies = searchMovieResult.getMovieList();
        assertThat(movies.size()).isEqualTo(4);
    }

}