package com.warkiz.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * created by zhuangguangquan on 2018/5/27
 * <p>
 * the container for IndicatorSeekBar to make the indicator stay always
 */
public class IndicatorStayLayout extends LinearLayout {

    public IndicatorStayLayout(Context context) {
        this(context, null);
    }

    public IndicatorStayLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorStayLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            layoutIndicator(getChildAt(i), i);
        }
        super.onFinishInflate();
    }

    /**
     * If you want to initial seek bar by java code to make
     * indicator stay always,call this.
     *
     * @param seekBar the direct child in indicatorStayLayout
     */
    public void attachTo(IndicatorSeekBar seekBar) {
        attachTo(seekBar, -2);
    }

    /**
     * If you want to initial seek bar by java code to make
     * indicator stay always,call this.
     *
     * @param seekBar the direct child in indicatorStayLayout
     * @param index   the child index you wanted indicatorSeekBar to attach to IndicatorStayLayout;
     */
    public void attachTo(IndicatorSeekBar seekBar, int index) {
        if (seekBar == null) {
            throw new NullPointerException("the seek bar wanna attach to IndicatorStayLayout " +
                    "can not be null value.");
        }
        layoutIndicator(seekBar, index);
        addView(seekBar, index + 1);
    }

    /**
     * layout each indicator
     *
     * @param child the indicatorSeekBar which should hava a indicator content view.
     * @param index the index you want the seek bar to located in IndicatorStayLayout.
     */
    private void layoutIndicator(View child, int index) {
        if (child instanceof IndicatorSeekBar) {
            IndicatorSeekBar seekBar = (IndicatorSeekBar) child;
            View contentView = seekBar.getIndicatorContentView(true);
            if (contentView == null) {
                throw new IllegalStateException("Can not find any indicator in the IndicatorSeekBar, please " +
                        "make sure you have called the attr: SHOW_INDICATOR_TYPE for IndicatorSeekBar and the value is not IndicatorType.NONE.");
            }
            if (contentView instanceof IndicatorSeekBar) {
                throw new IllegalStateException("IndicatorSeekBar can not be a contentView for Indicator in case this inflating loop.");
            }
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            MarginLayoutParams layoutParams = new MarginLayoutParams(params);
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin,
                    layoutParams.rightMargin, SizeUtils.dp2px(mContext, 2) - seekBar.getPaddingTop());
            addView(contentView, index, layoutParams);
            seekBar.updateStayIndicator(getViewScreenX());
        }
    }

    @Override
    public void setOrientation(int orientation) {
        if (orientation != VERTICAL) {
            throw new IllegalArgumentException("IndicatorStayLayout is always vertical and does"
                    + " not support horizontal orientation");
        }
        super.setOrientation(orientation);
    }

    private int getViewScreenX() {
        int[] mLocation = new int[2];
        getLocationOnScreen(mLocation);
        return mLocation[0];
    }

}
