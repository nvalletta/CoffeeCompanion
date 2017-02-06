package com.noralynn.coffeecompanion.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.model.Beverage;
import com.noralynn.coffeecompanion.utils.ResourceUtils;


class BeverageViewHolder extends ViewHolder {

    @NonNull
    private TextView mTextTitle;

    @NonNull
    private ImageView mImageIcon;

    BeverageViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextTitle = (TextView) itemView.findViewById(R.id.text_title);
        mImageIcon = (ImageView) itemView.findViewById(R.id.image_icon);
    }

    void bind(@NonNull Context context, @NonNull Beverage beverage) {
        mTextTitle.setText(beverage.getName());
        Drawable icon = ResourceUtils.getDrawableByName(context, beverage.getDrawableResourceName());
        mImageIcon.setImageDrawable(icon);
    }

}
