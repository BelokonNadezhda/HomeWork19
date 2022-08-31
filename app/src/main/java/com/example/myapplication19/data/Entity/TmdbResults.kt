package com.example.myapplication19.data.Entity

import com.google.gson.annotations.SerializedName

data class TmdbResults(
/* @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val tmdbFilms: List<TmdbFilm1>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int*/
  @SerializedName("total")
  val total: Int,
  @SerializedName("items")
  val tmdbFilms: List<Item>
)