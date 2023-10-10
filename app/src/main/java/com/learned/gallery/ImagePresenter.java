package com.learned.gallery;

import android.graphics.Color;
import android.view.ViewGroup;

import androidx.leanback.widget.Presenter;

/**
 * Created by howe.zhong
 * on 2023/10/9  9:48
 */
public class ImagePresenter extends Presenter {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        ImageItem imageItem = new ImageItem(parent.getContext());
        return new ViewHolder(imageItem);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        ImageItem imageItem = (ImageItem) viewHolder.view;
        if (item instanceof Picture) {
            if (((Picture) item).getId() % 2 == 0) {
                imageItem.getImageView().setBackgroundColor(Color.RED);
                imageItem.getImageView2().setBackgroundColor(Color.BLUE);
            } else {
                imageItem.getImageView().setBackgroundColor(Color.BLUE);
                imageItem.getImageView2().setBackgroundColor(Color.RED);
            }
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

    }
}
