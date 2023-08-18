package com.technipixl.network

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

data class CharactersContainer (
    @SerializedName("data")
    val data: CharactersResults
)
data class CharactersResults(
    @SerializedName("results")
    val results :MutableList<Character>
)

data class Character(
    @SerializedName("id")
    val id :Int? = null,
    @SerializedName("name")
    val name :String? = null,
    @SerializedName("thumbnail")
    val thumbnail :Thumbnail? = null
)

data class Thumbnail(
    @SerializedName("path")
    val path :String? = null,
    @SerializedName("extension")
    val extension :String? = null
)

interface CharactersService{
    @Headers("Content-type: application/json")
    @GET("characters")
    suspend fun getCharacters(
        @Query("apikey", encoded = false) pubKey :String,
        @Query("ts", encoded = false) timestamp :String,
        @Query("hash", encoded = false) key :String,
        @Query("limit", encoded = false) limit :String
    ) :Response<CharactersContainer>
}