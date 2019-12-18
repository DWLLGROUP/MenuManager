package com.duanj.menumanager.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import com.duanj.menumanager.bean.Constant;
import com.duanj.menumanager.bean.MenuEntity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author Mr.Duan
 * Date 2019/12/18
 * Description:初始化功能数据
 */
public class LoadTask {
    public Context context;
    public String fileName;

    public LoadTask(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
        new LoadMenuTask().execute();
    }

    /*
            * 异步任务执行对功能应用的加载初始化
            * */
    public class LoadMenuTask extends AsyncTask<String, Void, List<MenuEntity>> {
        //上面的方法中，第一个参数：用户权限，第二个参数的包装类：进度的刻度，第三个参数：任务执行的返回结果
        @Override
        //在界面上显示进度条
        protected void onPreExecute() {
        };
        protected List<MenuEntity> doInBackground(String... params) {  //三个点，代表可变参数
            //如有需求可以根据用户权限修改相关的值
            List<MenuEntity> menus=new ArrayList<>();
            try {

                String strByJson=getJson(context,fileName);
                //Json的解析类对象
                JsonParser parser = new JsonParser();
                //将JSON的String 转成一个JsonArray对象
                JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();
                Gson gson = new Gson();
                //加强for循环遍历JsonArray
                for (JsonElement indexArr : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    MenuEntity menuEntity = gson.fromJson(indexArr, MenuEntity.class);
                    menus.add(menuEntity);
                }


            }  catch (Exception e) {
                e.printStackTrace();
            }
            return menus;
        }
        //主要是更新UI
        @Override
        protected void onPostExecute(List<MenuEntity> menus) {
            super.onPostExecute(menus);
            Constant.getInstance().setAllMenus(menus);
        }
    }

    //获取json数据（本地）
    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Error",e.toString());
        }
        return stringBuilder.toString();
    }
}
