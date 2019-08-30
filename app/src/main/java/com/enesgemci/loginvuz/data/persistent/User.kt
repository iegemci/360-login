package com.enesgemci.loginvuz.data.persistent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    var firstName: String = "",
    var lastName: String = ""
) {

    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0
}