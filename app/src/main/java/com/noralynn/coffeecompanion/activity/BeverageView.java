package com.noralynn.coffeecompanion.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.noralynn.coffeecompanion.model.Beverage;

interface BeverageView {

    void initializeViews();

    void showErrorText(@StringRes int message);

    void setTitle(@NonNull String name);

    void setDescription(@NonNull String description);

    void setImage(@NonNull String drawableResourceName);

    @Nullable
    Beverage getBeverageFromBundle();
}
