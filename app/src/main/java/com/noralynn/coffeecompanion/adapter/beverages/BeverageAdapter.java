package com.noralynn.coffeecompanion.adapter.beverages;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.model.Beverage;

import java.util.ArrayList;
import java.util.List;

public class BeverageAdapter extends RecyclerView.Adapter<BeverageViewHolder> {

    @NonNull
    private Context mContext;

    @NonNull
    private LayoutInflater mLayoutInflater;

    @NonNull
    private List<Beverage> mBeverages = new ArrayList<>();

    @Nullable
    private OnClickListener mOnClickListener;

    public BeverageAdapter(@NonNull Context context, @NonNull List<Beverage> beverages) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mBeverages = beverages;
    }

    public void setClickListener(@NonNull OnClickListener beverageClickListener) {
        mOnClickListener = beverageClickListener;
    }

    @Override
    public BeverageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_beverage, parent, false);
        itemView.setOnClickListener(mOnClickListener);
        return new BeverageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BeverageViewHolder holder, int position) {
        Beverage beverage = mBeverages.get(position);
        if (null != holder) {
            holder.bind(mContext, beverage);
        }
    }

    @Override
    public int getItemCount() {
        return mBeverages.size();
    }

}
