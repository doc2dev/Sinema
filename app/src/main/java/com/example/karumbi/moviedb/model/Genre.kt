package com.example.karumbi.moviedb.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Karumbi on 11/10/2017.
 */

class Genre {
  @SerializedName("id")
  @Expose
  var id: Int = 0
  @SerializedName("name")
  @Expose
  var name: String? = null
}
