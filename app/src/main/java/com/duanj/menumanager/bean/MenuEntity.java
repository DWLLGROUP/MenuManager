package com.duanj.menumanager.bean;

import java.io.Serializable;

/**
 * Author Mr.Duan
 * Date 2019/7/15
 * Description:应用数据实体
 */
public class MenuEntity implements Serializable {
    private String id;
    private String title;
    private String ico;
    private String sort;
    private int  type ;//0棋牌类 1体育类 2动作类 3视频类
    private boolean select = false;

    public MenuEntity(String id, String title, String ico) {
        this.id = id;
        this.title = title;
        this.ico = ico;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
