package com.example.myday14;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by ${chenzhikai} on 2018/4/17.
 */

public class MyListView extends ListView {
    public MyListView(Context context) {
        this(context,null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//
//
//        Log.e("gettop", "onTouchEvent: "+getTop() );
//
//        Log.e("getheight", "onTouchEvent: "+ getHeight());
//
//        Log.e(" getY()", "onTouchEvent: "+ getY());
//        Log.e(" getgetRightY()", "onTouchEvent: "+ getRight());
//        Log.e(" getgetRightY()", "onTouchEvent: "+ getLeft());
//        if (getTop()<700){
//            return false;
//        }else {
//
//            return true;
//        }
//
//    }

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     * 判断View是否滚动到了最顶部,还能不能向上滚
     */
    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (this instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) this;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(this, -1) || this.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(this, -1);
        }
    }
}
