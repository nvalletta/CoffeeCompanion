package com.noralynn.coffeecompanion.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.noralynn.coffeecompanion.R;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder> {

    class CoffeeViewHolder extends ViewHolder {

        private TextView mTextTitle;
        private ImageView mImageIcon;

        public CoffeeViewHolder(View itemView) {
            super(itemView);
            mTextTitle = (TextView) itemView.findViewById(R.id.text_title);
            mImageIcon = (ImageView) itemView.findViewById(R.id.image_icon);
        }

    }

    @NonNull
    private Context mContext;

    @NonNull
    private LayoutInflater mLayoutInflater;

    public CoffeeAdapter(@NonNull Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public CoffeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mLayoutInflater.inflate(R.layout.item_coffee, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(CoffeeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
