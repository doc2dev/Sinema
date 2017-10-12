package com.example.karumbi.moviedb.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.karumbi.moviedb.R;
import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.util.Utils;
import com.example.karumbi.moviedb.viewmodel.MovieDetailViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String MOVIE_TITLE = "movie_title";
    public static final String MOVIE_POSTER = "movie_poster";
    public static final String MOVIE_ID = "movie_id";
    public static final String MOVIE_DETAILS = "movie_details";

    private MovieDetailViewModel viewModel;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.movie_poster)
    ImageView moviePoster;
    @BindView(R.id.movie_details)
    TextView movieDetails;
    @BindView(R.id.loading)
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        String posterPath = getIntent().getStringExtra(MOVIE_POSTER);
        Picasso.with(this)
                .load(Utils.getBackdropUrl(posterPath))
                .into(moviePoster);
        String detailsSummary = getIntent().getStringExtra(MOVIE_DETAILS);
        movieDetails.setText(detailsSummary);
        String title = getIntent().getStringExtra(MOVIE_TITLE);
        getSupportActionBar().setTitle(title);
        viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        viewModel.movieObservable.observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                showMovieDetails(movie);
            }
        });
        progressBar.setVisibility(View.VISIBLE);
        String id = String.valueOf(getIntent().getIntExtra(MOVIE_ID, -1));
        viewModel.getMovieDetails(id);
    }

    private void showMovieDetails(Movie movie) {
        progressBar.setVisibility(View.GONE);
        if (movie != null) {

        }
    }
}
