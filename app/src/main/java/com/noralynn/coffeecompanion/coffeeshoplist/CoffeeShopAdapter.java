package com.noralynn.coffeecompanion.coffeeshoplist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.noralynn.coffeecompanion.R;

import java.util.ArrayList;
import java.util.List;

class CoffeeShopAdapter extends RecyclerView.Adapter<CoffeeShopViewHolder> {

    @NonNull
    private CoffeeShopListViewPresenter presenter;

    @Nullable
    private List<CoffeeShop> coffeeShops = new ArrayList<>();

    CoffeeShopAdapter(@NonNull CoffeeShopListViewPresenter presenter, @Nullable List<CoffeeShop> coffeeShops) {
        this.presenter = presenter;
        this.coffeeShops = coffeeShops;
    }

    @Override
    public CoffeeShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_coffee_shop, parent, false);
        final CoffeeShopViewHolder viewHolder = new CoffeeShopViewHolder(itemView);
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != coffeeShops) {
                    presenter.onClickCoffeeShop(coffeeShops.get(viewHolder.getAdapterPosition()));
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CoffeeShopViewHolder holder, int position) {
        if (null == coffeeShops) {
            return;
        }
        CoffeeShop coffeeShop = coffeeShops.get(position);
        holder.bind(coffeeShop);
    }

    @Override
    public int getItemCount() {
        if (null == coffeeShops) {
            return 0;
        }
        return coffeeShops.size();
    }

}
