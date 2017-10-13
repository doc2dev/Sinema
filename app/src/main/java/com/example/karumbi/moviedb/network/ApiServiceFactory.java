package com.example.karumbi.moviedb.network;

import com.example.karumbi.moviedb.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

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
        return new OkHttpClient.Builder()
                .addInterceptor(getQueryParamsInterceptor())
                .addInterceptor(getLoggingInterceptor())
                .build();
    }

    private Interceptor getQueryParamsInterceptor() {
        return chain -> {
            Request original = chain.request();
            HttpUrl url = original.url();
            HttpUrl newUrl = url.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.MOVIE_API_KEY)
                    .addQueryParameter("language", "en-US")
                    .addQueryParameter("include_adult", "false")
                    .build();
            Request.Builder builder = original.newBuilder().url(newUrl);
            Request request = builder.build();
            return chain.proceed(request);
        };
    }

    private Interceptor getLoggingInterceptor() {
        HttpLoggingInterceptor interceptor =
                new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    public ApiService getApiService() {

        GsonConverterFactory factory = GsonConverterFactory.create(gson);

        return new Retrofit.Builder()
                .client(getHttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(factory)
                .build()
                .create(ApiService.class);
    }
}
