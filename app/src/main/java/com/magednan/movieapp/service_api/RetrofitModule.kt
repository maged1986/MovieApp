package com.magednan.movieapp.service_api

import com.magednan.movieapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    //to inject retrofit object in app
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .callTimeout(200L,TimeUnit.SECONDS)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()}

        return retrofit
    }

    //to inject retrofit api in app
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): MovieApi {
        val api by lazy {
            retrofit.create(MovieApi::class.java)
        }
        return api
    }
}