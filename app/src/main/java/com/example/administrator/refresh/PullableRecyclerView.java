package com.example.administrator.refresh;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016/6/15.
 */
public class PullableRecyclerView extends RecyclerView implements Pullable{
    private Context context;
    public PullableRecyclerView(Context context) {
        super(context);
        this.context = context;
    }

    public PullableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public PullableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }


    @Override
    public boolean canPullDown() {
        RecyclerView.LayoutManager mLayoutManager = this.getLayoutManager();
        if (getChildCount() == 0)
        {
            // 没有item的时候也可以下拉刷新
            return true;
        } else if (((LinearLayoutManager)mLayoutManager).findFirstVisibleItemPosition() == 0
                && getChildAt(0).getTop() >= 0)
        {
            // 滑到ListView的顶部了
            return true;
        } else
            return false;
    }

    @Override
    public boolean canPullUp() {
        RecyclerView.LayoutManager mLayoutManager = this.getLayoutManager();
       int fistvisiblePosition = ((LinearLayoutManager)mLayoutManager).findFirstVisibleItemPosition();
        int lastvisiblePosition = ((LinearLayoutManager)mLayoutManager).findLastVisibleItemPosition();
        int ss =getChildCount();
        if(fistvisiblePosition + lastvisiblePosition == 0){
            return false;
        }else if (getChildCount() == 0)
        {
            // 没有item的时候也可以上拉加载
            return true;
        } else if (lastvisiblePosition == (((LinearLayoutManager)mLayoutManager).getItemCount() - 1))
        {
            // 滑到底部了
            if (getChildAt(lastvisiblePosition - fistvisiblePosition) != null
                    && getChildAt(lastvisiblePosition - fistvisiblePosition).getBottom() <= getMeasuredHeight())
                return true;
        }
        return false;
    }
}
