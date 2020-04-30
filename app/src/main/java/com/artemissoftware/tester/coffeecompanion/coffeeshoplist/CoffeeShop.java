package com.artemissoftware.tester.coffeecompanion.coffeeshoplist;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//import com.yelp.clientlib.entities.Business;
//import com.yelp.clientlib.entities.Location;

import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.Location;

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

    @Nullable
    private String imageUrl;

    public CoffeeShop(Business business) {

        rating = business.getRating() == 0.0d ? 0.0d : business.getRating();
        distance = business.getDistance() == 0.0d ? 0.0d : convertFromMetersToMiles(business.getDistance());
        isClosed = business.getIsClosed() == true ? true : business.getIsClosed();
        name = business.getName();
        websiteUrl = business.getUrl();
        phoneNumber = business.getDisplayPhone();

        Location location = business.getLocation();
        if (null != location && null != location.getAddress1()) {
            address = Uri.encode(location.getAddress1() + ", " + location.getCity() + ", " + location.getState());
        }

    }

    private CoffeeShop(double rating, double distance, @Nullable String name, boolean isClosed, @Nullable String websiteUrl, @Nullable String phoneNumber, @Nullable String address, String imageUrl) {
        this.rating = rating;
        this.distance = distance;
        this.name = name;
        this.isClosed = isClosed;
        this.websiteUrl = websiteUrl;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.imageUrl = imageUrl;
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

    @Nullable
    public String getImageUrl() {
        return imageUrl;
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
        dest.writeString(websiteUrl);
        dest.writeString(phoneNumber);
        dest.writeString(address);
        dest.writeString(imageUrl);
    }

    private CoffeeShop(@NonNull Parcel parcel) {
        rating = parcel.readDouble();
        distance = parcel.readDouble();
        name = parcel.readString();
        isClosed = parcel.readByte() == 1;
        websiteUrl = parcel.readString();
        phoneNumber = parcel.readString();
        address = parcel.readString();
        imageUrl = parcel.readString();
    }

    public static final Creator<CoffeeShop> CREATOR
            = new Creator<CoffeeShop>() {
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
                Uri.encode("1600 Amphitheatre Parkway, CA"),
                "http://www.cats-r-us.com/fakeimage.png");
    }

}
