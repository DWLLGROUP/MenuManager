package com.duanj.menumanager.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duanj.menumanager.R;
import com.duanj.menumanager.adapter.AllMenuAdapter;
import com.duanj.menumanager.adapter.SelectAdapter;
import com.duanj.menumanager.bean.Constant;
import com.duanj.menumanager.bean.MenuEntity;
import com.duanj.menumanager.bean.MenuSection;
import com.duanj.menumanager.util.FullyGridLayoutManager;
import com.duanj.menumanager.util.MyItemTouchCallBack;
import com.duanj.menumanager.util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author Mr.Duan
 * Date 2019/12/18
 * Description:功能管理界面
 */
public class MenuManageActivity extends AppCompatActivity implements SelectAdapter.OnAction,AllMenuAdapter.OnAction {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_operate)
    TextView tvOperate;
    @BindView(R.id.rl_operate)
    RelativeLayout rlOperate;
    @BindView(R.id.rv_select)
    RecyclerView rvSelect;
    @BindView(R.id.rv_un_select)
    RecyclerView rvUnSelect;

    private List<MenuEntity> selectMenus = new ArrayList<>();//本地已保存的功能（根据用户id读取）

    private List<MenuEntity> allMenus=new ArrayList<>();//本地所有功能应用

    private List<MenuSection> useAllMenus = new ArrayList<>();//匹配已经选择和未选择的功能

    private SelectAdapter selectAdapter = null;
    private AllMenuAdapter allMenuAdapter=null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_manage);
        ButterKnife.bind(this);
        initData();
        tvTitle.setText("全部应用");
        tvOperate.setText("编辑");
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallBack(selectMenus));
        itemTouchHelper.attachToRecyclerView(rvSelect);
        rvSelect.setLayoutManager(new FullyGridLayoutManager(this,4));
        selectAdapter = new SelectAdapter(R.layout.view_item, selectMenus, this, itemTouchHelper, this);
        rvSelect.setAdapter(selectAdapter);

        rvUnSelect.setLayoutManager(new FullyGridLayoutManager(this,4));
        allMenuAdapter = new AllMenuAdapter(R.layout.view_item, R.layout.title_item, useAllMenus,this,this);
        rvUnSelect.setAdapter(allMenuAdapter);

    }

    //获取数据
    public void initData(){
        //获取本版本所有的应用
        allMenus.addAll(Constant.getInstance().getAllMenus()) ;
        //根据用户名称读取本地本地保存的功能
        selectMenus.addAll( SharedPreferencesUtils.readList(this, Constant.getInstance().saveName));
        useAllMenus.add(new MenuSection(true, "棋牌类"));
        for (int i = 0; i < allMenus.size(); i++) {
            if (allMenus.get(i).getType()==0) {
                useAllMenus.add(new MenuSection(allMenus.get(i)));
            }
        }
        useAllMenus.add(new MenuSection(true, "体育类"));
        for (int i = 0; i < allMenus.size(); i++) {
            if (  allMenus.get(i).getType()==1) {
                useAllMenus.add(new MenuSection(allMenus.get(i)));
            }
        }
        useAllMenus.add(new MenuSection(true, "动作类"));
        for (int i = 0; i < allMenus.size(); i++) {
            if (  allMenus.get(i).getType()==2) {
                useAllMenus.add(new MenuSection(allMenus.get(i)));
            }
        }
        useAllMenus.add(new MenuSection(true, "视频类"));
        for (int i = 0; i < allMenus.size(); i++) {
            if (  allMenus.get(i).getType()==3) {
                useAllMenus.add(new MenuSection(allMenus.get(i)));
            }
        }
        resetAllMenu();
    }

    //此方法重新设置所有应用状态（已选或未选）:当selectMenus不为空时
    public void resetAllMenu(){
        if(!selectMenus.isEmpty()) {
            for (MenuEntity menuEntity:selectMenus) {
                for (MenuSection menuSection : useAllMenus) {
                    if (!menuSection.isHeader && (menuSection.t).getId().equals(menuEntity.getId())) {
                        (menuSection.t).setSelect(true);
                    }
                }
            }
        }
    }

    @OnClick(R.id.rl_operate)
    public void onViewClicked() {
        if (TextUtils.equals(tvOperate.getText().toString(), "编辑")) {
            tvOperate.setText("保存");
            selectAdapter.setEdit(true);
            allMenuAdapter.setEdit(true);
        } else if (TextUtils.equals(tvOperate.getText().toString(), "保存")) {
            tvOperate.setText("编辑");
            selectAdapter.setEdit(false);
            allMenuAdapter.setEdit(false);
            saveMenuEntity();
        }
        selectAdapter.notifyDataSetChanged();
        allMenuAdapter.notifyDataSetChanged();
    }

    public void saveMenuEntity(){
        SharedPreferencesUtils.saveList(this,Constant.getInstance().saveName,selectMenus);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        for (MenuEntity menuEntity:allMenus){
            menuEntity.setSelect(false);
        }
    }

    @Override
    public void doCancel(MenuEntity menuEntity) {
        selectMenus.remove(menuEntity);
        selectAdapter.notifyDataSetChanged();
        for (MenuSection menuSection:useAllMenus){
            if(!menuSection.isHeader&&(menuSection.t).getId().equals(menuEntity.getId())){
                (menuSection.t).setSelect(false);
            }
        }
        allMenuAdapter.notifyDataSetChanged();
    }

    @Override
    public void doSelect(MenuEntity menuEntity) {
        selectMenus.add(menuEntity);
        selectAdapter.notifyDataSetChanged();
        for (MenuSection menuSection:useAllMenus){
            if(!menuSection.isHeader&&(menuSection.t).getId().equals(menuEntity.getId())){
                (menuSection.t).setSelect(true);
            }
        }
        allMenuAdapter.notifyDataSetChanged();
    }
}
