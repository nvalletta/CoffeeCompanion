package com.noralynn.coffeecompanion.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.noralynn.coffeecompanion.Application;
import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.adapter.CoffeeAdapter;
import com.noralynn.coffeecompanion.model.Beverage;
import com.noralynn.coffeecompanion.utils.DataUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @NonNull
    private static final String TAG = Application.class.getSimpleName();

    @Nullable
    private RecyclerView mRecyclerCoffee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerCoffee = (RecyclerView) findViewById(R.id.recycler_coffee);
        if (null != mRecyclerCoffee) {
            mRecyclerCoffee.setAdapter(new CoffeeAdapter(this));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: Yelp API call to find nearby coffee shops
            }
        });

        checkBeveragesInfo();
    }

    private void checkBeveragesInfo() {
        if (!DataUtils.beverageFileExists(this)) {
            Log.w(TAG, "Beverage file doesn't exist yet... creating...");
            DataUtils.writeToBeverageFile(this, "[  \n" +
                    "   {  \n" +
                    "      \"name\":\"Café au lait\",\n" +
                    "      \"description\":\"pictures\\/smallest\\/dierdrepic.jpg\",\n" +
                    "      \"drawableResourceName\":\"ic_cafe_au_lait\"\n" +
                    "   },\n" +
                    "   {  \n" +
                    "      \"name\":\"Café au lait\",\n" +
                    "      \"description\":\"pictures\\/smallest\\/dierdrepic.jpg\",\n" +
                    "      \"drawableResourceName\":\"ic_cafe_au_lait\"\n" +
                    "   },\n" +
                    "   {  \n" +
                    "      \"name\":\"Café au lait\",\n" +
                    "      \"description\":\"pictures\\/smallest\\/dierdrepic.jpg\",\n" +
                    "      \"drawableResourceName\":\"ic_cafe_au_lait\"\n" +
                    "   },\n" +
                    "   {  \n" +
                    "      \"name\":\"Café au lait\",\n" +
                    "      \"description\":\"pictures\\/smallest\\/dierdrepic.jpg\",\n" +
                    "      \"drawableResourceName\":\"ic_cafe_au_lait\"\n" +
                    "   },\n" +
                    "   {  \n" +
                    "      \"name\":\"Café au lait\",\n" +
                    "      \"description\":\"pictures\\/smallest\\/dierdrepic.jpg\",\n" +
                    "      \"drawableResourceName\":\"ic_cafe_au_lait\"\n" +
                    "   },\n" +
                    "   {  \n" +
                    "      \"name\":\"Café au lait\",\n" +
                    "      \"description\":\"pictures\\/smallest\\/dierdrepic.jpg\",\n" +
                    "      \"drawableResourceName\":\"ic_cafe_au_lait\"\n" +
                    "   },\n" +
                    "   {  \n" +
                    "      \"name\":\"Café au lait\",\n" +
                    "      \"description\":\"pictures\\/smallest\\/dierdrepic.jpg\",\n" +
                    "      \"drawableResourceName\":\"ic_cafe_au_lait\"\n" +
                    "   },\n" +
                    "   {  \n" +
                    "      \"name\":\"Café au lait\",\n" +
                    "      \"description\":\"pictures\\/smallest\\/dierdrepic.jpg\",\n" +
                    "      \"drawableResourceName\":\"ic_cafe_au_lait\"\n" +
                    "   }\n" +
                    "]");

            Log.w(TAG, "Beverage file created successfully.");
            Log.w(TAG, "Attempting to read list of Beverages from the beverage file...");
            String jsonString = DataUtils.readFromBeverageFile(this);
            if (null == jsonString) {
                Log.w(TAG, "Read failed :(");
            } else {
                Log.w(TAG, "Read successfully. Attempting to convert to a list of beverages using Gson...");
                List<Beverage> beverageList = DataUtils.deserializeBeveragesFromJson(jsonString);
                Log.w(TAG, "Deserialized Successfully!");
            }
        }
    }

}
