package com.noralynn.coffeecompanion.model;

import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;

public class Beverage {

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private String drawableResourceName;

    public Beverage(@NonNull String name, @NonNull String description, @NonNull String drawableResourceName) {
        this.name = name;
        this.description = description;
        this.drawableResourceName = drawableResourceName;
    }

}
