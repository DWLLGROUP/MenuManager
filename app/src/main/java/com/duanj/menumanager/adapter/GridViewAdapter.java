package com.duanj.menumanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.duanj.menumanager.ui.MainActivity;
import com.duanj.menumanager.R;
import com.duanj.menumanager.bean.MenuEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Author: Mr. Duan
 * Date: 2018/8/15
 * Description:ViewPage gridView适配器
 */

public class GridViewAdapter extends BaseAdapter {
    private List<MenuEntity> dataList;
    private int pageIndex = 0;
    private Context context;
    public GridViewAdapter(Context context, List<MenuEntity> datas, int page) {
        dataList = new ArrayList<>();
        //startd分别代表要显示的数组在总数据List中的开始和结束位置
        int start = page * MainActivity.item_grid_num;
        int end = start + MainActivity.item_grid_num;
        pageIndex = page;
        while ((start < datas.size()) && (start < end)) {
            dataList.add(datas.get(start));
            start++;
        }
        this.context=context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View itemView, ViewGroup viewGroup) {
        ViewHolder mHolder;
        if (itemView == null) {
            mHolder = new ViewHolder();
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_item, viewGroup, false);
            mHolder.iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            mHolder.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            mHolder.iv_flag = (ImageView) itemView.findViewById(R.id.iv_flag);
            itemView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) itemView.getTag();
        }
        MenuEntity menuEntity = dataList.get(i);
        if (menuEntity != null) {
            mHolder.iv_flag.setVisibility(View.GONE);
            //获取资源图片
            int drawableId =context.getResources().getIdentifier(menuEntity.getIco(),"drawable", context.getPackageName());
            mHolder.iv_icon.setBackgroundResource(drawableId);
            mHolder.tv_name.setText(menuEntity.getTitle());
        }
        return itemView;
    }

    private class ViewHolder {
        private ImageView iv_icon;
        private TextView tv_name;
        private ImageView iv_flag;
    }

}
