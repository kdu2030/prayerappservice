package com.kevin.prayerappservice.utils;

import com.kevin.prayerappservice.exceptions.DataValidationException;

public class ColorUtils {
    public static int colorHexStringToInt(String colorHexString) {
        if (!colorHexString.matches("#[0-9a-fA-F]{6}")) {
            throw new DataValidationException(new String[]{String.format("%s is not a valid color hex string.",
                    colorHexString)});
        }
        String digitsStr = colorHexString.substring(1);
        return Integer.parseInt(digitsStr, 16);
    }

    public static String colorIntToHexString(int colorInt){
        return String.format("#%x", colorInt);
    }
}
