package com.example.myapplication.openclose;

import java.util.ArrayList;
import java.util.List;

public class Item {
    public static final int TYPE_CLOSE = 0;
    public static final int TYPE_OPEN = -1;
    public static final int TYPE_PROVINCE = 0;
    public static final int TYPE_CITY = 1;
    public static final int TYPE_DISTRICT = 2;
    int mOpenType = TYPE_CLOSE;
    int mItemType = TYPE_PROVINCE;
    String mText;
    List<Item> mItemList;

    public Item() {
        mOpenType = TYPE_CLOSE;
    }

    public List<Item> getData() {
        if (mItemList != null) {
            return mItemList;
        }
        mItemList = new ArrayList<>();
        return mItemList;
    }

    public void openOrClose() {
        mOpenType = ~mOpenType;
    }

    public void close() {
        mOpenType = TYPE_CLOSE;
    }

    public int getOpenType() {
        return mOpenType;
    }

    public int getItemType() {
        return mItemType;
    }

    public void setText(String text) {
        this.mText = text;
    }

    public String getText() {
        return mText;
    }

    public boolean isOpen() {
        return mOpenType == TYPE_OPEN;
    }
}
