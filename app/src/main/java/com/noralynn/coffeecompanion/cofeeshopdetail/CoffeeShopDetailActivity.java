package com.noralynn.coffeecompanion.cofeeshopdetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.coffeeshop.CoffeeShop;

public class CoffeeShopDetailActivity extends AppCompatActivity implements CoffeeShopDetailView {

    public static final String COFFEE_SHOP_BUNDLE_KEY = "coffeeShopExtra";

    @SuppressWarnings("NullableProblems")
    @NonNull
    private CoffeeShopDetailViewPresenter presenter;

    @Nullable
    private ImageView imageView;

    @Nullable
    private TextView phoneNumberTextView;

    @Nullable
    private TextView titleTextView;

    @Nullable
    private AppCompatButton mapButton;

    @Nullable
    private AppCompatButton websiteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_shop_detail);
        initializeViews();

        presenter = new CoffeeShopDetailViewPresenter(this);
        presenter.onCreate(getIntent());
    }

    private void initializeViews() {
        imageView = (ImageView) findViewById(R.id.image_coffee_shop);
        phoneNumberTextView = (TextView) findViewById(R.id.text_phone_number);
        titleTextView = (TextView) findViewById(R.id.text_title);

        mapButton = (AppCompatButton) findViewById(R.id.button_find);
        if (null != mapButton) {
            mapButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onClickMapButton();
                }
            });
        }

        websiteButton = (AppCompatButton) findViewById(R.id.button_website);
        if (null != websiteButton) {
            websiteButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onClickWebsiteButton();
                }
            });
        }
    }

    @Override
    public void displayCoffeeShop(@Nullable CoffeeShopDetailModel coffeeShopDetailModel) {
        if (null == coffeeShopDetailModel) {
            return;
        }

        CoffeeShop coffeeShop = coffeeShopDetailModel.getCoffeeShop();
        if (null == coffeeShop) {
            return;
        }

        String name = coffeeShop.getName();
        String phoneNumber = coffeeShop.getPhoneNumber();
        String imageUrl = coffeeShop.getImageUrl();

        if (null != name && null != titleTextView) {
            titleTextView.setText(name);
        }

        if (null != phoneNumber && null != phoneNumberTextView) {
            phoneNumberTextView.setText(phoneNumber);
        }

        if (null != imageUrl && null != imageView) {
            Glide.with(this).load(imageUrl).into(imageView);
        }
    }

    @Override
    public void sendMapsIntent(@NonNull CoffeeShopDetailModel coffeeShopDetailModel) {
        CoffeeShop coffeeShop = coffeeShopDetailModel.getCoffeeShop();
        if (null == coffeeShop) {
            return;
        }

        Uri locationUri = Uri.parse("geo:0,0?q=" + coffeeShop.getAddress());
        Intent mapsIntent = new Intent(Intent.ACTION_VIEW);
        mapsIntent.setData(locationUri);
        if (null != mapsIntent.resolveActivity(getPackageManager())) {
            startActivity(mapsIntent);
        }
    }

}
