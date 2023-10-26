package com.alirahimi.movieapp.models.detail


import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("actors")
    val actors: String, // Tim Robbins, Morgan Freeman, Bob Gunton, William Sadler
    @SerializedName("awards")
    val awards: String, // Nominated for 7 Oscars. Another 19 wins & 30 nominations.
    @SerializedName("country")
    val country: String, // USA
    @SerializedName("director")
    val director: String, // Frank Darabont
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("imdb_id")
    val imdbId: String, // tt0111161
    @SerializedName("imdb_rating")
    val imdbRating: String, // 9.3
    @SerializedName("imdb_votes")
    val imdbVotes: String, // 1,738,596
    @SerializedName("metascore")
    val metascore: String, // 80
    @SerializedName("plot")
    val plot: String, // Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.
    @SerializedName("poster")
    val poster: String, // https://moviesapi.ir/images/tt0111161_poster.jpg
    @SerializedName("rated")
    val rated: String, // R
    @SerializedName("released")
    val released: String, // 14 Oct 1994
    @SerializedName("runtime")
    val runtime: String, // 142 min
    @SerializedName("title")
    val title: String, // The Shawshank Redemption
    @SerializedName("type")
    val type: String, // movie
    @SerializedName("writer")
    val writer: String, // Stephen King (short story "Rita Hayworth and Shawshank Redemption"), Frank Darabont (screenplay)
    @SerializedName("year")
    val year: String // 1994
)