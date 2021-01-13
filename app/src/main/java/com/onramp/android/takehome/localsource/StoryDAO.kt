package com.onramp.android.takehome.localsource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.onramp.android.takehome.model.Story

@Dao
interface StoryDAO{
    @Query("SELECT *FROM StoriesList")
    suspend fun getBookmarked(): List<Story>
    @Insert
    suspend fun insertStory(story: Story)
    @Delete
    suspend  fun deleteStory(story: Story)
}