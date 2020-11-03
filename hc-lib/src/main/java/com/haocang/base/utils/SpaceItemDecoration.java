package com.haocang.base.utils;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;


/**
 * Created by william on 2018/4/3.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        view.setBackgroundColor(Color.GRAY);
        if(parent.getChildPosition(view) != 0)
            outRect.top = space;
    }
}