package com.onramp.android.takehome.localsource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.onramp.android.takehome.model.Story

@Database(entities = [Story::class],version = 1)
abstract class StoryDB : RoomDatabase(){
    abstract fun storiesDAO(): StoryDAO
}