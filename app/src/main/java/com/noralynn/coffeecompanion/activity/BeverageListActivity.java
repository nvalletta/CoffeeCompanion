package com.noralynn.coffeecompanion.activity;

import android.content.Context;
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
import android.view.View;
import android.view.View.OnClickListener;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.adapter.beverages.BeverageAdapter;
import com.noralynn.coffeecompanion.model.Beverage;

import java.util.List;

public class BeverageListActivity extends AppCompatActivity implements BeverageListView {

    @NonNull
    private static final String BEVERAGE_LIST_MODEL_BUNDLE_KEY = "BEVERAGE_LIST_MODEL_BUNDLE_KEY";

    @NonNull
    private BeverageListModel beverageListModel = new BeverageListModel();

    @NonNull
    @SuppressWarnings("NullableProblems")
    private BeverageListViewPresenter presenter;

    @Nullable
    private RecyclerView beverageRecyclerView;

    @NonNull
    private OnClickListener beverageClickListener = new OnClickListener() {
        @Override
        public void onClick(@NonNull View view) {
            presenter.onClickBeverage(view);
        }
    };

    @NonNull
    private OnClickListener fabClickListener = new OnClickListener() {
        @Override
        public void onClick(@NonNull View view) {
            presenter.onClickFloatingActionButton();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new BeverageListViewPresenter(this);
        presenter.onCreate(savedInstanceState);
    }

    @Override
    public void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        beverageRecyclerView = (RecyclerView) findViewById(R.id.beverages_recycler);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(fabClickListener);
    }

    @Override
    public int getRecyclerPositionForView(@NonNull View view) {
        if (null == beverageRecyclerView) {
            return -1;
        }
        return beverageRecyclerView.getChildAdapterPosition(view);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelable(BEVERAGE_LIST_MODEL_BUNDLE_KEY, beverageListModel);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void startBeverageActivity(@NonNull Beverage beverage) {
        Intent beverageActivityIntent = new Intent(BeverageListActivity.this, BeverageActivity.class);
        beverageActivityIntent.putExtra(BeverageActivity.BEVERAGE_INTENT_KEY, beverage);
        startActivity(beverageActivityIntent);
    }

    @Override
    public void startCoffeeShopActivity() {
        Intent coffeeShopActivityIntent = new Intent(this, CoffeeShopActivity.class);
        startActivity(coffeeShopActivityIntent);
    }

    @Override
    public BeverageListModel getSavedBeverageListModel(@NonNull Bundle savedInstanceState) {
        return savedInstanceState.getParcelable(BEVERAGE_LIST_MODEL_BUNDLE_KEY);
    }

    @NonNull
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setModel(@NonNull BeverageListModel beverageListModel) {
        this.beverageListModel = beverageListModel;
    }

    @Override
    public void displayBeverages(@NonNull List<Beverage> beverages) {
        if (null != beverageRecyclerView) {
            BeverageAdapter adapter = new BeverageAdapter(this, beverages);
            adapter.setClickListener(beverageClickListener);
            beverageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            beverageRecyclerView.setAdapter(adapter);
        }
    }
}
