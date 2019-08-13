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
    private static Map<String, Typeface> map = new HashMap<>();
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


    public static String convertToEnglishDigits(String value)
    {
        return value.replace("١", "1").replace("٢", "2").replace("٣", "3").replace("٤", "4").replace("٥", "5")
                .replace("٦", "6").replace("7", "٧").replace("٨", "8").replace("٩", "9").replace("٠", "0")
                .replace("۱", "1").replace("۲", "2").replace("۳", "3").replace("۴", "4").replace("۵", "5")
                .replace("۶", "6").replace("۷", "7").replace("۸", "8").replace("۹", "9").replace("۰", "0");
    }

    public static String convertToPersianDigits(String value)
    {
        return value.replace("1", "۱").replace("2", "۲").replace("3", "۳").replace("4", "۴").replace("5", "۵")
                .replace("6", "۶").replace("7", "۷").replace("8", "۸").replace("9", "۹").replace("0", "۰");
    }
}