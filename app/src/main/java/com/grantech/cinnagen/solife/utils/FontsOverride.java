package com.grantech.cinnagen.solife.utils;

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
        setFont(defaultFontNameToOverride, customFontTypeface);
    }

    public static void setFont(String defaultFontNameToOverride, Typeface customFontTypeface)
    {
        try {
            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            map.put(defaultFontNameToOverride, customFontTypeface);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void setTypeface(String name, Typeface typeface)
    {
        map.put(name, typeface);
    }
    public static Typeface getTypeface(String name)
    {
        return map.get(name);
    }

}