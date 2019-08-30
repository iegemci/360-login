package com.enesgemci.loginvuz.di

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.Room
import com.enesgemci.loginvuz.data.persistent.AppDatabase
import com.enesgemci.loginvuz.data.persistent.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun provideDatabase(@NonNull application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "User.db"
        ).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.getUserDao()
    }
}