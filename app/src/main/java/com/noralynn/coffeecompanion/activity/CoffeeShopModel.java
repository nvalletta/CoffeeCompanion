package com.noralynn.coffeecompanion.activity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.noralynn.coffeecompanion.model.CoffeeShop;

import java.util.List;

class CoffeeShopModel implements Parcelable {

    @Nullable
    private List<CoffeeShop> coffeeShops;

    private boolean hasLocationPermission;

    CoffeeShopModel() { }

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

    //////////////////////////////////////
    //    Parcelable Implementation     //
    //////////////////////////////////////

    private CoffeeShopModel(Parcel in) {
        coffeeShops = in.createTypedArrayList(CoffeeShop.CREATOR);
        hasLocationPermission = in.readByte() != 0;
    }

    public static final Creator<CoffeeShopModel> CREATOR = new Creator<CoffeeShopModel>() {
        @Override
        public CoffeeShopModel createFromParcel(Parcel in) {
            return new CoffeeShopModel(in);
        }

        @Override
        public CoffeeShopModel[] newArray(int size) {
            return new CoffeeShopModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(coffeeShops);
        dest.writeByte((byte)(hasLocationPermission ? 1 : 0));
    }
}
