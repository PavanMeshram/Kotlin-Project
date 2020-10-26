package com.example.petmate.postfeed

import com.example.petmate.mypet.Pet
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://api.simplifiedcoding.in/course-apis/recyclerview/"

interface PostFeedApi {

    @GET("movies")
    fun getMyPost() : Call<List<PostFeed>>

    companion object {
        operator fun invoke() : PostFeedApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PostFeedApi::class.java)
        }
    }
}