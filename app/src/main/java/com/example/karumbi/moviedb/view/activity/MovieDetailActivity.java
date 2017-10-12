package com.example.karumbi.moviedb.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.karumbi.moviedb.App;
import com.example.karumbi.moviedb.R;
import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.util.Utils;
import com.example.karumbi.moviedb.viewmodel.MovieDetailViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
    private MovieDetailViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        viewModel = MovieDetailViewModel.getInstance();
        viewModel.inject(App.INSTANCE.networkComponent);
        Movie movie = viewModel.movie;
        Picasso.with(this)
                .load(Utils.getBackdropUrl(movie.getPosterPath()))
                .into(moviePoster);
        movieDetails.setText(movie.getOverview());
        getSupportActionBar().setTitle(movie.getTitle());
        progressBar.setVisibility(View.VISIBLE);
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

        }
    }
}
