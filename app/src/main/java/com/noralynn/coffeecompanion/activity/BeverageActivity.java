package com.noralynn.coffeecompanion.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.model.Beverage;
import com.noralynn.coffeecompanion.utils.ResourceUtils;

public class BeverageActivity extends AppCompatActivity implements BeverageView {

    @NonNull
    public static final String BEVERAGE_INTENT_KEY = "BEVERAGE_INTENT_KEY";

    @Nullable
    TextView titleTextView;

    @Nullable
    TextView descriptionTextView;

    @Nullable
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beverage);

        BeverageViewPresenter presenter = new BeverageViewPresenter(this);
        presenter.onCreate();
    }

    @Override
    public void initializeViews() {
        titleTextView = (TextView) findViewById(R.id.text_title);
        descriptionTextView = (TextView) findViewById(R.id.text_description);
        imageView = (ImageView) findViewById(R.id.image_beverage);
    }

    @Nullable
    public Beverage getBeverageFromBundle() {
        Intent intent = getIntent();
        if (null != intent) {
            Bundle extras = intent.getExtras();
            if (null != extras) {
                return extras.getParcelable(BEVERAGE_INTENT_KEY);
            }
        }
        return null;
    }

    @Override
    public void showErrorText(@StringRes int messageResId) {
        if (null == titleTextView) {
            return;
        }
        titleTextView.setText(getString(R.string.error_unable_to_load_beverage));
    }

    @Override
    public void setTitle(@NonNull String name) {
        if (null == titleTextView) {
            return;
        }
        titleTextView.setText(name);
    }

    @Override
    public void setDescription(@NonNull String description) {
        if (null == descriptionTextView) {
            return;
        }
        descriptionTextView.setText(description);
    }

    @Override
    public void setImage(@NonNull String drawableResourceName) {
        if (null == imageView) {
            return;
        }
        Drawable image = ResourceUtils.getDrawableByName(this, drawableResourceName);
        imageView.setImageDrawable(image);
    }

}
