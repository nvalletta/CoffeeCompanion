package com.noralynn.coffeecompanion.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.adapter.coffeeshops.CoffeeShopAdapter;
import com.noralynn.coffeecompanion.model.CoffeeShop;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.noralynn.coffeecompanion.http.ApiConstants.CONSUMER_KEY;
import static com.noralynn.coffeecompanion.http.ApiConstants.CONSUMER_SECRET;
import static com.noralynn.coffeecompanion.http.ApiConstants.TOKEN;
import static com.noralynn.coffeecompanion.http.ApiConstants.TOKEN_SECRET;

public class CoffeeShopActivity extends AppCompatActivity {

    private static final String TAG = CoffeeShopActivity.class.getSimpleName();

    @NonNull
    private static final String COFFEE_SHOPS_INTENT_KEY = "COFFEE_SHOPS_INTENT_KEY";

    @NonNull
    private static final String COFFEE_SHOPS_BUNDLE_KEY = "COFFEE_SHOPS_BUNDLE_KEY";

    @NonNull
    private Callback<SearchResponse> mCoffeeShopSearchCallback = new Callback<SearchResponse>() {
        @Override
        public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
            SearchResponse searchResponse = response.body();
            if (searchResponse.businesses() == null || searchResponse.businesses().size() == 0) {
                displayCoffeeShops();
                return;
            }
            Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "]");

            mCoffeeShops = new ArrayList<>(searchResponse.businesses().size());
            for (Business business : searchResponse.businesses()) {
                CoffeeShop coffeeShop = new CoffeeShop(business);
                mCoffeeShops.add(coffeeShop);
            }

            displayCoffeeShops();
        }

        @Override
        public void onFailure(Call<SearchResponse> call, Throwable t) {
            Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            // HTTP error happened, do something to handle it.
        }
    };

    @Nullable
    private ArrayList<CoffeeShop> mCoffeeShops;

    @NonNull
    private RecyclerView mCoffeeShopRecyclerView;

    @NonNull
    private ProgressBar mProgressBar;

    @NonNull
    private TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_shops);

        if (null != savedInstanceState) {
            mCoffeeShops = savedInstanceState.getParcelableArrayList(COFFEE_SHOPS_INTENT_KEY);
        }

        mCoffeeShopRecyclerView = (RecyclerView) findViewById(R.id.coffee_shops_recycler);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mEmptyTextView = (TextView) findViewById(R.id.empty_text);

        getListOfNearbyCoffeeShops();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(COFFEE_SHOPS_BUNDLE_KEY, mCoffeeShops);
        super.onSaveInstanceState(outState);
    }

    private void getListOfNearbyCoffeeShops() {
        if (null == mCoffeeShops) {
            sendYelpCoffeeShopSearchRequest();
        } else {
            displayCoffeeShops();
        }
    }

    private void sendYelpCoffeeShopSearchRequest() {
        YelpAPIFactory apiFactory = new YelpAPIFactory(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
        YelpAPI yelpAPI = apiFactory.createAPI();

        Map<String, String> params = new HashMap<>();
        params.put("category_filter", "coffee");
        params.put("sort", "1");
        Call<SearchResponse> call = yelpAPI.search("Cedar City", params);

        call.enqueue(mCoffeeShopSearchCallback);
    }

    private void displayCoffeeShops() {
        mProgressBar.setVisibility(View.GONE);
        if (null == mCoffeeShops || mCoffeeShops.size() == 0) {
            mCoffeeShopRecyclerView.setVisibility(View.GONE);
            mEmptyTextView.setVisibility(View.VISIBLE);
            return;
        }

        CoffeeShopAdapter coffeeShopAdapter = new CoffeeShopAdapter(this, mCoffeeShops);
        mCoffeeShopRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCoffeeShopRecyclerView.setAdapter(coffeeShopAdapter);
        coffeeShopAdapter.notifyDataSetChanged();
    }

}
