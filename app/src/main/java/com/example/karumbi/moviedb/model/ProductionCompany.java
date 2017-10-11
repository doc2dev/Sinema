package com.example.karumbi.moviedb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Karumbi on 11/10/2017.
 */

public class ProductionCompany {

  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("id")
  @Expose
  private int id;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
