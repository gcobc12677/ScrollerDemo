package com.rytass.geeyang.chartdemo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rytass.geeyang.chartdemo.Global.L;
import com.rytass.geeyang.chartdemo.Holder.BaseHolder;

import java.util.ArrayList;

/**
 * Created by yangjiru on 2016/6/6.
 */
public class CDAdapter<T, S extends BaseHolder> extends BaseAdapter {

    public ArrayList<T> datas = null;
    public Activity activity = null;
    private LayoutInflater inflater = null;
    public ArrayList<S> holders = null;
    T data = null;

    public CDAdapter(Activity activity) {
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (datas != null) {
            holders = new ArrayList<S>(datas.size());
        } else {
            holders = new ArrayList<S>(0);
        }
    }

    public void release() {
        try {
            activity = null;
            inflater = null;
            if (holders != null && holders.size() > 0) {
                for (S holder : holders) {
                    holder.release();
                }
            }
        } catch (Exception e) {
            /*L.e(e);*/
        }

    }

    @Override
    public int getCount() {
        int size = 0;
        if (datas != null) {
            size = datas.size();
        }
        return size;
    }

    @Override
    public T getItem(int position) {
        // TODO Auto-generated method stub
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View vi = null;
        S holder = null;
        try {
            vi = convertView;
            if (convertView == null) {
                holder = getS(activity);
                vi = holder.inflate(inflater);
                holders.add(holder);
            } else {
                holder = (S) vi.getTag();
            }
            data = getItem(position);
            holder.setData(data, position);
            holder.reset();
            vi.setTag(holder);
            return vi;
        } catch (Exception e) {
            /*L.e(e);*/
        }
        return vi;
    }

    protected Class<S> clazzOfS;

    public S getS(Activity activity) {
        try {
            return clazzOfS.getDeclaredConstructor(Activity.class).newInstance(activity);
        } catch (Exception e) {
            L.e(e);
            return null;
        }
    }

    public void setData(ArrayList<T> datas) {
        this.datas = datas;
    }
}

