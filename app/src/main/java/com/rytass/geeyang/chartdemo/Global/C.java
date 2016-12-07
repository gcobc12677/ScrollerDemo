
package com.rytass.geeyang.chartdemo.Global;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class C {

    @SuppressLint("SdCardPath")
    public final static String DB_PATH = "/data/data/com.rytass.geeyang.chartdemo/";
    public final static String DATABASE_NAME = "chartdemo.db";
    public final static String APP_PATH = "/chartdemo/";
    public final static String APP_LOG_PATH = (APP_PATH +"log/");
    public final static String APP_MEDIA_PATH = (APP_PATH +"Media/");

    public final static String STATUS_PROPERTY = "statusProperty";
    public final static String MONEY = "$";
    public final static String EMPTY = "";
    public final static String LFT = "[";
    public final static String RGT_LFT = "][";
    public final static String RGT = "]";
    public final static String DASH = "-";
    public final static String SPACE = " ";
    public final static String NULL = "null";
    public final static String HANDLER_PUT_STRING_KEY = "msg";

    public final static String yyyy_MM_ddTHH_mm_ssZ = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public final static SimpleDateFormat sdf_yyyy_MM_ddTHH_mm_ssZ = new SimpleDateFormat(yyyy_MM_ddTHH_mm_ssZ, Locale.US);

    public final static DecimalFormat df_price = new DecimalFormat("#.##");

    public final static String SEPERATE_SIGN = ", ";
}

