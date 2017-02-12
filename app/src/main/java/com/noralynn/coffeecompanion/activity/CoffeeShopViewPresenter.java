package com.noralynn.coffeecompanion.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.model.CoffeeShop;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private Callback<SearchResponse> mCoffeeShopSearchCallback = new Callback<SearchResponse>() {
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

    CoffeeShopViewPresenter(@NonNull CoffeeShopView coffeeShopView) {
        this.coffeeShopView = coffeeShopView;
        coffeeShopModel = new CoffeeShopModel();
    }

    void onCreate(Bundle savedInstanceState) {
        coffeeShopView.initializeViews();
        coffeeShopModel = getBundledCoffeeShopModel(savedInstanceState);
        showListOfNearbyCoffeeShops();
    }

    @NonNull
    private CoffeeShopModel getBundledCoffeeShopModel(@Nullable Bundle savedInstanceState) {
        CoffeeShopModel model = null;
        if (null != savedInstanceState) {
            model = coffeeShopView.getSavedCoffeeShopModel(savedInstanceState);
        }
        return model != null ? model : new CoffeeShopModel();
    }

    private void showListOfNearbyCoffeeShops() {
        List<CoffeeShop> coffeeShops = coffeeShopModel.getCoffeeShops();
        if (coffeeShops == null || coffeeShops.size() == 0) {
            sendYelpCoffeeShopSearchRequest();
        } else {
            coffeeShopView.setModel(coffeeShopModel);
            coffeeShopView.displayCoffeeShops(coffeeShops);
        }
    }

    private void sendYelpCoffeeShopSearchRequest() {
        YelpAPIFactory apiFactory = new YelpAPIFactory(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
        YelpAPI yelpAPI = apiFactory.createAPI();

        Map<String, String> params = new HashMap<>();
        params.put("category_filter", "coffee");
        params.put("sort", "1");
        Call<SearchResponse> call = yelpAPI.search("Saratoga Springs", params);

        call.enqueue(mCoffeeShopSearchCallback);
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
        coffeeShopView.setModel(coffeeShopModel);
        coffeeShopView.displayCoffeeShops(coffeeShops);
    }

}
