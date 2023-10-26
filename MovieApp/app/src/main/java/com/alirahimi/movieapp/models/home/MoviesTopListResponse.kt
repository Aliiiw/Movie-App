package com.alirahimi.movieapp.models.home


import com.google.gson.annotations.SerializedName

data class MoviesTopListResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("metadata")
    val metadata: Metadata
) {
    data class Data(
        @SerializedName("country")
        val country: String, // USA
        @SerializedName("genres")
        val genres: List<String>,
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("images")
        val images: List<String>,
        @SerializedName("imdb_rating")
        val imdbRating: String, // 9.3
        @SerializedName("poster")
        val poster: String, // https://moviesapi.ir/images/tt0111161_poster.jpg
        @SerializedName("title")
        val title: String, // The Shawshank Redemption
        @SerializedName("year")
        val year: String // 1994
    )

    data class Metadata(
        @SerializedName("current_page")
        val currentPage: String, // 1
        @SerializedName("page_count")
        val pageCount: Int, // 6
        @SerializedName("per_page")
        val perPage: Int, // 10
        @SerializedName("total_count")
        val totalCount: Int // 59
    )
}