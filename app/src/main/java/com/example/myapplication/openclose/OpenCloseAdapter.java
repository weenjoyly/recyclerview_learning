package com.example.myapplication.openclose;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.myapplication.model.ProvinceCityDistrictData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OpenCloseAdapter extends RecyclerView.Adapter {
    private static final String TAG = "OpenCloseAdapter";
    List<Item> mItemList;//初始化。也就是省列表
    ProvinceCityDistrictData mProvinceCityDistrictData;

    public OpenCloseAdapter() {
        mItemList = new ArrayList<>();
        Observable.create(new ObservableOnSubscribe<ProvinceCityDistrictData>() {
            @Override
            public void subscribe(ObservableEmitter<ProvinceCityDistrictData> emitter) throws Exception {
                ProvinceDataProcessImpl provinceData = new ProvinceDataProcessImpl();
                emitter.onNext(provinceData.processData());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ProvinceCityDistrictData>() {
            @Override
            public void accept(ProvinceCityDistrictData data) throws Exception {
                update(data);
            }
        });
    }

    private void update(ProvinceCityDistrictData data) {
        OpenCloseAdapter.this.mProvinceCityDistrictData = data;
        Set<String> set = data.provinceCityMap().keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String province = iterator.next();
            mItemList.add(new ProvinceItem(province, mProvinceCityDistrictData.provinceCityMap().get(province), mProvinceCityDistrictData.cityDistrictMap()));
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position).getItemType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: create" + i);
        return OpenCloseVHFactory.create(i, viewGroup.getContext(), viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: bind" + i);
        TextView textView = null;
        if (viewHolder instanceof CityVH) {
            textView = ((CityVH) viewHolder).mTextView;
        } else if (viewHolder instanceof ProvinceVH) {
            textView = ((ProvinceVH) viewHolder).mTextView;
        } else if (viewHolder instanceof DistrictVH) {
            textView = ((DistrictVH) viewHolder).mTextView;
        }
        textView.setText(mItemList.get(i).getText());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = mItemList.get(viewHolder.getAdapterPosition());
                List<Item> sonList = item.getData();
                if (sonList.size() == 0) {
                    return;
                }
                item.openOrClose();
                if (item.getOpenType() == Item.TYPE_OPEN) {
                    mItemList.addAll(viewHolder.getAdapterPosition() + 1, sonList);
                } else if (item instanceof ProvinceItem) {
                    mItemList.removeAll(sonList);
                    for (Item sonItem : sonList) {
                        if (sonItem.isOpen()) {
                            List<Item> grandSonList = sonItem.getData();
                            for (Item grandSonItem : grandSonList) {
                                grandSonItem.close();
                            }
                            mItemList.removeAll(grandSonList);
                        }
                        sonItem.close();
                    }
                } else {
                    mItemList.removeAll(sonList);
                    for (int i = 0; i < sonList.size(); i++) {
                        sonList.get(i).close();
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
