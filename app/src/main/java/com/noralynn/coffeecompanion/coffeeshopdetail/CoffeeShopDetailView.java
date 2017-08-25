package com.noralynn.coffeecompanion.coffeeshopdetail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface CoffeeShopDetailView {

    void displayCoffeeShop(@Nullable CoffeeShopDetailModel coffeeShopDetailModel);

    void sendMapsIntent(@NonNull CoffeeShopDetailModel coffeeShopDetailModel);

}
