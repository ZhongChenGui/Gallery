package com.learned.gallery;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.leanback.widget.HorizontalGridView;

import java.util.Objects;

/**
 * Created by howe.zhong
 * on 2023/10/9  10:16
 */
public class CustomGridView extends HorizontalGridView {
    public CustomGridView(Context context) {
        super(context, null);
    }

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CustomGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    public View focusSearch(View focused, int direction) {
        // 只有非滚动状态下才能获取下一个view
        if (getScrollState() == SCROLL_STATE_IDLE) {
            return super.focusSearch(focused, direction);
        } else {
            return focused;
        }
    }

    /**
     * 设置子视图的缩放系数/旋转角度
     *
     * @param canvas
     * @param child
     * @param drawingTime
     * @return
     */
    @Override
    public boolean drawChild(Canvas canvas, View child, long drawingTime) {
        String TAG = "drawChild";
        int saveCount = canvas.save();
        // 获取当前位置与选择的位置
        int position = getChildAdapterPosition(child);
        int selectedPosition = getSelectedPosition();
        Log.d("drawChild", "drawChild: position = " + position);
        Log.d("drawChild", "drawChild: selectedPosition = " + selectedPosition);
        // 设置item缩放
        int width = child.getWidth();
        Log.d(TAG, "drawChild: width = " + width);
        float scale = 1f;
        /**
         * 注意:如果当前view小于焦点view，是用焦点view - 当前view
         * 如果当前view大于焦点view，是用当前view - 焦点view
         */
        if (position < selectedPosition) {
            // 在焦点左边：依次缩小0.1，用选中焦点的位置减去当前的位置，比如焦点的位置是 2，当前的位置是1，
            // 那么缩放：     1 - （2 - 1） * 0.1 = 0.9，缩放0.9
            // 下一个缩放为：  1 - （3 - 1） * 0.1 = 0.8，缩放0.8
            // 下一个缩放为：  1 - （4 - 1） * 0.1 = 0.7，缩放0.7
            scale = 1f - (selectedPosition - position) * 0.1f;
            // 设置缩放中心点，为最右边，目的是为了让view始终保持在最右边，方便计算接下来的偏移量
            ((ImageItem) child).getItem_root().setPivotX(width);
        } else if (position > selectedPosition) {
            // 在焦点右边：依次缩小0.1，用当前的位置减去选中焦点的位置，比如焦点的位置是 2，当前的位置是3，
            // 那么缩放：     1 - （3 - 2） * 0.1 = 0.9，缩放0.9
            // 下一个缩放为：  1 - （4 - 2） * 0.1 = 0.8，缩放0.8
            // 下一个缩放为：  1 - （5 - 2） * 0.1 = 0.7，缩放0.7
            scale = 1f - (position - selectedPosition) * 0.1f;
            // 设置缩放中心点，为最左边，目的是为了让view始终保持在最左边，方便计算接下来的偏移量
            ((ImageItem) child).getItem_root().setPivotX(0);
        }
        ((ImageItem) child).getItem_root().setScaleX(scale);
        ((ImageItem) child).getItem_root().setScaleY(scale);

        // 设置item偏移
        /**
         * 焦点view左右的偏移固定
         */
        // 焦点view左边第一个view的位置向左边 -50px
        if (position == selectedPosition - 1) {
            canvas.translate(-50, 0);
        }
        // 焦点view右边第一个view的位置向右边 50px
        if (position == selectedPosition + 1) {
            canvas.translate(50, 0);
        }
        if (position < selectedPosition - 1) {
            // todo 在焦点view左边
            int v = 0; //保存不覆盖上一个view的情况下需要偏移的px [缩放后需要偏移多少px才能紧靠上一个view，比如缩放了 上一个view缩放0.9f,宽是200，
            // 那么上一个view只用了180px，还有20px是空的，那么就需要向右偏移20px才能紧靠上一个view]

            int v2 = 0; // 保存一共盖住了多少个像素 [上一个view需要盖住下一个view多少px]
            // 计算view缩放的倍数判断偏移量
            for (int i = 0; i < selectedPosition - 1 - position; i++) {
                // 焦点左边第二个view偏移量为：(width / 10)的结果是 每次缩放0.1的px,
                // 比如第一个缩小了0.9，宽是200，那么就会空出20px，第二个缩小了0.8f,那么就会空出40px
                v += (i + 1) * (width / 10);
                // ((i + 2) * 0.1 * width) 计算缩放的宽度
                // (width - ((i + 2) * 0.1 * width))  计算缩放后的宽度
                // (width - ((i + 2) * 0.1 * width)) / 5 假设覆盖下一个view 五分之一
                v2 += (width - ((i + 2) * 0.1 * width)) / 5;
            }
            Log.d(TAG, "drawChild: v1 = " + v);
            // -50是默认的间距
            canvas.translate(-50 + v + v2, 0);
        } else if (position > selectedPosition + 1) {
            // todo 在焦点右边，同上
            int v = 0;
            int v2 = 0;
            for (int i = 0; i < position - 1 - selectedPosition; i++) {
                v += (i + 1) * (width / 10);
                v2 += (width - ((i + 2) * 0.1 * width)) / 5;
            }
            Log.d(TAG, "drawChild: v2 = " + v);
            // 50是默认的间距
            canvas.translate(50 - v - v2, 0);
        }
        boolean result = super.drawChild(canvas, child, drawingTime);
        canvas.restoreToCount(saveCount);
        return result;
    }


    /**
     * 重写视图布置顺序:前半顺序绘制，后半倒序绘制，中间位置
     * 中间位置最后一个绘制count-1
     * 中间位置之前的视图绘制顺序为i
     * 中间位置之后的视图绘制顺序为center+count-1-i
     */
    @Override
    public synchronized int getChildDrawingOrder(int childCount, int i) {
        int focus = getSelectedPosition();
//        int focus = getLayoutManager().getPosition(mSnapHelper.findSnapView(getLayoutManager()));
        Log.d("getChildDrawingOrder", "1focus = " + focus);
//        int center = focus;
        int center = childCount / 2;
        if (childCount % 2 != 0) {
            center++;
        }
        int itemCount = Objects.requireNonNull(getAdapter()).getItemCount();
        if (childCount != itemCount) {
            if (focus < center) {
                //开头位置，以目标位置为中心点
                center = focus;
            } else {
                //结尾位置，以目标位置为中心点
//            int count = ((PictureAdapter)getAdapter()).getDataList().size();
//            Log.d("TAG", "count = "+count);
                if (focus == (itemCount - 1)) {
                    center = itemCount - 1;
                }
            }
        } else {
            center = focus;
        }

        //序号为i的视图的绘制序号
        int order;
        if (i == center) {
            order = childCount - 1;
        } else if (i < center) {
            order = i;
        } else {
            order = center + childCount - 1 - i;
        }
        Log.d("getChildDrawingOrder", "childCount = " + childCount + ",center = " + center + ",order = " + order + ",i = " + i);
        return order;
    }
}
