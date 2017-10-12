package com.example.karumbi.moviedb.network;

/**
 * Created by Eston on 12/10/2017.
 */

public interface ResultCallback<T> {
    void onResult(T result);
    void onError(String message);
}
