package com.example.karumbi.moviedb.viewmodel;


import com.example.karumbi.moviedb.dependency_injection.NetworkComponent;
import com.example.karumbi.moviedb.dependency_injection.mocks.DaggerTestNetworkComponentJvm;
import com.example.karumbi.moviedb.dependency_injection.mocks.TestNetworkModuleJvm;
import com.example.karumbi.moviedb.model.Movie;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Eston on 13/10/2017.
 */
public class MovieDetailViewModelTest {

    private MovieDetailViewModel viewModel;
    private Movie movie;

    @Before
    public void setup() {
        NetworkComponent networkComponent = DaggerTestNetworkComponentJvm.builder()
                .testNetworkModuleJvm(new TestNetworkModuleJvm())
                .build();
        viewModel = MovieDetailViewModel.getInstance();
        viewModel.inject(networkComponent);
    }

    @Test
    public void testGetMovieDetails() throws Exception {
        movie = null;
        CountDownLatch latch = new CountDownLatch(1);
        viewModel.getMovieDetails("390043")
                .subscribe(testMovie -> {
                    movie = testMovie;
                    latch.countDown();
                });
        latch.await();
        assertThat(movie).isNotNull();
        assertThat(movie.getTitle()).isEqualToIgnoringCase("The Hitman's Bodyguard");
        assertThat(movie.getVoteAverage()).isEqualTo(6.5);
    }

}