package com.noralynn.coffeecompanion.cofeeshopdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.noralynn.coffeecompanion.coffeeshoplist.CoffeeShop;

import static com.noralynn.coffeecompanion.cofeeshopdetail.CoffeeShopDetailActivity.COFFEE_SHOP_BUNDLE_KEY;

class CoffeeShopDetailViewPresenter {

    @NonNull
    private final CoffeeShopDetailView coffeeShopDetailView;

    @NonNull
    private final CoffeeShopDetailModel coffeeShopDetailModel;

    CoffeeShopDetailViewPresenter(@NonNull CoffeeShopDetailView coffeeShopDetailView) {
        this.coffeeShopDetailView = coffeeShopDetailView;
        coffeeShopDetailModel = new CoffeeShopDetailModel();
    }

    void onClickMapButton() {
        coffeeShopDetailView.sendMapsIntent(coffeeShopDetailModel);
    }

    void onCreate(@Nullable Intent intent) {
        if (null != intent && null != intent.getExtras()) {
            CoffeeShop coffeeShop = getCoffeeShopFromBundle(intent.getExtras());
            coffeeShopDetailModel.setCoffeeShop(coffeeShop);
            coffeeShopDetailView.displayCoffeeShop(coffeeShopDetailModel);
        }
    }

    void onClickWebsiteButton() {
        //todo: open webview
    }

    private CoffeeShop getCoffeeShopFromBundle(@NonNull Bundle extras) {
        return extras.getParcelable(COFFEE_SHOP_BUNDLE_KEY);
    }
}
