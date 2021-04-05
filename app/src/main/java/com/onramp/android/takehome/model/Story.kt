package com.onramp.android.takehome.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "StoriesList")
data class Story(
        @PrimaryKey
        var id: Int,
        var time: Long,
        var title: String,
        var url: String,
        var isBookmarked: Boolean = false
)