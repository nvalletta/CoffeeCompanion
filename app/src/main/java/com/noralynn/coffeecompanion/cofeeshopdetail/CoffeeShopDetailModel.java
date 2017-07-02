package com.noralynn.coffeecompanion.cofeeshopdetail;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.noralynn.coffeecompanion.coffeeshoplist.CoffeeShop;

class CoffeeShopDetailModel implements Parcelable {

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

    private CoffeeShopDetailModel(Parcel in) {
        coffeeShop = in.readParcelable(CoffeeShop.class.getClassLoader());
    }

    public static final Creator<CoffeeShopDetailModel> CREATOR = new Creator<CoffeeShopDetailModel>() {
        @Override
        public CoffeeShopDetailModel createFromParcel(Parcel in) {
            return new CoffeeShopDetailModel(in);
        }

        @Override
        public CoffeeShopDetailModel[] newArray(int size) {
            return new CoffeeShopDetailModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(coffeeShop, flags);
    }

}
