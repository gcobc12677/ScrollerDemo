
package com.rytass.geeyang.chartdemo.Custom;

import android.os.Parcel;
import android.os.Parcelable;

public class BroadCastData implements Parcelable {

    public int messageType = 0;
    public String key = null;
    public String key2 = null;

    public BroadCastData() {
    }

    public BroadCastData(Parcel read) {
        messageType = read.readInt();
        key = read.readString();
        key2 = read.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(messageType);
        if (key != null) {
            dest.writeString(key);
        } else {
            dest.writeString("");
        }
        if (key2 != null) {
            dest.writeString(key2);
        } else {
            dest.writeString("");
        }
    }

    public final static Creator<BroadCastData> CREATOR = new Creator<BroadCastData>() {

        @Override
        public BroadCastData createFromParcel(Parcel source) {
            return new BroadCastData(source);
        }

        @Override
        public BroadCastData[] newArray(int size) {
            return new BroadCastData[size];
        }

    };

}

