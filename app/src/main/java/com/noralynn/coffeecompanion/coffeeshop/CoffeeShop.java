package com.noralynn.coffeecompanion.coffeeshop;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.Location;

import java.util.Locale;

public class CoffeeShop implements Parcelable {

    private double rating;

    private double distance;

    private boolean isClosed;

    @Nullable
    private String name;

    @Nullable
    private String websiteUrl;

    @Nullable
    private String phoneNumber;

    @Nullable
    private String address;

    public CoffeeShop(Business business) {
        rating = business.rating() == null ? 0.0d : business.rating();
        distance = business.distance() == null ? 0.0d : convertFromMetersToMiles(business.distance());
        isClosed = business.isClosed() == null ? true : business.isClosed();
        name = business.name();
        websiteUrl = business.url();
        phoneNumber = business.displayPhone();

        Location location = business.location();
        if (null != location && null != location.address() && location.address().size() > 0) {
            address = Uri.encode(location.address().get(0) + ", " + location.city() + ", " + location.stateCode());
        }
    }

    private CoffeeShop(double rating, double distance, @Nullable String name, boolean isClosed, @Nullable String websiteUrl, @Nullable String phoneNumber, @Nullable String address) {
        this.rating = rating;
        this.distance = distance;
        this.name = name;
        this.isClosed = isClosed;
        this.websiteUrl = websiteUrl;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    private double convertFromMetersToMiles(double distance) {
        return distance * 0.000621371192;
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

    @Nullable
    public String getWebsiteUrl() {
        return websiteUrl;
    }

    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

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

    public static final Parcelable.Creator<CoffeeShop> CREATOR
            = new Parcelable.Creator<CoffeeShop>() {
        public CoffeeShop createFromParcel(Parcel in) {
            return new CoffeeShop(in);
        }

        public CoffeeShop[] newArray(int size) {
            return new CoffeeShop[size];
        }
    };

    @NonNull
    public String getHumanReadableDistance() {
        return String.format(Locale.getDefault(), "%1$,.2f mi", distance);
    }

    public static CoffeeShop fake(int number) {
        return new CoffeeShop(
                (double) number,
                (double) number,
                "Coffee Shop " + number,
                false,
                "http://www.cats-r-us.com/" + number,
                "1-800-" + number,
                Uri.encode("1600 Amphitheatre Parkway, CA")
        );
    }

}
