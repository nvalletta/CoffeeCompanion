package com.noralynn.coffeecompanion.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.adapter.beverages.BeverageAdapter;
import com.noralynn.coffeecompanion.model.Beverage;
import com.noralynn.coffeecompanion.utils.DataUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @NonNull
    private static final String BEVERAGES_BUNDLE_KEY = "BEVERAGES_BUNDLE_KEY";

    @NonNull
    public static final String BEVERAGE_INTENT_KEY = "BEVERAGE_INTENT_KEY";

    @Nullable
    private ArrayList<Beverage> mBeverages;

    @NonNull
    private RecyclerView mBeverageRecyclerView;

    @NonNull
    private OnClickListener mBeverageClickListener = new OnClickListener() {
        @Override
        public void onClick(@NonNull View view) {
            startBeverageActivity(view);
        }
    };

    @NonNull
    private OnClickListener mFabClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            startCoffeeShopActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (null != savedInstanceState) {
            mBeverages = savedInstanceState.getParcelableArrayList(BEVERAGES_BUNDLE_KEY);
        }

        mBeverageRecyclerView = (RecyclerView) findViewById(R.id.beverages_recycler);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(mFabClickListener);

        checkBeveragesInfo();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelableArrayList(BEVERAGES_BUNDLE_KEY, mBeverages);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void checkBeveragesInfo() {
        // If we already have a list of beverages, don't do any more work.
        if (null != mBeverages) {
            return;
        }

        // Otherwise, let's get a new list of beverages from our raw resource.
        mBeverages = DataUtils.deserializeBeveragesFromJson(this);
        if (null != mBeverages) {
            BeverageAdapter adapter = new BeverageAdapter(this, mBeverages);
            adapter.setClickListener(mBeverageClickListener);
            mBeverageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mBeverageRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private void startBeverageActivity(@NonNull View view) {
        Log.d(TAG, "onClick() called with: view = [" + view + "]");
        if (null == mBeverages) {
            return;
        }

        int position = mBeverageRecyclerView.getChildLayoutPosition(view);
        if (position >= mBeverages.size()) {
            return;
        }

        Beverage beverage = mBeverages.get(position);
        if (null != beverage) {
            Intent beverageActivityIntent = new Intent(MainActivity.this, BeverageActivity.class);
            beverageActivityIntent.putExtra(BEVERAGE_INTENT_KEY, beverage);
            startActivity(beverageActivityIntent);
        }
    }

    private void startCoffeeShopActivity() {
        Intent coffeeShopActivityIntent = new Intent(this, CoffeeShopActivity.class);
        startActivity(coffeeShopActivityIntent);
    }


}
