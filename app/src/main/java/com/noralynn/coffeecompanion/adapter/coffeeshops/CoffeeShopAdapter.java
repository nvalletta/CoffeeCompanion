package com.noralynn.coffeecompanion.adapter.coffeeshops;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.model.CoffeeShop;

import java.util.ArrayList;
import java.util.List;

public class CoffeeShopAdapter extends RecyclerView.Adapter<CoffeeShopViewHolder> {

    @NonNull
    private Context mContext;

    @NonNull
    private LayoutInflater mLayoutInflater;

    @NonNull
    private List<CoffeeShop> mCoffeeShops = new ArrayList<>();

    public CoffeeShopAdapter(@NonNull Context context, @NonNull List<CoffeeShop> coffeeShops) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mCoffeeShops = coffeeShops;
    }

    @Override
    public CoffeeShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_coffee_shop, parent, false);
        return new CoffeeShopViewHolder(mContext, itemView);
    }

    @Override
    public void onBindViewHolder(CoffeeShopViewHolder holder, int position) {
        CoffeeShop coffeeShop = mCoffeeShops.get(position);
        if (null != holder) {
            holder.bind(coffeeShop);
        }
    }

    @Override
    public int getItemCount() {
        return mCoffeeShops.size();
    }

}
