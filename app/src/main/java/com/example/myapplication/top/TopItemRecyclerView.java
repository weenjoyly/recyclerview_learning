package com.example.myapplication.top;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.example.myapplication.model.ProvinceCityDistrictData;
import com.example.myapplication.openclose.ProvinceDataProcessImpl;

import io.reactivex.Observable;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TopItemRecyclerView {
    public void init(final RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        final DivideAdapter divideAdapter = new DivideAdapter(recyclerView);
        recyclerView.setAdapter(divideAdapter);
        final DivideItemDecoration decoration = new DivideItemDecoration();
        recyclerView.addItemDecoration(decoration);
        Observable.create(new ObservableOnSubscribe<ProvinceCityDistrictData>() {
            @Override
            public void subscribe(ObservableEmitter<ProvinceCityDistrictData> emitter) throws Exception {
                ProvinceDataProcessImpl provinceData = new ProvinceDataProcessImpl();
                ProvinceCityDistrictData process = provinceData.processData();
                emitter.onNext(process);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<ProvinceCityDistrictData>() {
                    @Override
                    public void accept(ProvinceCityDistrictData data) throws Exception {
                        decoration.setData(data.nameMap(), data.itemList());
                        divideAdapter.setData(data.itemList(), data.cityDistrictMap());
                        recyclerView.invalidate();
                    }
                });
    }
}
