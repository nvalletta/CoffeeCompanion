package com.noralynn.coffeecompanion.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Beverage implements Parcelable {

    @NonNull
    private final String name;

    @NonNull
    private final String description;

    @NonNull
    private final String drawableResourceName;

    public Beverage(@NonNull String name, @NonNull String description, @NonNull String drawableResourceName) {
        this.name = name;
        this.description = description;
        this.drawableResourceName = drawableResourceName;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public String getDrawableResourceName() {
        return drawableResourceName;
    }

    public static final Parcelable.Creator<Beverage> CREATOR
            = new Parcelable.Creator<Beverage>() {
        public Beverage createFromParcel(Parcel in) {
            return new Beverage(in);
        }

        public Beverage[] newArray(int size) {
            return new Beverage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(drawableResourceName);
    }

    private Beverage(@NonNull Parcel parcel) {
        name = parcel.readString();
        description = parcel.readString();
        drawableResourceName = parcel.readString();
    }

}
