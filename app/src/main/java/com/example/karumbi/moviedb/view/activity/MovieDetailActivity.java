package com.example.karumbi.moviedb.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.karumbi.moviedb.App;
import com.example.karumbi.moviedb.R;
import com.example.karumbi.moviedb.model.Genre;
import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.util.Utils;
import com.example.karumbi.moviedb.viewmodel.MovieDetailViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.movie_poster)
    ImageView moviePoster;
    @BindView(R.id.movie_details)
    TextView movieDetails;
    @BindView(R.id.loading)
    ProgressBar progressBar;
    @BindView(R.id.detail_content)
    View detailContent;
    @BindView(R.id.movie_tag)
    TextView movieTag;
    @BindView(R.id.movie_genre)
    TextView movieGenre;
    @BindView(R.id.movie_rating)
    TextView movieRating;
    @BindView(R.id.movie_date)
    TextView releaseDate;
    @BindView(R.id.movie_run_time)
    TextView runningTime;
    @BindView(R.id.back)
    View backButton;
    private MovieDetailViewModel viewModel;
    CountingIdlingResource countingIdlingResource = new CountingIdlingResource("DETAILS");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        viewModel = MovieDetailViewModel.getInstance();
        viewModel.inject(App.INSTANCE.networkComponent);
        showAvailableDetails();
    }

    private void showAvailableDetails() {
        countingIdlingResource.increment();
        Movie movie = viewModel.movie;
        Picasso.with(this)
                .load(Utils.getBackdropUrl(movie.getBackdropPath()))
                .into(moviePoster);
        movieDetails.setText(movie.getOverview());
        getSupportActionBar().setTitle(movie.getTitle());
        progressBar.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(view -> onBackPressed());
        fetchMovieDetails(String.valueOf(movie.getId()));
    }

    private void fetchMovieDetails(String id) {
        viewModel.getMovieDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showMovieDetails);
    }

    private void showMovieDetails(Movie movie) {
        progressBar.setVisibility(View.GONE);
        if (movie != null) {
            detailContent.setVisibility(View.VISIBLE);
            movieTag.setText(getString(R.string.tagline, movie.getTagline()));
            StringBuilder genres = new StringBuilder();
            for (int i = 0; i < movie.getGenres().size(); i++) {
                Genre genre = movie.getGenres().get(i);
                genres.append(genre.getName());
                if (i < (movie.getGenres().size() - 1)) {
                    genres.append(", ");
                }
            }
            movieGenre.setText(getString(R.string.genres, genres.toString()));
            movieRating.setText(getString(R.string.rating, String.valueOf(movie.getVoteAverage())));
            releaseDate.setText(getString(R.string.release_date, movie.getReleaseDate()));
            runningTime.setText(getString(R.string.running_time, String.valueOf(movie.getRuntime())));
        }
        countingIdlingResource.decrement();
    }
}
