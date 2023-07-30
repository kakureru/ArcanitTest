package com.example.arcanittest.app.di

import com.example.arcanittest.BuildConfig
import com.example.arcanittest.data.network.service.ReposService
import com.example.arcanittest.data.network.service.UsersService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.github.com/"

val networkModule = module {

    single<Retrofit> {
        provideRetrofit(okHttpClient = get())
    }

    single<OkHttpClient> {
        provideHttpClient(httpLoggingInterceptor = get())
    }

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single<UsersService> {
        provideUsersService(retrofit = get())
    }

    single<ReposService> {
        provideReposService(retrofit = get())
    }
}

private fun provideUsersService(retrofit: Retrofit) = retrofit.create(UsersService::class.java)

private fun provideReposService(retrofit: Retrofit) = retrofit.create(ReposService::class.java)

private fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit
    .Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) = OkHttpClient
    .Builder()
    .addInterceptor { chain ->
        val requestBuilder = chain
            .request()
            .newBuilder()
        requestBuilder.addHeader("Authorization", "Bearer ${BuildConfig.API_TOKEN}")
        return@addInterceptor chain.proceed(requestBuilder.build())
    }
    .addInterceptor(httpLoggingInterceptor)
    .build()