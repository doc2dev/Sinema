package com.example.karumbi.moviedb.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karumbi.moviedb.R;
import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.util.Utils;
import com.example.karumbi.moviedb.view.activity.MovieDetailActivity;
import com.example.karumbi.moviedb.viewmodel.MovieDetailViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private List<Movie> movies;

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (movies != null)
            return movies.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (movies != null)
            return movies.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (movies != null)
            return movies.get(position).getId();
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final View root = LayoutInflater.from(context)
                .inflate(R.layout.search_item, viewGroup, false);
        if (movies != null) {
            final Movie movie = movies.get(position);
            ImageView thumb = root.findViewById(R.id.movie_thumb);
            TextView title = root.findViewById(R.id.movie_title);
            title.setText(movie.getTitle());
            Picasso.with(context)
                    .load(Utils.getPosterUrl(movie.getPosterPath()))
                    .placeholder(R.drawable.movie_placeholder)
                    .into(thumb);
            root.setOnClickListener(view1 -> {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                MovieDetailViewModel.getInstance().movie = movie;
                context.startActivity(intent);
            });
        }
        return root;
    }
}
