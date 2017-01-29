package com.noralynn.coffeecompanion.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;

import com.noralynn.coffeecompanion.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ResourceUtils {

    private static final String TAG = ResourceUtils.class.getSimpleName();

    @NonNull
    private static final String ENCODING_FORMAT = "UTF-8";

    private static final int FILE_NAME_BEVERAGES = R.raw.beverages;

    @Nullable
    public static Drawable getDrawableByName(@NonNull Context context, @Nullable String name) {
        if (null == name) {
            return null;
        }

        Resources resources = context.getResources();
        if (null == resources) {
            return null;
        }

        final int resourceId = resources.getIdentifier(name, "drawable", context.getPackageName());
        return ResourcesCompat.getDrawable(resources, resourceId, null);
    }

    @Nullable
    static String getJsonStringFromBeveragesResourceFile(@NonNull Context context) {
        try {
            InputStream inputStream = context.getResources().openRawResource(FILE_NAME_BEVERAGES);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, ENCODING_FORMAT);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String receiveString;
            StringBuilder stringBuilder = new StringBuilder();

            while ((receiveString = bufferedReader.readLine()) != null ) {
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

}
