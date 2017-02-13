package com.noralynn.coffeecompanion.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.noralynn.coffeecompanion.model.CoffeeShop;

import java.util.List;

interface CoffeeShopView {

    void initializeViews();

    void requestPermission(int requestCode, @NonNull String[] permissions);

    void showMessage(@StringRes int message);

    void setModel(@NonNull CoffeeShopModel coffeeShopModel);

    void displayCoffeeShops(@NonNull List<CoffeeShop> coffeeShopModel);

    @NonNull
    Context getContext();

    @Nullable
    CoffeeShopModel getSavedCoffeeShopModel(@NonNull Bundle savedInstanceState);
}
