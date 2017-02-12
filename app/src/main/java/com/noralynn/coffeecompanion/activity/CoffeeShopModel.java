package com.noralynn.coffeecompanion.activity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.noralynn.coffeecompanion.model.CoffeeShop;

import java.util.List;

class CoffeeShopModel implements Parcelable {

    @Nullable
    List<CoffeeShop> coffeeShops;

    CoffeeShopModel() { }

    @Nullable
    List<CoffeeShop> getCoffeeShops() {
        return coffeeShops;
    }

    void setCoffeeShops(@Nullable List<CoffeeShop> coffeeShops) {
        this.coffeeShops = coffeeShops;
    }


    //////////////////////////////////////
    //    Parcelable Implementation     //
    //////////////////////////////////////

    private CoffeeShopModel(Parcel in) {
        coffeeShops = in.createTypedArrayList(CoffeeShop.CREATOR);
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
    }
}
