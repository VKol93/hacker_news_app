package com.onramp.android.takehome.localsource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.onramp.android.takehome.model.Story

@Dao
interface StoryDAO{
    @Query("SELECT * FROM StoriesList")
    fun getStories(): List<Story>

    @Query("SELECT *FROM StoriesList")
    fun getBookmarked(): List<Story>

    @Insert
    fun insertStory(story: Story)
    @Delete
    fun deleteStory(story: Story)
}