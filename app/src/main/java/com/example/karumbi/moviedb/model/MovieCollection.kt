package com.example.karumbi.moviedb.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieCollection {

  @SerializedName("id")
  @Expose
  var id: Int = 0
  @SerializedName("name")
  @Expose
  var name: String? = null
  @SerializedName("poster_path")
  @Expose
  var posterPath: String? = null
  @SerializedName("backdrop_path")
  @Expose
  var backdropPath: String? = null
}
