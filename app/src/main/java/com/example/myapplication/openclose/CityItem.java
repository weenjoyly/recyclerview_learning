package com.example.myapplication.openclose;

import java.util.ArrayList;
import java.util.List;

public class CityItem extends Item {
    private List<String> mDistrictList;
    private List<Item> mItemList;

    public CityItem(String city, List<String> mDistrictList) {
        super();
        this.mDistrictList = mDistrictList;
        setText(city);
        mItemType = Item.TYPE_CITY;
    }

    public List<Item> getData() {
        if (mItemList != null) {
            return mItemList;
        }
        mItemList = new ArrayList<>();
        if (mDistrictList == null) {
            return mItemList;
        }
        for (int i = 0; i < mDistrictList.size(); i++) {
            mItemList.add(new DistrictItem(mDistrictList.get(i)));
        }
        return mItemList;
    }
}
