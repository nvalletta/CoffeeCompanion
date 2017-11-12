package com.noralynn.coffeecompanion.beveragelist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.common.Beverage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.noralynn.coffeecompanion.beveragelist.BeverageListActivity.BEVERAGE_LIST_MODEL_BUNDLE_KEY;

class BeverageListViewPresenter {

    private static final String TAG = BeverageListViewPresenter.class.getSimpleName();

    @NonNull
    private static final String ENCODING_FORMAT = "UTF-8";

    private static final int FILE_NAME_BEVERAGES = R.raw.beverages;

    @Nullable
    private BeverageListModel beverageListModel;

    @NonNull
    private BeverageListView beverageListView;

    BeverageListViewPresenter(@NonNull BeverageListView beverageListView) {
        this.beverageListView = beverageListView;
    }

    void onCreate(@Nullable Bundle savedInstanceState) {
        beverageListModel = getBundledBeverageListModel(savedInstanceState);
        checkBeveragesInfo();
    }

    void onClickBeverage(@NonNull Beverage beverage) {
        beverageListView.startBeverageActivity(beverage);
    }

    void onClickFloatingActionButton() {
        beverageListView.startCoffeeShopListActivity();
    }

    @NonNull
    private BeverageListModel getBundledBeverageListModel(@Nullable Bundle savedInstanceState) {
        BeverageListModel model = null;
        if (null != savedInstanceState) {
            model = savedInstanceState.getParcelable(BEVERAGE_LIST_MODEL_BUNDLE_KEY);
        }
        return model != null ? model : new BeverageListModel();
    }

    private void checkBeveragesInfo() {
        if (beverageListModel == null) {
            return;
        }
        // If we already have a list of beverages, don't do any more work.
        List<Beverage> beverages = beverageListModel.getBeverages();
        if (null != beverages) {
            return;
        }

        // Otherwise, let's get a new list of beverages from our raw resource.
        beverages = deserializeBeveragesFromJson();
        if (null != beverages) {
            beverageListModel.setBeverages(beverages);
            beverageListView.displayBeverages(beverageListModel);
        }
    }

    @Nullable
    private ArrayList<Beverage> deserializeBeveragesFromJson() {
        String beveragesJsonString = getJsonStringFromBeveragesResourceFile();
        if (null == beveragesJsonString) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<Beverage>>() {
        }.getType();
        return new Gson().fromJson(getJsonStringFromBeveragesResourceFile(), type);
    }

    @Nullable
    private String getJsonStringFromBeveragesResourceFile() {
        Context context = beverageListView.getContext();
        try {
            InputStream inputStream = context.getResources().openRawResource(FILE_NAME_BEVERAGES);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, ENCODING_FORMAT);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String receiveString;
            StringBuilder stringBuilder = new StringBuilder();

            while ((receiveString = bufferedReader.readLine()) != null) {
                stringBuilder.append(receiveString);
            }

            inputStream.close();
            return stringBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Unsupported encoding! " + ENCODING_FORMAT);
        } catch (IOException e) {
            Log.e(TAG, "Error when attempting to read file: " + e.toString());
        }
        return null;
    }

    @Nullable
    BeverageListModel getBeverageListModel() {
        return beverageListModel;
    }
}
