package com.example.myapplication.openclose;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;


public class OpenCloseVHFactory {

    public static RecyclerView.ViewHolder create(int type, Context context, ViewGroup viewGroup) {
        int layoutid = 0;
        if (type == 0) {
            layoutid = R.layout.item;
        } else if (type == 1) {
            layoutid = R.layout.city_item;
        } else {
            layoutid = R.layout.district_item;
        }
        View view = LayoutInflater.from(context).inflate(layoutid, viewGroup, false);
        if (type == 0) {
            return new ProvinceVH(view);
        } else if (type == 1) {
            return new CityVH(view);
        }
        return new DistrictVH(view);
    }
}
