package com.example.petmate.postfeed

import com.google.gson.annotations.SerializedName

class PostFeed(
    val id: String,
    //val id: Int,
    val image: String,
    @SerializedName("is_new")
    val isNew: String,
    val rating: String,
    @SerializedName("like_percent")
    val likePercent: String,
    @SerializedName("vote_count")
    val voteCount: String,
    val title:String,
    val language:String,
    val type: String
)