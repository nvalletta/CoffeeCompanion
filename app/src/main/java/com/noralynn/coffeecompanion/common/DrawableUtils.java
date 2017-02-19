package com.noralynn.coffeecompanion.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;

public class DrawableUtils {

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

}
