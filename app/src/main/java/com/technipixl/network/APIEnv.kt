package com.technipixl.network

import com.technipixl.exo1.HashGenerator
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit

val timeStamp = Date().time
val privateKey = "9a763e591033c0f83bae7c440a06e78a295aaed7"
val publicKey = "29a23afed49d04c9581916a8bfa498e8"

val trueKey = HashGenerator.generateHash(timeStamp, privateKey, publicKey)

class MarvelServiceImpl {
    fun getRetrofit() : Retrofit {
        val okBuilder = OkHttpClient().newBuilder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            callTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
        }
        return Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/v1/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okBuilder.build())
            .build()
    }

    suspend fun getCharacters() :Response<CharactersContainer> = getRetrofit().create(CharactersService::class.java).getCharacters(
        publicKey, timeStamp.toString(), trueKey ?: "", limit = "100")
    suspend fun getComics(id :String) :Response<ComicsContainer> = getRetrofit().create(ComicsService::class.java).getComics(
        id,
        publicKey, timeStamp.toString(), trueKey ?: "", limit = "100")
    suspend fun getDetails(id :String) :Response<DetailContainer> = getRetrofit().create(DetailService::class.java).getDetails(
        id,
        publicKey, timeStamp.toString(), trueKey ?: "", limit = "100")
}

