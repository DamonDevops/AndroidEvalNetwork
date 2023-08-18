package com.technipixl.network

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

data class ComicsContainer (
    @SerializedName("data")
    val data: ComicsResults
)
data class ComicsResults(
    @SerializedName("results")
    val results :MutableList<ComicItems>
)

data class ComicItems(
    @SerializedName("id")
    val id :Int,
    @SerializedName("name")
    val name :String,
    @SerializedName("thumbnail")
    val thumbnail :Thumbnail? = null,
    @SerializedName("comics")
    val comics :ComicsList
)

data class ComicsList(
    @SerializedName("items")
    val comicList :MutableList<Comic>
)

data class Comic(
    @SerializedName("resourceURI")
    val URI :String? = null,
    @SerializedName("name")
    val name :String? = null
)

interface ComicsService{
    @Headers("Content-type: application/json")
    @GET("characters/{id}")
    suspend fun getComics(
        @Path("id") id :String,
        @Query("apikey", encoded = false) pubKey :String,
        @Query("ts", encoded = false) timestamp :String,
        @Query("hash", encoded = false) key :String,
        @Query("limit", encoded = false) limit :String
    ) : Response<ComicsContainer>
}