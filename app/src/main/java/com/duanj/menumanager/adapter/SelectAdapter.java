package com.duanj.menumanager.adapter;

import android.content.Context;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.duanj.menumanager.R;
import com.duanj.menumanager.bean.MenuEntity;
import com.duanj.menumanager.ui.MenuManageActivity;


import java.util.List;

/**
 * Author Mr.Duan
 * Date 2019/7/15
 * Description:选中可拖动应用适配器
 */
public class SelectAdapter extends BaseQuickAdapter<MenuEntity,BaseViewHolder> {
    private OnAction onAction;
    private Context context;
    private boolean isEdit = false;
    private List<MenuEntity> data;
    private ItemTouchHelper itemTouchHelper;

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public SelectAdapter(int layoutResId, @Nullable List<MenuEntity> data, Context mContext, ItemTouchHelper itemTouchHelper, OnAction onAction) {
        super(layoutResId, data);
        this.context = mContext;
        this.data = data;
        this.onAction = onAction;
        this.itemTouchHelper = itemTouchHelper;
    }

    @Override
    protected void convert(final BaseViewHolder helper,final MenuEntity menuEntity) {
        int drawableId =context.getResources().getIdentifier(menuEntity.getIco(),"drawable", context.getPackageName());
        helper.setBackgroundRes(R.id.iv_icon,drawableId);
        helper.setText(R.id.tv_name,menuEntity.getTitle());
        helper.setImageResource(R.id.iv_flag,R.drawable.msg_gouxus);
        if (isEdit) {
            helper.setGone(R.id.iv_flag, true);
        } else {
            helper.setGone(R.id.iv_flag, false);
        }


        helper.getView(R.id.rl_root).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (isEdit) {
                    itemTouchHelper.startDrag(helper);
                    Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
                    vibrator.vibrate(100);
                    return true;
                }
                return false;
            }
        });


        helper.getView(R.id.iv_flag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuEntity.setSelect(false);
                onAction.doCancel(menuEntity);
            }
        });


    }

    public interface OnAction {
        void doCancel(MenuEntity menuEntity);
    }
}
