package com.grantech.cinnagen.solife;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vamsi on 06-05-2017 for Android custom font article
 */
public class FontsOverride
{
    static Map<String, Typeface> map = new HashMap<>();
    public static void setFont(Context context, String defaultFontNameToOverride, String fontAssetName)
    {
        final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), fontAssetName);
        try {
            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            map.put(fontAssetName, customFontTypeface);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static Typeface getTypeface(String name)
    {
        return map.get(name);
    }

}