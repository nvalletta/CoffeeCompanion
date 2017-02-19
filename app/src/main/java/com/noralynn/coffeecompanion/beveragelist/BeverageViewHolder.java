package com.noralynn.coffeecompanion.beveragelist;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.common.Beverage;
import com.noralynn.coffeecompanion.common.DrawableUtils;


class BeverageViewHolder extends ViewHolder {

    @NonNull
    private TextView mTitleTextView;

    @NonNull
    private ImageView mIconImageView;

    BeverageViewHolder(@NonNull View itemView) {
        super(itemView);
        mTitleTextView = (TextView) itemView.findViewById(R.id.text_title);
        mIconImageView = (ImageView) itemView.findViewById(R.id.image_icon);
    }

    void bind(@NonNull Beverage beverage) {
        mTitleTextView.setText(beverage.getName());
        Drawable icon = DrawableUtils.getDrawableByName(itemView.getContext(), beverage.getDrawableResourceName());
        mIconImageView.setImageDrawable(icon);
    }

}
