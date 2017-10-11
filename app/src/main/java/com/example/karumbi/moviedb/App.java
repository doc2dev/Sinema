package com.example.karumbi.moviedb;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Karumbi on 11/10/2017.
 */

public class App extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }
}
