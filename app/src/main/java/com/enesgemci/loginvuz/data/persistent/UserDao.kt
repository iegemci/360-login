package com.enesgemci.loginvuz.data.persistent

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomWarnings

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User)

    @Query("DELETE FROM user")
    fun deleteUsers()

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM user ORDER BY userId DESC LIMIT 1")
    fun getUser(): LiveData<User>
}