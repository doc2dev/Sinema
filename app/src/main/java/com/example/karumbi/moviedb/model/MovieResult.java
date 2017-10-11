package com.example.karumbi.moviedb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karumbi on 11/10/2017.
 */

public class MovieResult {
  
  @SerializedName("page")
  @Expose
  private int page;
  @SerializedName("total_results")
  @Expose
  private int totalResults;
  @SerializedName("total_pages")
  @Expose
  private int totalPages;
  @SerializedName("results")
  @Expose
  private List<Movie> movieList = new ArrayList<>();

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getTotalResults() {
    return totalResults;
  }

  public void setTotalResults(int totalResults) {
    this.totalResults = totalResults;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public List<Movie> getMovieList() {
    return movieList;
  }

  public void setMovieList(List<Movie> movieList) {
    this.movieList = movieList;
  }
}
