package com.duanj.menumanager.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import com.duanj.menumanager.bean.MenuEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Author Mr.Duan
 * Date 2019/7/9
 * Description:SP文件的工具类（用于保存用户相关信息及设置）
 */

public class SharedPreferencesUtils {

    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_date";


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context, String key, Object object) {

        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }

        editor.commit();
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(Context context, String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * @param object
     */
    public static void saveObject(Context context, String key, Object object) throws Exception {
        if (object instanceof Serializable) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(object);//把对象写到流里
                String temp = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
                editor.putString(key, temp);
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new Exception("User must implements Serializable");
        }
    }

    public static Object getObject(Context context, String key) {
        SharedPreferences sharedPreferences=context.getSharedPreferences(FILE_NAME,context.MODE_PRIVATE);
        String temp = sharedPreferences.getString(key, "");
        ByteArrayInputStream bais =  new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));
        Object object = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            object= ois.readObject();
        } catch (IOException e) {
        }catch(ClassNotFoundException e1) {

        }
        return object;
    }

    public static void  deleteObject(Context context, String key) {
        SharedPreferences sharedPreferences=context.getSharedPreferences(FILE_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 保存list的数据
     * @param context
     * @param key
     * @param datas
     */
    public static void saveList(Context context, String key, List<MenuEntity> datas){

        SharedPreferences.Editor editor = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(datas);
        //Log.d("要保存的数据", "saved json is "+ json);
        editor.putString(key, json);
        editor.commit();

    }
    /**
     * 读取list(json)
     * @param context
     * @return
     */
    public static List<MenuEntity> readList(Context context, String key){
        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);
        String json =preferences.getString(key, null);
        List<MenuEntity> datas = new ArrayList<>();
        if (json != null)
        {
            Gson gson = new Gson();
            Type type = new TypeToken<List<MenuEntity>>(){}.getType();
            datas = gson.fromJson(json, type);
        }
        return datas;
    }

    public static void  deleteList(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor  editor = preferences.edit();
        editor.remove(key);
        editor.clear();
        editor.commit();
    }
}
