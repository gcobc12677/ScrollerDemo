
package com.rytass.geeyang.chartdemo.Activity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.rytass.geeyang.chartdemo.Global.D;
import com.rytass.geeyang.chartdemo.Global.L;

import java.util.Timer;

public class BaseActivity extends AppCompatActivity {

    public String name = getClass().getSimpleName();
    public Activity activity = null;
    public ProgressDialog loading = null;
    public Timer timer = null;
    public Handler handler = new Handler();
    public Builder networkDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = this;
        try {
            if (L.IS_DEBUG) L.d((name + " onCreate"));
            D.c = getApplicationContext();
            loading = new ProgressDialog(this);
        } catch (Throwable e) {
            L.e(e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void findViews() {
    }

    protected void initial() {
    }

    protected void setListeners() {
    }

    protected void openLoading(final String message) {
        if (handler != null) {
            handler.post(new Runnable() {

                @Override
                public void run() {
                    try {
                        if ((loading != null) && (!loading.isShowing())) {
                            loading.setMessage(message);
                            loading.show();
                        }
                    } catch (Exception e) {
                        L.e(e);
                    }
                }

            });
        }
    }

    protected void closeLoading(final String message) {
        if (handler != null) {
            handler.post(new Runnable() {

                @Override
                public void run() {
                    try {
                        if ((loading != null) && loading.isShowing()) {
                            loading.dismiss();
                        }
                    } catch (Exception e) {
                        L.e(e);
                    }
                }

            });
        }
    }

}

