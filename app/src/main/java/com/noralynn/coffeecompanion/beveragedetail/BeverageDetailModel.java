package com.noralynn.coffeecompanion.beveragedetail;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.noralynn.coffeecompanion.common.Beverage;

class BeverageDetailModel implements Parcelable {

    @Nullable
    private Beverage beverage;

    BeverageDetailModel() { }

    @Nullable
    public Beverage getBeverage() {
        return beverage;
    }

    public void setBeverage(@Nullable Beverage beverage) {
        this.beverage = beverage;
    }

    private BeverageDetailModel(Parcel in) {
        beverage = in.readParcelable(Beverage.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(beverage, flags);
    }

    public static final Creator<BeverageDetailModel> CREATOR = new Creator<BeverageDetailModel>() {
        @Override
        public BeverageDetailModel createFromParcel(Parcel in) {
            return new BeverageDetailModel(in);
        }

        @Override
        public BeverageDetailModel[] newArray(int size) {
            return new BeverageDetailModel[size];
        }
    };

}
