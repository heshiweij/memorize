package com.xju.memorize.util;

/**
 * 数学计算工具
 */
public class MathUtil {

    /**
     * 随机数
     *
     * @return int
     */
    public static int random(int min, int max) {
        return (int) (min + Math.random() * (max - min + 1));
    }

}
