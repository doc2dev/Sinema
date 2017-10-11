package com.example.karumbi.moviedb.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Karumbi on 11/10/2017.
 */

public class ApiServiceFactory {

  private String baseUrl;
  private Gson gson;

  public ApiServiceFactory(String baseUrl) {
    this.baseUrl = baseUrl;
    gson = new GsonBuilder()
        .create();
  }

  private OkHttpClient getHttpClient() {
    Interceptor apiKeyInterceptor = new Interceptor() {
      @Override
      public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl url = original.url();
        HttpUrl newUrl = url.newBuilder()
            .addQueryParameter("api_key", "1275f053fa98ca18f6823ce44d1b2901")
            .addQueryParameter("language", "en-US")
            .build();
        Request.Builder builder = original.newBuilder().url(newUrl);
        Request request = builder.build();
        return chain.proceed(request);
      }
    };
    return new OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .build();
  }

  public ApiService getApiService() {

    GsonConverterFactory factory = GsonConverterFactory.create(gson);

    return new Retrofit.Builder()
        .client(getHttpClient())
        .baseUrl(baseUrl)
        .addConverterFactory(factory)
        //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build()
        .create(ApiService.class);
  }
}
