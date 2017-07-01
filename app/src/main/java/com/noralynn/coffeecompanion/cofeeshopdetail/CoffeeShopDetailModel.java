package com.noralynn.coffeecompanion.cofeeshopdetail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.noralynn.coffeecompanion.coffeeshoplist.CoffeeShop;

class CoffeeShopDetailModel {

    @Nullable
    private CoffeeShop coffeeShop;

    CoffeeShopDetailModel() { }

    void setCoffeeShop(@NonNull CoffeeShop coffeeShop) {
        this.coffeeShop = coffeeShop;
    }

    @Nullable
    public CoffeeShop getCoffeeShop() {
        return coffeeShop;
    }
}
