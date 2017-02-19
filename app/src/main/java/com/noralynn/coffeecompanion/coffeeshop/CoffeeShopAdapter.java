package com.noralynn.coffeecompanion.coffeeshop;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noralynn.coffeecompanion.R;

import java.util.ArrayList;
import java.util.List;

public class CoffeeShopAdapter extends RecyclerView.Adapter<CoffeeShopViewHolder> {

    @NonNull
    private CoffeeShopViewPresenter presenter;

    @Nullable
    private List<CoffeeShop> coffeeShops = new ArrayList<>();

    public CoffeeShopAdapter(@NonNull CoffeeShopViewPresenter presenter, @Nullable List<CoffeeShop> coffeeShops) {
        this.presenter = presenter;
        this.coffeeShops = coffeeShops;
    }

    @Override
    public CoffeeShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_coffee_shop, parent, false);
        return new CoffeeShopViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CoffeeShopViewHolder holder, int position) {
        CoffeeShop coffeeShop = coffeeShops.get(position);
        if (null != holder) {
            holder.bind(coffeeShop);
        }
    }

    @Override
    public int getItemCount() {
        if (null == coffeeShops) {
            return 0;
        }
        return coffeeShops.size();
    }

}
