package com.noralynn.coffeecompanion.coffeeshop;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.noralynn.coffeecompanion.R;

import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static com.noralynn.coffeecompanion.beveragelist.BeverageListActivity.BEVERAGE_LIST_MODEL_BUNDLE_KEY;

public class CoffeeShopActivity extends AppCompatActivity implements CoffeeShopView {

    @NonNull
    public static final String COFFEE_SHOPS_BUNDLE_KEY = "COFFEE_SHOPS_BUNDLE_KEY";

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private CoffeeShopViewPresenter presenter;

    @Nullable
    private RecyclerView coffeeShopRecyclerView;

    @Nullable
    private ProgressBar progressBar;

    @Nullable
    private TextView emptyTextView;

    @NonNull
    private String[] locationPermissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_shops);
        initializeViews();

        CoffeeShopModel model = null;
        if (null != savedInstanceState) {
            model = savedInstanceState.getParcelable(BEVERAGE_LIST_MODEL_BUNDLE_KEY);
        }
        if (null == model) {
            model = new CoffeeShopModel(locationPermissionHasBeenGranted());
        }

        presenter = new CoffeeShopViewPresenter(this, model);
        presenter.onCreate();
    }

    public void initializeViews() {
        coffeeShopRecyclerView = (RecyclerView) findViewById(R.id.coffee_shops_recycler);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        emptyTextView = (TextView) findViewById(R.id.empty_text);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(COFFEE_SHOPS_BUNDLE_KEY, presenter.getCoffeeShopModel());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void displayCoffeeShops(@NonNull CoffeeShopModel model) {
        hideProgressBar();
        hideEmptyTextView();

        List<CoffeeShop> coffeeShops = model.getCoffeeShops();
        CoffeeShopAdapter coffeeShopAdapter = new CoffeeShopAdapter(presenter, coffeeShops);
        if (null != coffeeShopRecyclerView) {
            coffeeShopRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            coffeeShopRecyclerView.setAdapter(coffeeShopAdapter);
        }
    }

    @Override
    public void displayPermissionRequest() {
        requestPermission(LOCATION_PERMISSION_REQUEST_CODE, locationPermissions);
    }

    private boolean locationPermissionHasBeenGranted() {
        int permission = ContextCompat.checkSelfPermission(
                this,
                ACCESS_COARSE_LOCATION
        );

        return permission == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showPermissionError() {
        showMessage(R.string.error_cannot_get_device_location);
    }

    @NonNull
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void shareCoffeeShop(@NonNull String message) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_title)));
    }

    @Override
    public void requestPermission(int requestCode, @NonNull String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE || permissions.length == 0) {
            return;
        }

        boolean canAccessLocation = canAccessLocation(permissions, grantResults);
        if (canAccessLocation) {
            presenter.onPermissionGranted();
        } else {
            presenter.onRequestPermissionFailed();
        }
    }

    private boolean canAccessLocation(@NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length != 0) {
            String permission = permissions[0];
            if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (grantResults.length != 0) {
                    int grantResult = grantResults[0];
                    return grantResult == PackageManager.PERMISSION_GRANTED;
                }
            }
        }
        return false;
    }

    @Override
    public void showMessage(@StringRes int message) {
        hideRecyclerView();
        hideProgressBar();
        showEmptyTextView(message);
    }

    private void hideProgressBar() {
        if (null != progressBar) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void hideRecyclerView() {
        if (null != coffeeShopRecyclerView) {
            coffeeShopRecyclerView.setVisibility(View.GONE);
        }
    }

    private void hideEmptyTextView() {
        if (null != emptyTextView) {
            emptyTextView.setVisibility(View.GONE);
        }
    }

    private void showEmptyTextView(@StringRes int message) {
        if (null != emptyTextView) {
            emptyTextView.setVisibility(View.VISIBLE);
            emptyTextView.setText(getString(message));
        }
    }

}
