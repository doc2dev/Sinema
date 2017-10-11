package com.example.karumbi.moviedb.view.activity;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.karumbi.moviedb.R;
import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.view.adapter.MovieListAdapter;
import com.example.karumbi.moviedb.viewmodel.MovieListViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MoviesList extends AppCompatActivity {

  private MovieListViewModel viewModel;
  private ProgressDialog progressDialog;
  private MovieListAdapter adapter;
  @BindView(R.id.recycler)
  RecyclerView recyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movies_list);
    ButterKnife.bind(this);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle("Popular Movies");
    progressDialog = new ProgressDialog(this);
    progressDialog.setIndeterminate(true);
    viewModel = ViewModelProviders.of(this)
        .get(MovieListViewModel.class);
    viewModel.movieObservable.observe(this, new Observer<List<Movie>>() {
      @Override
      public void onChanged(@Nullable List<Movie> movies) {
        displayMovieList(movies);
      }
    });
    fetchMovies();
  }

  private void displayMovieList(List<Movie> movies) {
    Timber.d("Showing list...");
    adapter = new MovieListAdapter(movies);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
    adapter.notifyDataSetChanged();
    progressDialog.hide();
  }

  private void fetchMovies() {
    progressDialog.show();
    viewModel.fetchMovies();
  }

}
