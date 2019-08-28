package com.example.myapplication.openclose;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProvinceItem extends Item {
    String mProvinceName;
    List<String> mCityList;
    List<Item> mItemList;
    Map<String, List<String>> mCityDistrictMap;

    public ProvinceItem(String province, List<String> cityList, Map<String, List<String>> cityDistrictMap) {
        super();
        this.mProvinceName = province;
        this.mCityList = cityList;
        this.mCityDistrictMap = cityDistrictMap;
        setText(province);
        mItemType = Item.TYPE_PROVINCE;
    }

    public List<Item> getData() {
        if (mItemList != null) {
            return mItemList;
        }
        mItemList = new ArrayList<>();
        if (mCityList == null) {
            return mItemList;
        }
        for (String cityName : mCityList) {
            mItemList.add(new CityItem(cityName, mCityDistrictMap.get(cityName)));
        }
        return mItemList;
    }

}
