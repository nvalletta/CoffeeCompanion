package com.noralynn.coffeecompanion.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.noralynn.coffeecompanion.model.Beverage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.noralynn.coffeecompanion.utils.ResourceUtils.getJsonStringFromBeveragesResourceFile;

public class DataUtils {

    @Nullable
    public static ArrayList<Beverage> deserializeBeveragesFromJson(@NonNull Context context) {
        String beveragesJsonString = getJsonStringFromBeveragesResourceFile(context);
        if (null == beveragesJsonString) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<Beverage>>(){}.getType();
        return new Gson().fromJson(getJsonStringFromBeveragesResourceFile(context), type);
    }

}
