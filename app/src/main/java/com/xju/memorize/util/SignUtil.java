package com.xju.memorize.util;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class SignUtil {
    private static SignUtil instance;

    private SignUtil() {

    }

    public static SignUtil getInstance() {
        if (instance == null)
            instance = new SignUtil();

        return instance;
    }


    public String signNameValue(HashMap<String, String> map, String deskey) {
        if (map == null) {
            throw new NullPointerException(
                    "代签名键值对为空！( 详情见hkrt.icardpay.Utils.Sign.signNameValue() )");
        }
        String str = nameValuePairsToStr(map);
        return MD5Util.getMD5String(str + deskey);
    }

    public String signNameValue(HashMap<String, String> map) {
        if (map == null)
            throw new NullPointerException(
                    "代签名键值对为空！( 详情见hkrt.icardpay.Utils.Sign.signNameValue() )");
        String str = nameValuePairsToStr(map);

        return MD5Util.getMD5String(str);
    }


    /**
     * @param params
     * @return
     */
    public String signCheckNameValue(TreeMap<String, Object> params) {
        if (params == null)
            throw new NullPointerException(
                    "代签名键值对为空！( 详情见hkrt.icardpay.Utils.Sign.signNameValue() )");
        String str = nameValuePairsToStr(params);
        return MD5Util.getMD5String(str);
    }

    public String nameValuePairsToStr(TreeMap<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        if (params == null)
            return null;
        for (Entry<String, Object> entry : params.entrySet()) {
            sb.append(entry.getValue());
        }
        return sb.toString();
    }


    /**
     * 对hashmap排序
     *
     * @param map
     * @return
     */
    public String nameValuePairsToStr(HashMap<String, String> map) {
        StringBuilder sb = new StringBuilder();
        if (map == null)
            return null;
        List<Entry<String, String>> list = new ArrayList<Entry<String, String>>(
                map.entrySet());
        Collections.sort(list, new Comparator<Entry<String, String>>() {

            @Override
            public int compare(Entry<String, String> arg0,
                               Entry<String, String> arg1) {
                return arg0.getKey().compareTo(arg1.getKey());
            }

        });

        for (Entry<String, String> listMap : list) {
            /** 修改了下,空字符串不参与签名 */
            String res = "";
            if (null != listMap.getValue() && !"sign".equals(listMap.getKey())) {
                res = listMap.getValue();
//                res = res.replaceAll("\r|\n", "");
//                res= res.replaceAll(" ", "");
                sb.append(res); // 这里去掉空格了。
            }
            try {
                Log.i("ts", "key:" + listMap.getKey().trim() + "\nvalue:" + listMap.getValue().trim());
            } catch (NullPointerException e) {
            }
        }
        return sb.toString();
    }



    /**
     * 获取指定编码字符集字符串的byte数组
     *
     * @param content 字符串
     * @param charset 编码字符集
     * @return 返回字符串的byte数组
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    protected static byte[] getContentBytes(String content, String charset)
            throws UnsupportedEncodingException {
        if (charset != null && charset.trim().length() != 0) {
            return content.getBytes();
        }
        return content.getBytes(charset);
    }
}
