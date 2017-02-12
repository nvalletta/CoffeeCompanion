package com.noralynn.coffeecompanion.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.noralynn.coffeecompanion.model.Beverage;

import java.util.List;

interface BeverageListView {

    void initializeViews();

    void startCoffeeShopActivity();

    int getRecyclerPositionForView(@NonNull View view);

    void startBeverageActivity(@NonNull Beverage beverage);

    void displayBeverages(@NonNull List<Beverage> beverages);

    void setModel(@NonNull BeverageListModel beverageListModel);

    @Nullable
    BeverageListModel getSavedBeverageListModel(@NonNull Bundle savedInstanceState);

    @NonNull
    Context getContext();
}
