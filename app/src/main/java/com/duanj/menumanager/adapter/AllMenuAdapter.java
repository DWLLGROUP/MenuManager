package com.duanj.menumanager.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.duanj.menumanager.R;
import com.duanj.menumanager.bean.MenuEntity;
import com.duanj.menumanager.bean.MenuSection;

import java.util.List;

/**
 * Author Mr.Duan
 * Date 2019/7/18
 * Description:所有应用
 */
public class AllMenuAdapter extends BaseSectionQuickAdapter<MenuSection, BaseViewHolder> {
    private Context context;
    private boolean isEdit = false;
    private OnAction onAction;

    public void setEdit(boolean edit) {
        isEdit = edit;
    }
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public AllMenuAdapter(int layoutResId, int sectionHeadResId, List<MenuSection> data , Context context, OnAction onAction) {
        super(layoutResId, sectionHeadResId, data);
        this.context=context;
        this.onAction = onAction;
    }

    @Override
    protected void convertHead(BaseViewHolder helper, MenuSection item) {
        helper.setText(R.id.tv_item_title,item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuSection item) {
        final MenuEntity menuEntity=item.t;
        //获取资源图片
        int drawableId =context.getResources().getIdentifier(menuEntity.getIco(),"drawable", context.getPackageName());
        helper.setBackgroundRes(R.id.iv_icon,drawableId);
        helper.setText(R.id.tv_name,menuEntity.getTitle());
        if(menuEntity.isSelect()){//判断是否已经被选中
            helper.setImageResource(R.id.iv_flag, R.drawable.msg_gouxuh);
        }else {
            helper.setImageResource(R.id.iv_flag, R.drawable.msg_gouxul);
        }
        if (isEdit) {//判断是否处于编辑状态
            helper.setGone(R.id.iv_flag, true);
            helper.setBackgroundColor(R.id.rl_root,context.getResources().getColor(R.color.whiteLight));
        } else {
            helper.setGone(R.id.iv_flag, false);
            helper.setBackgroundColor(R.id.rl_root,context.getResources().getColor(R.color.colorWhite));
        }
        //设置点击添加
        helper.getView(R.id.iv_flag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!menuEntity.isSelect()) {
                    menuEntity.setSelect(true);
                    onAction.doSelect(menuEntity);
                }
            }
        });
    }

    public interface OnAction {
        void doSelect(MenuEntity menuEntity);
    }
}
