package com.ilyamarvin.checkyourweather.Common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonInfo {

    public static final String APP_ID = "56e9c4879ae3062c3a25116d0a29f652";
    public static Location current_location = null;

    public static String convertUnixToDate(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm EEE MM yyyy");
        String formatted = sdf.format(date);
        return formatted;
    }
}
