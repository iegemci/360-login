package com.enesgemci.loginvuz.data.persistent

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {

    suspend fun saveUser(user: User) = withContext(Dispatchers.IO) {
        userDao.insertUser(user)
    }

    suspend fun deleteUsers() = withContext(Dispatchers.IO) {
        userDao.deleteUsers()
    }

    fun getUser() = userDao.getUser()
}