package com.learned.gallery;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by howe.zhong
 * on 2023/10/9  9:44
 */
public class ImageItem extends LinearLayout {
    private final ImageView mImageView2;
    private final ImageView mImageView;
    private final LinearLayout item_root;

    public ImageItem(Context context) {
        this(context, null);
    }

    public ImageItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setFocusable(false);
        this.setClickable(false);
        this.setFocusableInTouchMode(false);
        this.setGravity(Gravity.CENTER_VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.item_picture, this, true);
        mImageView = this.findViewById(R.id.imageview);
        mImageView2 = this.findViewById(R.id.imageview2);
        item_root = this.findViewById(R.id.item_root);
    }

    public View getImageView() {
        return mImageView;
    }
    public View getImageView2() {
        return mImageView2;
    }

    public LinearLayout getItem_root() {
        return item_root;
    }
}
