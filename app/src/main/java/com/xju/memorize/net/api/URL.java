package com.xju.memorize.net.api;

public class URL {

    /**
     * Url 模式枚举
     */
    enum Type {
        // 生产
        PROD,
    }

    /**
     * Url 模式
     */
    private static final Type mType = Type.PROD;

    /**
     * 生产 URL
     */
    public static String BASE_PROD_URL = "http://memorize.lcd.cool";

    /**
     * 获取基础链接
     *
     * @return String
     */
    public static String getUrl() {
        switch (mType) {
            case PROD:
                return BASE_PROD_URL;
        }

        return BASE_PROD_URL;
    }

    /**
     * 是否为正式环境
     *
     * @return boolean
     */
    public static boolean isProd() {
        return mType == Type.PROD;
    }
}
