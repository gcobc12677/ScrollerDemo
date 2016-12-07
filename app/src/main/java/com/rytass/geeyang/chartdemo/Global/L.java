
package com.rytass.geeyang.chartdemo.Global;


import android.util.Log;

public class L {

    public final static String TAG = "LCLASS";
    public final static String ERROR = "error";
    public final static boolean IS_DEBUG = true;

    public final static void e(Throwable e) {
        if (IS_DEBUG) {
            try {
                L.e(ERROR, e.toString(), e);
            } catch (RuntimeException e2) {
            } catch (Exception e2) {
            }
        }
    }

    public final static void e(String TAG, String msg, Throwable e) {
        if (IS_DEBUG) {
            try {
                Log.e(TAG, msg, e);
            } catch (RuntimeException e2) {
            } catch (Exception e2) {
            }
        }
    }

    public final static void d() {
        String nowClass = C.EMPTY;
        String nowMethod = C.EMPTY;
        int nowLineNumber = 0;
        if (IS_DEBUG) {
            try {
                nowClass = Thread.currentThread().getStackTrace()[3].getClassName();
                if(nowClass.indexOf(C.MONEY)!=-1)
                    nowClass=nowClass.substring(0,nowClass.indexOf(C.MONEY));
                nowClass=(nowClass.indexOf(46)!=-1)?nowClass.substring(nowClass.lastIndexOf(46)+1):nowClass;
                nowMethod = Thread.currentThread().getStackTrace()[3].getMethodName();
                nowLineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
                d(TAG, C.LFT + nowClass + C.RGT_LFT + nowMethod + C.RGT_LFT + nowLineNumber + C.RGT);
            } catch (RuntimeException e) {
            } catch (Exception e) {
            }
        }
    }

    public final static void d(String msg) {
        String nowClass = C.EMPTY;
        String nowMethod = C.EMPTY;
        int nowLineNumber = 0;
        if (IS_DEBUG) {
            try {
                nowClass = Thread.currentThread().getStackTrace()[3].getClassName();
                if(nowClass.indexOf(C.MONEY)!=-1)
                    nowClass=nowClass.substring(0,nowClass.indexOf(C.MONEY));
                nowClass=(nowClass.indexOf(46)!=-1)?nowClass.substring(nowClass.lastIndexOf(46)+1):nowClass;
                nowMethod = Thread.currentThread().getStackTrace()[3].getMethodName();
                nowLineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
                d(TAG, C.LFT + nowClass + C.RGT_LFT + nowMethod + C.RGT_LFT + nowLineNumber + C.RGT + msg);
            } catch (RuntimeException e) {
            } catch (Exception e) {
            }
        }
    }

    public final static void d(String TAG, String msg) {
        String tId = getThreadId();
        if (IS_DEBUG) {
            try {
                Log.d(TAG, (tId + msg));
            } catch (RuntimeException e) {
            } catch (Exception e) {
            }
        }
    }

    public static String getThreadId() {
        return C.SPACE + C.DASH + C.LFT + Thread.currentThread().getId() + C.RGT;
    }

}

