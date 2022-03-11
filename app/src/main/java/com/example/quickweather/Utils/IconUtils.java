package com.example.quickweather.Utils;

import com.example.quickweather.R;

public class IconUtils {

    public static int getIconResourceId(String iconId) {

        switch (iconId) {
            case "01d":
                return R.drawable.ic_01d;
            case "01n":
                return R.drawable.ic_01n;
            case "02d":
                return R.drawable.ic_02d;
            case "02n":
                return R.drawable.ic_02n;
            case "03d":
                return R.drawable.ic_03d;
            case "03n":
                return R.drawable.ic_03d;
            case "04d":
                return R.drawable.icons8_onedrive_96;
            case "04n":
                return R.drawable.icons8_onedrive_96;
            case "09d":
                return R.drawable.ic_09d;
            case "09n":
                return R.drawable.ic_09d;
            case "10d":
                return R.drawable.ic_10d;
            case "10n":
                return R.drawable.ic_10d;
            case "11d":
                return R.drawable.ic_11d;
            case "11n":
                return R.drawable.ic_11d;
            case "13d":
                return R.drawable.ic_13d;
            case "13n":
                return R.drawable.ic_13d;
            case "50d":
                return R.drawable.ic_50d;
            case "50n":
                return R.drawable.ic_50d;
            default:
                return R.drawable.icons8_onedrive_96;
        }
    }
}
