package com.example.myapplication.top;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DivideItemDecoration extends RecyclerView.ItemDecoration {
    private static final int TOPIC_ITEM_HEIGHT = 123;
    private Paint mTextPaint;
    private Paint mBgPaint;

    private Map<String, String> itemNameMap;
    private List<String> itemList;
    private Set<String> firstItemList;
    private Set<String> lastItemList;


    public DivideItemDecoration() {
        firstItemList = new HashSet<>();
        lastItemList = new HashSet<>();
        mBgPaint = new Paint();
        mBgPaint.setColor(Color.GREEN);
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.RED);
        mTextPaint.setStrokeWidth(12f);
        mTextPaint.setTextSize(20f);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            if (!isFirst(child)) {
                continue;
            }
            c.drawRect(0, child.getTop() - TOPIC_ITEM_HEIGHT, parent.getWidth(), child.getTop(), mBgPaint);
            c.drawText(getTag(child), parent.getWidth() / 2f, (child.getTop() - TOPIC_ITEM_HEIGHT + child.getTop()) / 2f, mTextPaint);
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        View view = findFirstLastView(parent);
        int bottom = TOPIC_ITEM_HEIGHT;
        if (view != null && view.getBottom() < TOPIC_ITEM_HEIGHT) {
            bottom = view.getBottom();
        }
        if (view == null) {
            //修复四川城市特别多，超过一屏幕，所以找不到最后一个列表，然后view==null导致无法绘制省份
            view = parent.getChildAt(0);
            if (view == null) {
                return;
            }
        }
        c.drawRect(0, 0, parent.getWidth(), bottom, mBgPaint);
        c.drawText(getTag(view), parent.getWidth() / 2f, bottom / 2f, mTextPaint);
    }

    private View findFirstLastView(RecyclerView parent) {
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            if (isLast(parent.getChildAt(i))) {
                return parent.getChildAt(i);
            }
        }
        return null;
    }

    private String getTag(View view) {
        String data = (String) view.getTag();
        return itemNameMap.get(data);
    }

    private boolean isLast(View view) {
        String tag = (String) view.getTag();
        if (lastItemList.contains(tag)) {
            return true;
        }
        int i = itemList.indexOf(tag);
        if (i == itemList.size() - 1) {
            return true;
        }
        if (i < 0 || i >= itemList.size()) {
            return false;
        }
        String current = itemNameMap.get(itemList.get(i));
        String next = itemNameMap.get(itemList.get(i + 1));
        boolean result = current != null && !current.equals(next);
        if (result) {
            lastItemList.add(tag);
        }
        return result;
    }

    private boolean isFirst(View view) {
        String tag = (String) view.getTag();
        if (firstItemList.contains(tag)) {
            return true;
        }
        int i = itemList.indexOf(tag);
        if (i < 0 || i >= itemList.size()) {
            return false;
        }

        if (i == 0) {
            return true;
        }
        if (i == itemList.size() - 1) {
            return false;
        }
        String current = itemNameMap.get(itemList.get(i));
        String pre = itemNameMap.get(itemList.get(i - 1));
        boolean result = current != null && !current.equals(pre);
        if (result) {
            firstItemList.add(tag);
        }
        return result;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (isFirst(view))
            outRect.set(0, TOPIC_ITEM_HEIGHT, 0, 0);
    }

    public void setData(HashMap<String, String> itemNameMap, List<String> listProvince) {
        this.itemNameMap = itemNameMap;
        this.itemList = listProvince;
    }


}
