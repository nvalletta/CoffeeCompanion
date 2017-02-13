package com.noralynn.coffeecompanion.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.adapter.coffeeshops.CoffeeShopAdapter;
import com.noralynn.coffeecompanion.model.CoffeeShop;

import java.util.List;

public class CoffeeShopActivity extends AppCompatActivity implements CoffeeShopView {

    @NonNull
    private static final String COFFEE_SHOPS_BUNDLE_KEY = "COFFEE_SHOPS_BUNDLE_KEY";

    @Nullable
    private CoffeeShopModel coffeeShopModel;

    @NonNull
    private CoffeeShopViewPresenter coffeeShopViewPresenter;

    @NonNull
    private RecyclerView coffeeShopRecyclerView;

    @NonNull
    private ProgressBar progressBar;

    @NonNull
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_shops);
        coffeeShopViewPresenter = new CoffeeShopViewPresenter(this);
        coffeeShopViewPresenter.onCreate(savedInstanceState);
    }

    @Override
    public void initializeViews() {
        coffeeShopRecyclerView = (RecyclerView) findViewById(R.id.coffee_shops_recycler);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        emptyTextView = (TextView) findViewById(R.id.empty_text);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(COFFEE_SHOPS_BUNDLE_KEY, coffeeShopModel);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void displayCoffeeShops(@NonNull List<CoffeeShop> coffeeShops) {
        progressBar.setVisibility(View.GONE);
        emptyTextView.setVisibility(View.GONE);

        CoffeeShopAdapter coffeeShopAdapter = new CoffeeShopAdapter(this, coffeeShops);
        coffeeShopRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        coffeeShopRecyclerView.setAdapter(coffeeShopAdapter);
    }

    @NonNull
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void requestPermission(int requestCode, @NonNull String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        coffeeShopViewPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void showMessage(@StringRes int message) {
        coffeeShopRecyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        emptyTextView.setVisibility(View.VISIBLE);
        emptyTextView.setText(getString(message));
    }

    @Override
    public void setModel(@NonNull CoffeeShopModel coffeeShopModel) {
        this.coffeeShopModel = coffeeShopModel;
    }

    @Nullable
    @Override
    public CoffeeShopModel getSavedCoffeeShopModel(@NonNull Bundle savedInstanceState) {
        return savedInstanceState.getParcelable(COFFEE_SHOPS_BUNDLE_KEY);
    }

}
