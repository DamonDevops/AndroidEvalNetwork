package com.technipixl.network

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

data class DetailContainer(
    @SerializedName("data")
    val data :DetailResults
)

data class DetailResults(
    @SerializedName("results")
    val results :MutableList<DetailDatas>
)

data class DetailDatas(
    @SerializedName("title")
    val title :String? = null,
    @SerializedName("description")
    val description :String? = null,
    @SerializedName("images")
    val image :MutableList<Thumbnail>
)

interface DetailService{
    @Headers("Content-type: application/json")
    @GET("comics/{id}")
    suspend fun getDetails(
        @Path("id") id :String,
        @Query("apikey", encoded = false) pubKey :String,
        @Query("ts", encoded = false) timestamp :String,
        @Query("hash", encoded = false) key :String,
        @Query("limit", encoded = false) limit :String
    ) : Response<DetailContainer>
}