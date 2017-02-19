package com.noralynn.coffeecompanion.coffeeshop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

interface CoffeeShopView {

    void showPermissionError();

    void displayPermissionRequest();

    void showMessage(@StringRes int message);

    void displayCoffeeShops(@NonNull CoffeeShopModel coffeeShopModel);

    void requestPermission(int requestCode, @NonNull String[] permissions);

    @NonNull
    Context getContext();
}
