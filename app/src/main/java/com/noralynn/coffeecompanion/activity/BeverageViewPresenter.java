package com.noralynn.coffeecompanion.activity;

import android.support.annotation.NonNull;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.model.Beverage;

class BeverageViewPresenter {

    @NonNull
    private BeverageView beverageView;

    @NonNull
    private BeverageModel beverageModel;

    BeverageViewPresenter(@NonNull BeverageView beverageView) {
        this.beverageView = beverageView;
        beverageModel = new BeverageModel();
    }

    void onCreate() {
        beverageView.initializeViews();
        Beverage beverage = beverageView.getBeverageFromBundle();
        beverageModel.setBeverage(beverage);
        if (null == beverage) {
            beverageView.showErrorText(R.string.error_unable_to_load_beverage);
        } else {
            beverageView.setTitle(beverage.getName());
            beverageView.setDescription(beverage.getDescription());
            beverageView.setImage(beverage.getDrawableResourceName());
        }
    }

}
