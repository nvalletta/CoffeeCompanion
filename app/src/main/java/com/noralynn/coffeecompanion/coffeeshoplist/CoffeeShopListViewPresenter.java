package com.noralynn.coffeecompanion.coffeeshoplist;

import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.coffeeshoplist.idlingresource.CoffeeShopsIdlingResource;
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
import static com.noralynn.coffeecompanion.coffeeshoplist.CoffeeShopListActivity.COFFEE_SHOPS_BUNDLE_KEY;
import static com.noralynn.coffeecompanion.http.ApiConstants.CONSUMER_KEY;
import static com.noralynn.coffeecompanion.http.ApiConstants.CONSUMER_SECRET;
import static com.noralynn.coffeecompanion.http.ApiConstants.TOKEN;
import static com.noralynn.coffeecompanion.http.ApiConstants.TOKEN_SECRET;


class CoffeeShopListViewPresenter {

    @NonNull
    private CoffeeShopListView coffeeShopListView;

    @Nullable
    private CoffeeShopListModel coffeeShopListModel;

    @Nullable
    private CoffeeShopsIdlingResource coffeeShopsIdlingResource;

    @NonNull
    private Callback<SearchResponse> coffeeShopSearchCallback = new Callback<SearchResponse>() {
        @Override
        public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
            SearchResponse searchResponse = response.body();
            Log.d("CoffeeShop", "onSearchCompleted() called with: response = [" + response + "]");
            List<Business> businesses = searchResponse.businesses();
            handleSearchResponse(businesses);

            // FOR OUR TESTS:
            notifyIdlingResource();
        }

        @Override
        public void onFailure(Call<SearchResponse> call, Throwable t) {
            Log.e("CoffeeShop", "onFailure()", t);
            coffeeShopListView.showMessage(R.string.error_unable_to_load_coffee_shops);

            // FOR OUR TESTS:
            notifyIdlingResource();
        }
    };

    CoffeeShopListViewPresenter(@NonNull CoffeeShopListView coffeeShopListView) {
        this.coffeeShopListView = coffeeShopListView;
    }

    void onCreate(@Nullable Bundle savedInstanceState, boolean locationPermissionHasBeenGranted) {
        coffeeShopListModel = getCoffeeShopListModelFromBundle(savedInstanceState, locationPermissionHasBeenGranted);

        if (coffeeShopListModel.getCoffeeShops() != null) {
            // We already have our coffee shops; just return.
            coffeeShopListView.displayCoffeeShops(coffeeShopListModel);
            return;
        }

        if (!coffeeShopListModel.hasLocationPermission()) {
            coffeeShopListView.displayPermissionRequest();
        } else {
            onPermissionGranted();
        }
    }

    private CoffeeShopListModel getCoffeeShopListModelFromBundle(@Nullable Bundle savedInstanceState, boolean locationPermissionHasBeenGranted) {
        CoffeeShopListModel model = null;
        if (null != savedInstanceState) {
            model = savedInstanceState.getParcelable(COFFEE_SHOPS_BUNDLE_KEY);
        }
        return model != null ? model : new CoffeeShopListModel(locationPermissionHasBeenGranted);
    }

    @NonNull
    @VisibleForTesting
    CoffeeShopsIdlingResource getCoffeeShopsIdlingResource() {
        if (null == coffeeShopsIdlingResource) {
            coffeeShopsIdlingResource = new CoffeeShopsIdlingResource();
            if (coffeeShopListModel.getCoffeeShops() != null && !coffeeShopListModel.getCoffeeShops().isEmpty()) {
                coffeeShopsIdlingResource.onSearchCompleted();
            }
        }
        return coffeeShopsIdlingResource;
    }

    //FOR OUR TESTS:
    private void notifyIdlingResource() {
        if (null != coffeeShopsIdlingResource) {
            coffeeShopsIdlingResource.onSearchCompleted();
        }
    }

    private void sendYelpCoffeeShopSearchRequest() {
        // First, let's see if we can get our coordinates. If not, we'll have to listen
        // for changes in device Location, because the device may not have registered
        // a Location update yet.
        CoordinateOptions coordinateOptions = getCoordinateOptions();
        if (null == coordinateOptions) {
            listenForLocationUpdates();
            return;
        }

        YelpAPIFactory apiFactory = new YelpAPIFactory(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
        YelpAPI yelpAPI = apiFactory.createAPI();

        Map<String, String> params = new HashMap<>();
        params.put("category_filter", "coffee");
        params.put("sort", "1");

        Call<SearchResponse> call = yelpAPI.search(coordinateOptions, params);

        call.enqueue(coffeeShopSearchCallback);
    }

    private void handleSearchResponse(@Nullable List<Business> businesses) {
        if (businesses == null || businesses.size() == 0) {
            coffeeShopListView.showMessage(R.string.no_nearby_coffee_shops);
            return;
        }

        List<CoffeeShop> coffeeShops = new ArrayList<>(businesses.size());
        for (Business business : businesses) {
            CoffeeShop coffeeShop = new CoffeeShop(business);
            coffeeShops.add(coffeeShop);
        }

        coffeeShopListModel.setCoffeeShops(coffeeShops);
        coffeeShopListView.displayCoffeeShops(coffeeShopListModel);
        if (coffeeShops.size() > 0) {
            coffeeShopListView.showShareButton();
        } else {
            coffeeShopListView.hideShareButton();
        }
    }

    @Nullable
    private CoordinateOptions getCoordinateOptions() {
        Location location = getCurrentLocation();
        if (null == location) {
            listenForLocationUpdates();
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
        Context context = coffeeShopListView.getContext();
        // Make sure we really do have permission before trying to access location!
        if (ActivityCompat.checkSelfPermission(context, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("CoffeeShopListActivity", "We don't have permission to get the current device location...");
            return null;
        }

        // Get the user's current location
        LocationManager manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        String locationProvider = getLocationProvider(manager);
        if (null == locationProvider) {
            return null;
        }

        return manager.getLastKnownLocation(locationProvider);
    }

    private String getLocationProvider(@NonNull LocationManager manager) {
        String locationProvider = null;
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        }
        return locationProvider;
    }

    private void listenForLocationUpdates() {
        Context context = coffeeShopListView.getContext();
        final LocationManager manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("CoffeeShopListActivity", "We don't have permission to listen to the current device location...");
            return;
        }

        manager.requestLocationUpdates(getLocationProvider(manager), 60000, 10, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                manager.removeUpdates(this);
                sendYelpCoffeeShopSearchRequest();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    void onShareClicked() {
        List<CoffeeShop> coffeeShops = coffeeShopListModel.getCoffeeShops();
        if (null != coffeeShops && coffeeShops.size() > 0) {
            coffeeShopListView.sendShareIntent(coffeeShopListModel.getCoffeeShops().get(0));
        }
    }

    void onPermissionGranted() {
        coffeeShopListModel.setHasLocationPermission(true);
        sendYelpCoffeeShopSearchRequest();
    }

    void onRequestPermissionFailed() {
        coffeeShopListModel.setHasLocationPermission(false);
        coffeeShopListView.showPermissionError();
    }

    @NonNull
    CoffeeShopListModel getCoffeeShopListModel() {
        return coffeeShopListModel;
    }

    public void onClickCoffeeShop(@Nullable CoffeeShop coffeeShop) {
        if (null == coffeeShop) {
            return;
        }

        coffeeShopListView.openCoffeeShopDetailsActivity(coffeeShop);
    }
}
