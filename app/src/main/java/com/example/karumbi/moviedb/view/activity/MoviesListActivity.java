package com.example.karumbi.moviedb.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.karumbi.moviedb.App;
import com.example.karumbi.moviedb.R;
import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.model.MovieResult;
import com.example.karumbi.moviedb.view.adapter.MovieListAdapter;
import com.example.karumbi.moviedb.view.adapter.SearchAdapter;
import com.example.karumbi.moviedb.viewmodel.MovieListViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
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

    CountingIdlingResource countingIdlingResource = new CountingIdlingResource("LOADER");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_list);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.popular_title);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.label_loading));
        viewModel = MovieListViewModel.getInstance(App.INSTANCE.networkComponent);
        countingIdlingResource.increment();
        fetchMovies();
        setUpSearch();
    }

    private void setUpSearch() {
        searchAdapter = new SearchAdapter(this);
        searchResults.setAdapter(searchAdapter);
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
        searchView.setOnCloseListener(() -> {
            searchResults.setVisibility(View.GONE);
            return false;
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
        //idlingResource.setIdleState(true);
        countingIdlingResource.decrement();
    }

    private void fetchMovies() {
        progressDialog.show();
        viewModel.fetchPopularMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieResult>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.hide();
                    }

                    @Override
                    public void onNext(MovieResult movieResult) {
                        displayMovieList(movieResult.getMovieList());
                    }
                });
    }

    private void findMovie(String query) {
        Timber.d("Search query: %s", query);
        if (query.isEmpty()) {
            searchResults.setVisibility(View.GONE);
        } else {
            searchResults.setVisibility(View.VISIBLE);
        }
        viewModel.searchMovies(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieResult movieResult) {
                        searchAdapter.setMovies(movieResult.getMovieList());
                    }
                });
    }
}
