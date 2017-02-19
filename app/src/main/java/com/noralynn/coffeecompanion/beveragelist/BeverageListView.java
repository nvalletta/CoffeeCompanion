package com.noralynn.coffeecompanion.beveragelist;

import android.content.Context;
import android.support.annotation.NonNull;

import com.noralynn.coffeecompanion.common.Beverage;

interface BeverageListView {

    void startCoffeeShopActivity();

    void startBeverageActivity(@NonNull Beverage beverage);

    void displayBeverages(@NonNull BeverageListModel beverageListModel);

    @NonNull
    Context getContext();

}
