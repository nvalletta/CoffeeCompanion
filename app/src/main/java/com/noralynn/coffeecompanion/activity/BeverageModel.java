package com.noralynn.coffeecompanion.activity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.noralynn.coffeecompanion.model.Beverage;

class BeverageModel implements Parcelable {

    @Nullable
    private Beverage beverage;

    BeverageModel() { }

    @Nullable
    public Beverage getBeverage() {
        return beverage;
    }

    public void setBeverage(@Nullable Beverage beverage) {
        this.beverage = beverage;
    }

    //////////////////////////////////////
    //    Parcelable Implementation     //
    //////////////////////////////////////

    private BeverageModel(Parcel in) {
        beverage = in.readParcelable(Beverage.class.getClassLoader());
    }

    public static final Creator<BeverageModel> CREATOR = new Creator<BeverageModel>() {
        @Override
        public BeverageModel createFromParcel(Parcel in) {
            return new BeverageModel(in);
        }

        @Override
        public BeverageModel[] newArray(int size) {
            return new BeverageModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(beverage, flags);
    }
}
