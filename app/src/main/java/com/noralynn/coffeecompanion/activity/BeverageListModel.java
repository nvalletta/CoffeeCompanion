package com.noralynn.coffeecompanion.activity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.noralynn.coffeecompanion.model.Beverage;

import java.util.List;

class BeverageListModel implements Parcelable {

    @Nullable
    private List<Beverage> mBeverages;

    BeverageListModel() {
    }

    @Nullable
    public List<Beverage> getBeverages() {
        return mBeverages;
    }

    public void setBeverages(@Nullable List<Beverage> mBeverages) {
        this.mBeverages = mBeverages;
    }


    //////////////////////////////////////
    //    Parcelable Implementation     //
    //////////////////////////////////////

    private BeverageListModel(Parcel in) {
        mBeverages = in.createTypedArrayList(Beverage.CREATOR);
    }

    public static final Creator<BeverageListModel> CREATOR = new Creator<BeverageListModel>() {
        @Override
        public BeverageListModel createFromParcel(Parcel in) {
            return new BeverageListModel(in);
        }

        @Override
        public BeverageListModel[] newArray(int size) {
            return new BeverageListModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mBeverages);
    }
}
