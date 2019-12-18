package com.duanj.menumanager.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Author Mr.Duan
 * Date 2019/7/18
 * Description:分层信息
 */
public class MenuSection extends SectionEntity<MenuEntity> {
    public MenuSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public MenuSection(MenuEntity menuEntity) {
        super(menuEntity);
    }
}
