package com.duanj.menumanager.bean;


import java.util.List;

/**
 * Author Mr.Duan
 * Date 2019/7/9
 * Description:单例模式获取公共数据
 */

public class Constant {
    public final String saveName="menus";
    public List<MenuEntity> allMenus;//本版本所有的功能数据

    public static Constant constant;

    public static Constant getInstance() {
        if (constant == null) {
            synchronized (Constant.class) {
                if (constant == null) {
                    constant = new Constant();
                }
            }
        }
        return constant;
    }

    public List<MenuEntity> getAllMenus() {
        return allMenus;
    }

    public void setAllMenus(List<MenuEntity> allMenus) {
        this.allMenus = allMenus;
    }
}