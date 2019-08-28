package com.example.myapplication.openclose;

public class DistrictItem extends Item {
    String mDistrictName;

    public DistrictItem(String district) {
        super();
        this.mDistrictName = district;
        setText(district);
        mItemType = Item.TYPE_DISTRICT;
    }
}
