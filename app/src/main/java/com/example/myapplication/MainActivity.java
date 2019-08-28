package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.myapplication.top.TopItemRecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = new RecyclerView(this);
        setContentView(recyclerView);
        //   展开收起
        //  new OpenCloseRecyclerView().init(recyclerView);
        //悬浮置顶实现
        new TopItemRecyclerView().init(recyclerView);
    }
}
