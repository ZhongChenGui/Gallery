package com.learned.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.ItemBridgeAdapter;

import android.os.Bundle;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ArrayObjectAdapter mArrayObjectAdapter;
    private HorizontalGridView mHorizontalGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mArrayObjectAdapter = new ArrayObjectAdapter(new ImagePresenter());
        ItemBridgeAdapter itemBridgeAdapter = new ItemBridgeAdapter(mArrayObjectAdapter);
        mHorizontalGridView = this.findViewById(R.id.horizontal_grid_view);
        mHorizontalGridView.setAdapter(itemBridgeAdapter);
//        horizontalGridView.requestFocus();
        mHorizontalGridView.setWindowAlignmentOffsetPercent(50);
        mHorizontalGridView.setFocusableInTouchMode(false);
        mHorizontalGridView.setFocusable(false);
        mHorizontalGridView.setClickable(false);
        mHorizontalGridView.setWindowAlignment(HorizontalGridView.WINDOW_ALIGN_NO_EDGE);
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

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mHorizontalGridView.getScrollState() == HorizontalGridView.SCROLL_STATE_IDLE) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    mHorizontalGridView.setSelectedPositionSmooth(mHorizontalGridView.getSelectedPosition() - 1);
                    return true;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    mHorizontalGridView.setSelectedPositionSmooth(mHorizontalGridView.getSelectedPosition() + 1);
                    return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }
}