package com.noralynn.coffeecompanion.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.model.Beverage;
import com.noralynn.coffeecompanion.utils.ResourceUtils;

public class BeverageActivity extends AppCompatActivity {

    @Nullable
    private Beverage mBeverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beverage);

        Intent intent = getIntent();
        if (null != intent) {
            Bundle extras = intent.getExtras();
            if (null != extras) {
                mBeverage = extras.getParcelable(MainActivity.BEVERAGE_INTENT_KEY);
            }
        }

        if (null != mBeverage) {
            displayData();
        }
    }

    private void displayData() {
        TextView titleTextView = (TextView) findViewById(R.id.text_title);
        if (null == mBeverage) {
            titleTextView.setText(getString(R.string.error_unable_to_load_beverage));
            return;
        }

        TextView descriptionTextView = (TextView) findViewById(R.id.text_description);
        descriptionTextView.setText(mBeverage.getDescription());

        ImageView imageView = (ImageView) findViewById(R.id.image_beverage);
        Drawable icon = ResourceUtils.getDrawableByName(this, mBeverage.getDrawableResourceName());
        imageView.setImageDrawable(icon);
    }


}
