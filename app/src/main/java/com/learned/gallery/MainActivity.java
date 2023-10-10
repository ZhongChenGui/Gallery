package com.learned.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.ItemBridgeAdapter;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ArrayObjectAdapter mArrayObjectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HorizontalGridView horizontalGridView = this.findViewById(R.id.horizontal_grid_view);
        mArrayObjectAdapter = new ArrayObjectAdapter(new ImagePresenter());
        ItemBridgeAdapter itemBridgeAdapter = new ItemBridgeAdapter(mArrayObjectAdapter);
        horizontalGridView.setAdapter(itemBridgeAdapter);
        horizontalGridView.requestFocus();
        horizontalGridView.setWindowAlignmentOffsetPercent(50);
        horizontalGridView.setWindowAlignment(HorizontalGridView.WINDOW_ALIGN_NO_EDGE);
        initData();
    }

    private void initData() {
        mArrayObjectAdapter.addAll(0, getStarsList());
    }

    private List<Picture> getStarsList() {
        List<Picture> pictureList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            pictureList.add(new Picture(i, i + "", i + ""));
        }
        return pictureList;
    }
}