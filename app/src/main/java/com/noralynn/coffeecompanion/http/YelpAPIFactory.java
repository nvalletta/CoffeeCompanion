package com.noralynn.coffeecompanion.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import retrofit2.Retrofit;

public class YelpAPIFactory {

    private static final String YELP_API_URL = "https://api.yelp.com/";

    /**
     * NOTE: If you'd like to see nearby coffee shops,
     * you should replace the below values with your own.
     * These values have been omitted for security purposes.
     */
    private static final String YELP_CONSUMER_KEY = "";
    private static final String YELP_CONSUMER_SECRET = "";
    private static final String YELP_TOKEN = "";
    private static final String YELP_TOKEN_SECRET = "";

    @Nullable
    private static YelpService sYelpServiceInstance;

    @NonNull
    public static YelpService getYelpServiceInstance() {
        if (null == sYelpServiceInstance) {
            sYelpServiceInstance = getYelpService();
        }
        return sYelpServiceInstance;
    }

    @NonNull
    private static YelpService getYelpService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(YELP_API_URL)
                .build();

        return retrofit.create(YelpService.class);
    }

}
