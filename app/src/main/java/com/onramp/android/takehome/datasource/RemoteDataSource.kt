package com.onramp.android.takehome.datasource

import com.onramp.android.takehome.model.Story
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiHackerNews {
    @GET("topstories.json")
    suspend fun getTopStoriesID(): List<Int>
    @GET("item/{id}.json")
    suspend fun getStory(@Path("id") id:Int): Story

    @GET("beststories.json")
    suspend fun getTopStories(): List<Story>
//    {
//        val result = mutableListOf<Story>()
//        val top = getTopStoriesID()
//        for (id in top){
//            val story = getStory(id)
//            result.add(story)
//        }
//        return result
//    }

}

object RemoteDataSource {
    private val BASE_URL = "https://hacker-news.firebaseio.com/v0/"
    private val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    val retrofitService: ApiHackerNews = retrofit.create(ApiHackerNews::class.java)
}
