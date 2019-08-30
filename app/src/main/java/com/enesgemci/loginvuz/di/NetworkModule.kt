package com.enesgemci.loginvuz.di

import android.app.Application
import com.enesgemci.loginvuz.R
import com.enesgemci.loginvuz.data.network.RestApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(context: Application): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor(
                    object : HttpLoggingInterceptor.Logger {
                        override fun log(message: String) {
                            Timber.e(message)
                        }
                    }
                ).apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, context: Application): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideRestApi(retrofit: Retrofit): RestApi {
        return retrofit.create(RestApi::class.java)
    }
}