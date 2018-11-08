package com.example.karumbi.moviedb.network

import com.example.karumbi.moviedb.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

/**
 * Created by Karumbi on 11/10/2017.
 */

class ApiServiceFactory(private val baseUrl: String) {
  private val gson: Gson = GsonBuilder()
      .create()

  private val httpClient: OkHttpClient
    get() = OkHttpClient.Builder()
        .addInterceptor(queryParamsInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

  private val queryParamsInterceptor: Interceptor
    get() = Interceptor { chain ->
      val original = chain.request()
      val url = original.url()
      val newUrl = url.newBuilder()
          .addQueryParameter("api_key", BuildConfig.MOVIE_API_KEY)
          .addQueryParameter("language", "en-US")
          .addQueryParameter("include_adult", "false")
          .build()
      val builder = original.newBuilder().url(newUrl)
      val request = builder.build()
      chain.proceed(request)
    }

  private val loggingInterceptor: Interceptor
    get() {
      val interceptor = HttpLoggingInterceptor { message -> Timber.tag("OkHttp").d(message) }
      interceptor.level = HttpLoggingInterceptor.Level.BODY
      return interceptor
    }

  val apiService: ApiService
    get() {

      val factory = GsonConverterFactory.create(gson)

      return Retrofit.Builder()
          .client(httpClient)
          .baseUrl(baseUrl)
          .addConverterFactory(factory)
          .build()
          .create(ApiService::class.java)
    }

}
