package com.noralynn.coffeecompanion.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.adapter.BeverageAdapter.BeverageViewHolder;
import com.noralynn.coffeecompanion.model.Beverage;
import com.noralynn.coffeecompanion.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

public class BeverageAdapter extends RecyclerView.Adapter<BeverageViewHolder> {

    class BeverageViewHolder extends ViewHolder {

        private TextView mTextTitle;
        private ImageView mImageIcon;

        public BeverageViewHolder(View itemView) {
            super(itemView);
            mTextTitle = (TextView) itemView.findViewById(R.id.text_title);
            mImageIcon = (ImageView) itemView.findViewById(R.id.image_icon);
        }

    }

    @NonNull
    private Context mContext;

    @NonNull
    private LayoutInflater mLayoutInflater;

    @NonNull
    private List<Beverage> mBeverages = new ArrayList<>();

    public BeverageAdapter(@NonNull Context context, @NonNull List<Beverage> beverages) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mBeverages = beverages;
    }

    @Override
    public BeverageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_beverage, parent, false);
        return new BeverageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BeverageViewHolder holder, int position) {
        Beverage beverage = mBeverages.get(position);
        if (null != holder) {
            holder.mTextTitle.setText(beverage.getName());
            Drawable icon = ResourceUtils.getDrawableByName(mContext, beverage.getDrawableResourceName());
            holder.mImageIcon.setImageDrawable(icon);
        }
    }

    @Override
    public int getItemCount() {
        return mBeverages.size();
    }

}
