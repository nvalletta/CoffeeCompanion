package com.noralynn.coffeecompanion.adapter.coffeeshops;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.model.CoffeeShop;

import java.util.Locale;


class CoffeeShopViewHolder extends ViewHolder {

    @NonNull
    private TextView mNameTextView;

    @NonNull
    private TextView mDistanceTextView;

    @NonNull
    private RatingBar mRatingBar;

    @NonNull
    private TextView mIsClosedTextView;

    @NonNull
    private Context mContext;

    CoffeeShopViewHolder(@NonNull Context context, @NonNull View itemView) {
        super(itemView);
        mContext = context;
        mNameTextView = (TextView) itemView.findViewById(R.id.name_text);
        mDistanceTextView = (TextView) itemView.findViewById(R.id.distance_text);
        mRatingBar = (RatingBar) itemView.findViewById(R.id.rating);
        mIsClosedTextView = (TextView) itemView.findViewById(R.id.is_closed_text);
    }

    void bind(@NonNull CoffeeShop coffeeShop) {
        mNameTextView.setText(coffeeShop.getName());
        mDistanceTextView.setText(String.format(Locale.getDefault(), "%1$,.2f mi", coffeeShop.getDistance()));
        mRatingBar.setRating((float)coffeeShop.getRating());
        mIsClosedTextView.setText(coffeeShop.isClosed() ? mContext.getString(R.string.closed) : mContext.getString(R.string.open));
    }

}
