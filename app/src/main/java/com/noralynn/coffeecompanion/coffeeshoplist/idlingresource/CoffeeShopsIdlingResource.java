package com.noralynn.coffeecompanion.coffeeshoplist.idlingresource;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

public class CoffeeShopsIdlingResource implements IdlingResource {

    @Nullable
    private ResourceCallback resourceCallback;

    private boolean isIdle = false;

    @Override
    public String getName() {
        return CoffeeShopsIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(@Nullable ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    public void onSearchCompleted() {
        isIdle = true;
        notifyResourceCallback();
    }

    private void notifyResourceCallback() {
        if (resourceCallback != null && isIdle) {
            resourceCallback.onTransitionToIdle();
        }
    }
}
