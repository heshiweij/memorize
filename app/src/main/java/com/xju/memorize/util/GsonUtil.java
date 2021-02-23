package com.xju.memorize.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

/**
 * 类描述：项目的util工具 gson解析
 */
public class GsonUtil {
    public static Gson gson = new Gson();
    /**
     * 说明：如果解析抛异常返回null
     * @param result 要解析的json字符串
     * @param clazz 对应的javabean的字节码
     * @return 返回 对应的javabean 对象
     */
    public static <T> T fromJson(String result, Class<T> clazz){
        try{
            if(gson==null){
                gson = new Gson();
            }
            return gson.fromJson(result, clazz);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * json字符串转成对象
     * @param str
     * @param type
     * @return
     */
    public static <T> T fromJson(String str, Type type) {
        if(gson==null){
            gson = new Gson();
        }
        return gson.fromJson(str, type);
    }


    public static String toJson(Object obj){
        if(null == gson){
            gson = new Gson();
        }
        return gson.toJson(obj);
    }

    public String getJson(Context context, String fileName) {

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
        }
        return stringBuilder.toString();
    }
}
