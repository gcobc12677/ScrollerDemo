
package com.rytass.geeyang.chartdemo.Receiver;

import android.content.BroadcastReceiver;

public abstract class CDReceiver extends BroadcastReceiver {

    public final static String MSG_TYPE = "MessageType";
    public final static String NAME_FILTER = (CDReceiver.class.getPackage() + ".cdreceiver");
    public final static int TYPE_GCM_RECEIVE = 0;

}

