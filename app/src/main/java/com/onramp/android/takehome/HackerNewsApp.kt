package com.onramp.android.takehome

import android.app.Application
import androidx.room.Room
import com.onramp.android.takehome.localsource.StoriesDB

class HackerNewsApp: Application() {
    lateinit var db: StoriesDB

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(applicationContext, StoriesDB::class.java, "db").build()
    }
}