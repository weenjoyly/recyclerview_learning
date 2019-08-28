package com.example.myapplication.top;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;


public class DivideVH extends RecyclerView.ViewHolder {
    public TextView mTextView;
    public LinearLayout mLayout;

    public DivideVH(@NonNull View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.text);
        mLayout = itemView.findViewById(R.id.layout);
    }
}
