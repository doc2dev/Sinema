package com.example.karumbi.moviedb.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karumbi.moviedb.R;
import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.util.Utils;
import com.example.karumbi.moviedb.view.activity.MovieDetailActivity;
import com.example.karumbi.moviedb.viewmodel.MovieDetailViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Karumbi on 11/10/2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.VH> {

    private List<Movie> movies;

    public MovieListAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //Timber.d("Creating view holder...");
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new VH(root);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        //Timber.d("Binding view holder...");
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        //Timber.d("Item count...");
        return movies.size();
    }

    static class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_title)
        TextView movieTitle;
        @BindView(R.id.movie_poster)
        ImageView moviePoster;
        @BindView(R.id.movie_short_desc)
        TextView movieTag;
        @BindView(R.id.movie_rating)
        TextView movieRating;
        View root;

        VH(View itemView) {
            super(itemView);
            root = itemView;
            ButterKnife.bind(this, itemView);
        }

        void bind(final Movie movie) {
            movieTitle.setText(root.getContext().getString(R.string.movie_title, movie.getTitle(), getYearText(movie)));
            movieTag.setText(Utils.truncate(movie.getOverview()));
            movieRating.setText(String.valueOf(movie.getVoteAverage()));
            Picasso.with(root.getContext())
                    .load(Utils.getBackdropUrl(movie.getBackdropPath()))
                    .into(moviePoster);
            root.setOnClickListener(view -> {
                Intent intent = new Intent(root.getContext(), MovieDetailActivity.class);
                MovieDetailViewModel.getInstance().movie = movie;
                if (root.getContext() instanceof Activity) {
                    Activity activity = (Activity) root.getContext();
                    ActivityOptionsCompat optionsCompat =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    activity, root, "imageTransition"
                            );
                    activity.startActivity(intent, optionsCompat.toBundle());
                }
            });
        }

        private String getYearText(Movie movie) {
            String dateString = movie.getReleaseDate();
            String[] splits = dateString.split("-");
            return splits[0];
        }
    }
}
