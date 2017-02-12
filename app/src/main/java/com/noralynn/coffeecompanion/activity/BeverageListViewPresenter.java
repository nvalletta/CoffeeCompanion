package com.noralynn.coffeecompanion.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.noralynn.coffeecompanion.model.Beverage;
import com.noralynn.coffeecompanion.utils.DataUtils;

import java.util.List;

class BeverageListViewPresenter {

    @NonNull
    private BeverageListModel beverageListModel;

    @NonNull
    private BeverageListView beverageListView;

    BeverageListViewPresenter(@NonNull BeverageListView beverageListView) {
        this.beverageListView = beverageListView;
        beverageListModel = new BeverageListModel();
    }

    @SuppressWarnings("ConstantConditions")
    void onCreate(@Nullable Bundle savedInstanceState) {
        beverageListView.initializeViews();
        beverageListModel = getBundledBeverageListModel(savedInstanceState);
        checkBeveragesInfo();
    }

    void onClickBeverage(@NonNull View view) {
        Log.d("BeverageListView", "onClick() called with: view = [" + view + "]");
        List<Beverage> beverages = beverageListModel.getBeverages();
        if (null == beverages) {
            return;
        }

        int position = beverageListView.getRecyclerPositionForView(view);
        if (position >= beverages.size()) {
            return;
        }

        Beverage beverage = beverages.get(position);
        if (null != beverage) {
            beverageListView.startBeverageActivity(beverage);
        }
    }

    void onClickFloatingActionButton() {
        beverageListView.startCoffeeShopActivity();
    }

    @NonNull
    private BeverageListModel getBundledBeverageListModel(@Nullable Bundle savedInstanceState) {
        BeverageListModel model = null;
        if (null != savedInstanceState) {
            model = beverageListView.getSavedInstanceState(savedInstanceState);
        }
        return model != null ? model : new BeverageListModel();
    }

    private void checkBeveragesInfo() {
        // If we already have a list of beverages, don't do any more work.
        List<Beverage> beverages = beverageListModel.getBeverages();
        if (null != beverages) {
            return;
        }

        // Otherwise, let's get a new list of beverages from our raw resource.
        beverages = DataUtils.deserializeBeveragesFromJson(beverageListView.getContext());
        if (null != beverages) {
            beverageListModel.setBeverages(beverages);
            beverageListView.loadModel(beverageListModel);
        }
    }

}
