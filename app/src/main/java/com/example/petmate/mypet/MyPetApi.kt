package com.example.petmate.mypet

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://api.simplifiedcoding.in/course-apis/recyclerview/"

interface MyPetApi {

    @GET("movies")
    fun getMyPets() : Call<List<Pet>>

    companion object {
        operator fun invoke() : MyPetApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyPetApi::class.java)
        }
    }
}