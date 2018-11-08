package com.example.karumbi.moviedb.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class Movie(
    @SerializedName("adult")
    @Expose
    var isAdult: Boolean = false,
    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null,
    @SerializedName("belongs_to_collection")
    @Expose
    var belongsToCollection: MovieCollection? = null,
    @SerializedName("budget")
    @Expose
    var budget: Int = 0,
    @SerializedName("genres")
    @Expose
    var genres: List<Genre> = ArrayList(),
    @SerializedName("homepage")
    @Expose
    var homepage: String? = null,
    @SerializedName("id")
    @Expose
    var id: Int = 0,
    @SerializedName("imdb_id")
    @Expose
    var imdbId: String? = null,
    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null,
    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null,
    @SerializedName("overview")
    @Expose
    var overview: String? = null,
    @SerializedName("popularity")
    @Expose
    var popularity: Double = 0.toDouble(),
    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null,
    @SerializedName("production_companies")
    @Expose
    var productionCompanies: List<ProductionCompany> = ArrayList(),
    @SerializedName("production_countries")
    @Expose
    var productionCountries: List<ProductionCountry> = ArrayList(),
    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null,
    @SerializedName("revenue")
    @Expose
    var revenue: Int = 0,
    @SerializedName("runtime")
    @Expose
    var runtime: Int = 0,
    @SerializedName("spoken_languages")
    @Expose
    var spokenLanguages: List<SpokenLanguage> = ArrayList(),
    @SerializedName("status")
    @Expose
    var status: String? = null,
    @SerializedName("tagline")
    @Expose
    var tagline: String? = null,
    @SerializedName("title")
    @Expose
    var title: String? = null,
    @SerializedName("video")
    @Expose
    var isVideo: Boolean = false,
    @SerializedName("vote_average")
    @Expose
    var voteAverage: Double = 0.toDouble(),
    @SerializedName("vote_count")
    @Expose
    var voteCount: Int = 0
)
