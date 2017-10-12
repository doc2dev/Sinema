package com.example.karumbi.moviedb.util;

/**
 * Created by Karumbi on 11/10/2017.
 */

public class Utils {

    public static String getBackdropUrl(String imgUrl) {
        return "https://image.tmdb.org/t/p/" +
                "w780" +
                imgUrl;
    }

    public static String getPosterUrl(String imgUrl) {
        return "https://image.tmdb.org/t/p/" +
                "w185" +
                imgUrl;
    }

    public static String truncate(String input) {
        int amt = 100;
        if (input.length() < amt) {
            return input;
        }
        return input.substring(0, amt) + "...";
    }
}
