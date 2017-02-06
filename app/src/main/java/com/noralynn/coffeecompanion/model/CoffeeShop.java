package com.noralynn.coffeecompanion.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yelp.clientlib.entities.Business;

public class CoffeeShop implements Parcelable {

    private double rating;

    private double distance;

    @Nullable
    private String name;

    private boolean isClosed;

    public CoffeeShop(Business business) {
        rating = business.rating() == null ? 0.0d : business.rating();
        distance = business.distance() == null ? 0.0d : business.distance();
        name = business.name();
        isClosed = business.isClosed() == null ? true : business.isClosed();
    }

    public double getRating() {
        return rating;
    }

    public double getDistance() {
        return distance;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public static final Parcelable.Creator<CoffeeShop> CREATOR
            = new Parcelable.Creator<CoffeeShop>() {
        public CoffeeShop createFromParcel(Parcel in) {
            return new CoffeeShop(in);
        }

        public CoffeeShop[] newArray(int size) {
            return new CoffeeShop[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(rating);
        dest.writeDouble(distance);
        dest.writeString(name);
        dest.writeByte((byte) (isClosed ? 1 : 0));
    }

    private CoffeeShop(@NonNull Parcel parcel) {
        rating = parcel.readDouble();
        distance = parcel.readDouble();
        name = parcel.readString();
        isClosed = parcel.readByte() == 1;
    }
}
