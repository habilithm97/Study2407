package com.example.paging

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(val name: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}