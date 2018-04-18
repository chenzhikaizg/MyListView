package com.example.myday14;

import android.content.Context;
import android.content.IntentFilter;
import android.support.annotation.BoolRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

/**
 * Created by ${chenzhikai} on 2018/4/17.
 */

public class MyVerticalListView  extends FrameLayout {

    private ViewDragHelper viewDragHelper;
    private View mListView;
    private int measuredHeight;
    private float mOldY;
    private float mMoveY;
    // 菜单是否打开
    private boolean mMenuIsOpen = false;
    public MyVerticalListView(@NonNull Context context) {
        this(context,null);
    }

    public MyVerticalListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyVerticalListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        viewDragHelper = ViewDragHelper.create(this, mViewDragHelperCallBack);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount !=2){
                throw new RuntimeException("MyVerticalListView只能包含两个子View");
        }
        mListView = getChildAt(1);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed){
            measuredHeight = getChildAt(0).getMeasuredHeight();

        }

    }

    private ViewDragHelper.Callback mViewDragHelperCallBack = new ViewDragHelper.Callback() {
     @Override
     public boolean tryCaptureView(View child, int pointerId) {
         //表示能能让前面的那个view滑动
         return mListView == child;
     }

     @Override
     public int clampViewPositionVertical(View child, int top, int dy) {
         //控制前面的view滑动的范围
         if (top<=0){
             top=0;
         }
         if (top>= measuredHeight){
             top = measuredHeight;
         }
         return top;
     }
     //在手指松开的时候，选择打开还是关闭
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (releasedChild == mListView){
                if (mListView.getTop()>measuredHeight/2){
                    // 滚动到菜单的高度（打开）
                    viewDragHelper.settleCapturedViewAt(0,measuredHeight);
                    mMenuIsOpen = true;
                }else {
                    mMenuIsOpen = false;
                    viewDragHelper.settleCapturedViewAt(0,0);
                }

                invalidate();
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);

        return true;

    }

    //关闭菜单
    public void closeMenu(){
        mMenuIsOpen = false;
        mListView.scrollTo(0,0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {


        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //拿到按下时的y位置
                mOldY = event.getY();
                viewDragHelper.processTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveY = event.getY();
                if ((mMoveY-mOldY)>0 && !canChildScrollUp()){

                    return  true;
                }
                // 菜单打开要拦截
                if (mMenuIsOpen) {
                    return true;
                }
                break;
        }
        return super.onInterceptHoverEvent(event);
    }

    /**
     * 响应滚动
     */
    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     * 判断View是否滚动到了最顶部,还能不能向上滚
     */
    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mListView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mListView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mListView, -1) || mListView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mListView, -1);
        }
    }
}
