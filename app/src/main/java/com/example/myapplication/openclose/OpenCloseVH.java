package com.example.myapplication.openclose;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;


public class OpenCloseVH extends RecyclerView.ViewHolder {
    TextView mTextView;

    public OpenCloseVH(@NonNull View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.text);
    }
}
