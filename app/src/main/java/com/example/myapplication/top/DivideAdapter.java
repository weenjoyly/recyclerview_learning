package com.example.myapplication.top;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class DivideAdapter extends RecyclerView.Adapter {

    private List<String> mProvinceList = new ArrayList<>();
    private Map<String, List<String>> mCity2DistrictMap;
    private Map<Object, Boolean> mOpenMap = new HashMap<>();
    private RecyclerView mRecyclerView;
    private int mCount;
    private Set<Integer> mOpenedList = new HashSet<>();

    public DivideAdapter(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    @Override
    public int getItemViewType(int position) {
        if (mOpenedList.contains(position)) {
            return 1;
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new DivideVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == 1) {
            final DivideVH vh = (DivideVH) viewHolder;
            int index = 1;
            while (getItemViewType(i - index) == 1) {
                index++;
            }
            //找到当前属于哪一个城市呢？
            String currentCity = mProvinceList.get(i - index);
            String pre = "       ";
            vh.mTextView.setText(pre + mCity2DistrictMap.get(currentCity).get(index - 1));
            vh.mLayout.setBackgroundColor(Color.RED);
            return;
        }
        final DivideVH vh = (DivideVH) viewHolder;
        vh.mTextView.setText(mProvinceList.get(i));
        vh.mLayout.setBackgroundColor(Color.RED);
        vh.mLayout.setTag(mProvinceList.get(i));
    }

    @Override
    public int getItemCount() {
        return mProvinceList.size() + mCount;
    }

    public void setData(List<String> listProvince, Map<String, List<String>> city2DistrictMap) {
        this.mProvinceList = listProvince;
        this.mCity2DistrictMap = city2DistrictMap;
        notifyDataSetChanged();
    }
}
