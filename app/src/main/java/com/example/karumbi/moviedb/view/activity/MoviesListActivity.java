package com.example.karumbi.moviedb.view.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.karumbi.moviedb.App;
import com.example.karumbi.moviedb.R;
import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.util.Utils;
import com.example.karumbi.moviedb.view.adapter.MovieListAdapter;
import com.example.karumbi.moviedb.view.adapter.SearchAdapter;
import com.example.karumbi.moviedb.viewmodel.MovieListViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MoviesListActivity extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.movie_search)
    SearchView searchView;
    @BindView(R.id.search_results)
    ListView searchResults;
    private MovieListViewModel viewModel;
    private ProgressDialog progressDialog;
    private MovieListAdapter adapter;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_list);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Popular Movies");
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        viewModel = ViewModelProviders.of(this)
                .get(MovieListViewModel.class);
        viewModel.inject(App.INSTANCE.networkComponent);
        viewModel.movieObservable.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                displayMovieList(movies);
            }
        });
        fetchMovies();
        setUpSearch();
    }

    private void setUpSearch() {
        searchAdapter = new SearchAdapter(this);
        searchResults.setAdapter(searchAdapter);
        viewModel.searchObservable.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                searchAdapter.setMovies(movies);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                findMovie(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                findMovie(query);
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchResults.setVisibility(View.GONE);
                return false;
            }
        });
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            findMovie(query);
        }
    }

    private void findMovie(String query) {
        Timber.d("Search query: %s", query);
        if (query.isEmpty()) {
            searchResults.setVisibility(View.GONE);
        } else {
            searchResults.setVisibility(View.VISIBLE);
        }
        viewModel.searchMovies(query);
    }
}
