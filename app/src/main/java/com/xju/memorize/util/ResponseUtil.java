package com.xju.memorize.util;

/**
 * 处理响应数据 - 工具类
 */
public class ResponseUtil {

//    /**
//     * 处理金额，保留两位小数
//     *
//     * @return String
//     */
//    @SuppressLint("DefaultLocale")
//    public static String transformMoney(String value) {
//        String defValue = "0.00";
//
//        if (value == null || TextUtils.isEmpty(value)) {
//            return defValue;
//        }
//
//        try {
//            double penny = Double.parseDouble(value);
//            return String.format("%.2f", penny / 100);
//        } catch (Exception e) {
//            return defValue;
//        }
//    }


//    /**
//     * 处理普通字符串
//     *
//     * @param value    String
//     * @param defValue String
//     * @return String
//     */
//    public static String transformString(String value, String defValue) {
//        if (value == null || TextUtils.isEmpty(value)) {
//            return defValue;
//        }
//
//        return value;
//    }

//    /**
//     * 处理普通字符串
//     *
//     * @param value String
//     * @return String
//     */
//    public static String transformString(String value) {
//        String defValue = "";
//        if (value == null || TextUtils.isEmpty(value)) {
//            return defValue;
//        }
//
//        return value;
//    }

//    /**
//     * 处理整数
//     *
//     * @param value    String
//     * @param defValue String
//     * @return String
//     */
//    public static String transformInt(String value, String defValue) {
//        if (value == null || TextUtils.isEmpty(value)) {
//            return defValue;
//        }
//
//        try {
//            return String.format("%s", Integer.parseInt(value));
//        } catch (Exception e) {
//            return defValue;
//        }
//    }

//    /**
//     * 处理整数
//     *
//     * @param value String
//     * @return String
//     */
//    public static String transformInt(String value) {
//        String defValue = "0";
//        if (value == null || TextUtils.isEmpty(value)) {
//            return "0";
//        }
//
//        try {
//            return String.format("%s", Integer.parseInt(value));
//        } catch (Exception e) {
//            return defValue;
//        }
//    }

}
