package com.example.myapplication.openclose;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class OpenCloseRecyclerView {
    public void init(RecyclerView recyclerView) {
        OpenCloseAdapter adapter = new OpenCloseAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividiItem());
    }
}
