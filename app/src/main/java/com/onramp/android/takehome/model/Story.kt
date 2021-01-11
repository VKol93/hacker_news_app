package com.onramp.android.takehome.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "StoriesList")
data class Story(
       // var by: String,
        //var descendants: Int,
        @PrimaryKey
        var id: Int,
        //var score: Int,
        var time: Long,
        var title: String,
        //var type: String,
        var url: String,
       // var isBookmarked: Boolean = false
)