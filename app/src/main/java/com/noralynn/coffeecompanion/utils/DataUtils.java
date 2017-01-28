package com.noralynn.coffeecompanion.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.noralynn.coffeecompanion.model.Beverage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.List;

public class DataUtils {

    private static final String TAG = DataUtils.class.getSimpleName();

    private static final String FILE_NAME_BEVERAGES = "beverages.json";

    public static boolean beverageFileExists(@NonNull Context context) {
        return context.getFileStreamPath(FILE_NAME_BEVERAGES).exists();
    }

    @Nullable
    public static String serializeBeveragesToJson(@NonNull List<Beverage> beverages) {
        return new Gson().toJson(beverages);
    }

    @Nullable
    public static List<Beverage> deserializeBeveragesFromJson(@NonNull String jsonString) {
        Type type = new TypeToken<List<Beverage>>(){}.getType();
        return new Gson().fromJson(jsonString, type);
    }

    public static void writeToBeverageFile(@NonNull Context context, @NonNull String data) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(FILE_NAME_BEVERAGES, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Nullable
    public static String readFromBeverageFile(@NonNull Context context) {
        try {
            InputStream inputStream = context.openFileInput(FILE_NAME_BEVERAGES);
            if (null == inputStream) {
                return null;
            }

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString;
            StringBuilder stringBuilder = new StringBuilder();

            while ((receiveString = bufferedReader.readLine()) != null ) {
                stringBuilder.append(receiveString);
            }

            inputStream.close();
            return stringBuilder.toString();
        }
        catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Error when attempting to read file: " + e.toString());
        }

        return null;
    }

}
