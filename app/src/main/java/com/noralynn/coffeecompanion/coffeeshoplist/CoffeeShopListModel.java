package com.noralynn.coffeecompanion.coffeeshoplist;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.List;

class CoffeeShopListModel implements Parcelable {

    @Nullable
    private List<CoffeeShop> coffeeShops;

    private boolean hasLocationPermission;

    CoffeeShopListModel(boolean hasLocationPermission) {
        this.hasLocationPermission = hasLocationPermission;
    }

    @Nullable
    List<CoffeeShop> getCoffeeShops() {
        return coffeeShops;
    }

    void setCoffeeShops(@Nullable List<CoffeeShop> coffeeShops) {
        this.coffeeShops = coffeeShops;
    }

    public boolean hasLocationPermission() {
        return hasLocationPermission;
    }

    public void setHasLocationPermission(boolean hasLocationPermission) {
        this.hasLocationPermission = hasLocationPermission;
    }

    CoffeeShopListModel(Parcel in) {
        coffeeShops = in.createTypedArrayList(CoffeeShop.CREATOR);
        hasLocationPermission = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(coffeeShops);
        dest.writeByte((byte)(hasLocationPermission ? 1 : 0));
    }

    public static final Creator<CoffeeShopListModel> CREATOR = new Creator<CoffeeShopListModel>() {
        @Override
        public CoffeeShopListModel createFromParcel(Parcel in) {
            return new CoffeeShopListModel(in);
        }

        @Override
        public CoffeeShopListModel[] newArray(int size) {
            return new CoffeeShopListModel[size];
        }
    };
}
