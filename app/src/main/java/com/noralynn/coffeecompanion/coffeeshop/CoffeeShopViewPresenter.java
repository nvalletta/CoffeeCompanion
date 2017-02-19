package com.noralynn.coffeecompanion.coffeeshop;

import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.noralynn.coffeecompanion.R;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;
import static com.noralynn.coffeecompanion.coffeeshop.CoffeeShopActivity.COFFEE_SHOPS_BUNDLE_KEY;
import static com.noralynn.coffeecompanion.http.ApiConstants.CONSUMER_KEY;
import static com.noralynn.coffeecompanion.http.ApiConstants.CONSUMER_SECRET;
import static com.noralynn.coffeecompanion.http.ApiConstants.TOKEN;
import static com.noralynn.coffeecompanion.http.ApiConstants.TOKEN_SECRET;


class CoffeeShopViewPresenter {

    @NonNull
    private CoffeeShopView coffeeShopView;

    @NonNull
    private CoffeeShopModel coffeeShopModel;

    @NonNull
    private Callback<SearchResponse> coffeeShopSearchCallback = new Callback<SearchResponse>() {
        @Override
        public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
            SearchResponse searchResponse = response.body();
            Log.d("CoffeeShop", "onResponse() called with: response = [" + response + "]");
            List<Business> businesses = searchResponse.businesses();
            handleSearchResponse(businesses);
        }

        @Override
        public void onFailure(Call<SearchResponse> call, Throwable t) {
            Log.d("CoffeeShop", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            coffeeShopView.showMessage(R.string.error_unable_to_load_coffee_shops);
        }
    };

    CoffeeShopViewPresenter(@NonNull CoffeeShopView coffeeShopView, boolean hasLocationPermission) {
        this.coffeeShopView = coffeeShopView;
        coffeeShopModel = new CoffeeShopModel();
        coffeeShopModel.setHasLocationPermission(hasLocationPermission);
    }

    void onCreate(Bundle savedInstanceState) {
        coffeeShopModel = getBundledCoffeeShopModel(savedInstanceState);

        if (coffeeShopModel.hasLocationPermission()) {
            coffeeShopView.displayCoffeeShops(coffeeShopModel);
        } else {
            coffeeShopView.displayPermissionRequest();
        }
    }

    @NonNull
    private CoffeeShopModel getBundledCoffeeShopModel(@Nullable Bundle savedInstanceState) {
        CoffeeShopModel model = null;
        if (null != savedInstanceState) {
            model = savedInstanceState.getParcelable(COFFEE_SHOPS_BUNDLE_KEY);
        }
        return model != null ? model : new CoffeeShopModel();
    }

    private void sendYelpCoffeeShopSearchRequest() {
        YelpAPIFactory apiFactory = new YelpAPIFactory(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
        YelpAPI yelpAPI = apiFactory.createAPI();

        Map<String, String> params = new HashMap<>();
        params.put("category_filter", "coffee");
        params.put("sort", "1");

        Call<SearchResponse> call = yelpAPI.search(getCoordinateOptions(), params);

        call.enqueue(coffeeShopSearchCallback);
    }

    private void handleSearchResponse(@Nullable List<Business> businesses) {
        if (businesses == null || businesses.size() == 0) {
            coffeeShopView.showMessage(R.string.no_nearby_coffee_shops);
            return;
        }

        List<CoffeeShop> coffeeShops = new ArrayList<>(businesses.size());
        for (Business business : businesses) {
            CoffeeShop coffeeShop = new CoffeeShop(business);
            coffeeShops.add(coffeeShop);
        }

        coffeeShopModel.setCoffeeShops(coffeeShops);
        coffeeShopView.displayCoffeeShops(coffeeShopModel);
    }

    @Nullable
    private CoordinateOptions getCoordinateOptions() {
        Location location = getCurrentLocation();
        if (null == location) {
            return null;
        }

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        double altitude = location.getAltitude();

        return CoordinateOptions.builder()
                .latitude(latitude)
                .longitude(longitude)
                .altitude(altitude)
                .build();
    }

    @Nullable
    private Location getCurrentLocation() {
        Context context = coffeeShopView.getContext();
        // Sanity check. Make sure we really do have permission before trying to access location!
        if (ActivityCompat.checkSelfPermission(context, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        // Get the user's current location
        LocationManager manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        String locationProvider = null;
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        }

        if (null == locationProvider) {
            return null;
        }

        return manager.getLastKnownLocation(locationProvider);
    }

    void onPermissionGranted() {
        coffeeShopModel.setHasLocationPermission(true);
        sendYelpCoffeeShopSearchRequest();
    }

    void onRequestPermissionFailed() {
        coffeeShopModel.setHasLocationPermission(false);
        coffeeShopView.showPermissionError();
    }
}
