package com.duanj.menumanager.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Mr. Duan
 * Date: 2018/8/15
 * Description:ViewPage 适配器
 */

public class ViewPagerAdapter<T> extends PagerAdapter {
    private List<T> viewList;
    private int pageIndex=0;


    public ViewPagerAdapter() {
        viewList = new ArrayList<>();
    }

    public  void add(List<T> datas) {
        if (viewList.size() > 0) {
            viewList.clear();
        }
        viewList.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView((View) viewList.get(position));
       pageIndex=position;
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
