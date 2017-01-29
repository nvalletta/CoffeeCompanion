package com.noralynn.coffeecompanion.activity;

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

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.adapter.BeverageAdapter;
import com.noralynn.coffeecompanion.model.Beverage;
import com.noralynn.coffeecompanion.utils.DataUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @NonNull
    private static final String TAG = MainActivity.class.getSimpleName();

    @Nullable
    private ArrayList<Beverage> mBeverages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: Yelp API call to find nearby coffee shops
            }
        });

        checkBeveragesInfo();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelableArrayList("beverages", mBeverages);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void checkBeveragesInfo() {
        mBeverages = DataUtils.deserializeBeveragesFromJson(this);

        RecyclerView beverageRecycler = (RecyclerView) findViewById(R.id.recycler_coffee);
        if (null != beverageRecycler && null != mBeverages) {
            BeverageAdapter adapter = new BeverageAdapter(this, mBeverages);
            beverageRecycler.setLayoutManager(new LinearLayoutManager(this));
            beverageRecycler.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        Log.w(TAG, "Deserialized Successfully!");
    }

}
