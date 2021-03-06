package com.noralynn.coffeecompanion.beveragelist;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.noralynn.coffeecompanion.common.Beverage;

import java.util.List;

public class BeverageListModel implements Parcelable {

    @Nullable
    private List<Beverage> mBeverages;

    public BeverageListModel() {
    }

    @Nullable
    List<Beverage> getBeverages() {
        return mBeverages;
    }

    void setBeverages(@Nullable List<Beverage> mBeverages) {
        this.mBeverages = mBeverages;
    }

    private BeverageListModel(Parcel in) {
        mBeverages = in.createTypedArrayList(Beverage.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mBeverages);
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
}
