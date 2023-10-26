package com.alirahimi.movieapp.models.home


import com.google.gson.annotations.SerializedName

class GenresListResponse : ArrayList<GenresListResponse.GenresListResponseItem>(){
    data class GenresListResponseItem(
        @SerializedName("id")
        val id: Int?, // 21
        @SerializedName("name")
        val name: String? // Sport
    )
}