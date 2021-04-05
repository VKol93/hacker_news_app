package com.onramp.android.takehome.datasource

import android.util.Log
import com.onramp.android.takehome.model.Story
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiHackerNews {
    @GET("topstories.json")
    suspend fun getTopStoriesID(): List<Int>

    @GET("item/{id}.json")
    suspend fun getStory(@Path("id") id: Int): Story
}

object RemoteDataSource {
    private val BASE_URL = "https://hacker-news.firebaseio.com/v0/"
    private val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    private val retrofitService: ApiHackerNews = retrofit.create(ApiHackerNews::class.java)

    suspend fun getTopStories(): List<Story> {
        val storyList: MutableList<Story> = mutableListOf()
        val topStoriesIDs: List<Int> = retrofitService.getTopStoriesID()
        val topTwentyStories = topStoriesIDs.take(20)
        for (id in topTwentyStories) {
            val story = retrofitService.getStory(id)
            storyList.add(story)
        }
        Log.d("_TAG", storyList.toString())
        return storyList
    }
}
