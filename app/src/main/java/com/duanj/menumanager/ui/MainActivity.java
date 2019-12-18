package com.duanj.menumanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.duanj.menumanager.R;
import com.duanj.menumanager.adapter.GridViewAdapter;
import com.duanj.menumanager.adapter.ViewPagerAdapter;
import com.duanj.menumanager.bean.Constant;
import com.duanj.menumanager.bean.MenuEntity;
import com.duanj.menumanager.util.SharedPreferencesUtils;
import com.duanj.menumanager.wedgit.PageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.dot_horizontal)
    LinearLayout dotHorizontal;

    private List<MenuEntity> menuEntities = new ArrayList<>();
    public static int item_grid_num = 8;//每一页中GridView中item的数量
    public static int number_columns = 4;//gridView一行展示的数目
    private ViewPagerAdapter mAdapter;
    private List<GridView> gridList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(mAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        gridList.clear();
        dotHorizontal.removeAllViews();
        List<MenuEntity> saveBeanList = SharedPreferencesUtils.readList(this, Constant.getInstance().saveName);
        menuEntities.clear();
        if (!saveBeanList.isEmpty()) {
            menuEntities.addAll(saveBeanList);
        }
        MenuEntity moreItemBean = new MenuEntity("tj", "添加编辑应用", "icon_add");
        menuEntities.add(moreItemBean);
        //计算viewpager一共显示几页
        int pageSize = menuEntities.size() % item_grid_num == 0
                ? menuEntities.size() / item_grid_num
                : menuEntities.size() / item_grid_num + 1;
        for (int i = 0; i < pageSize; i++) {
            GridView gridView = new GridView(this);
            gridView.setOnItemClickListener(this);
            GridViewAdapter adapter = new GridViewAdapter(this, menuEntities, i);
            gridView.setNumColumns(number_columns);
            gridView.setAdapter(adapter);
            gridList.add(gridView);
        }
        mAdapter.add(gridList);

        if (!gridList.isEmpty() && gridList.size()>1) {
            //设置圆点指示器
            viewPager.addOnPageChangeListener(new PageIndicator(this, dotHorizontal, gridList.size()));
            viewPager.setCurrentItem(0);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        int index = (viewPager.getCurrentItem() * 8) + position;
        MenuEntity menuEntity = menuEntities.get(index);
        switch (menuEntity.getTitle()) {
            case "添加编辑应用":
                startActivity(new Intent(this, MenuManageActivity.class));
                break;
            default:
                Toast.makeText(this, menuEntity.getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
