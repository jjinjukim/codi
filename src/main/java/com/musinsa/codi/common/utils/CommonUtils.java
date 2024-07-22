package com.musinsa.codi.common.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class CommonUtils {
    public static String formatPrice(Integer price) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);
        return numberFormat.format(price);
    }
}
