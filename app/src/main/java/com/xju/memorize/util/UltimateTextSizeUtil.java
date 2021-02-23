package com.xju.memorize.util;/**
 * Created by Administrator on 2016/10/27.
 */

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;


public class UltimateTextSizeUtil {

    public static SpannableString getLastOneMinString(String rate, int textSize) {
        SpannableString priceSpannableStr = new SpannableString(rate);

        priceSpannableStr.setSpan(new AbsoluteSizeSpan(textSize, true), rate.length() - 1,
                rate.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return priceSpannableStr;
    }

    /**
     * @param text
     * @param emphasizeText
     * @param textSize
     * @param color
     * @param textStyle     Typeface
     * @return
     */
    public static SpannableString getEmphasizedSpannableString(String text, String emphasizeText,
                                                               int textSize, int color, int textStyle) {
        String[] empasize = new String[1];
        empasize[0] = emphasizeText;
        return getEmphasizedSpannableString(text, empasize, textSize, color, textStyle);
    }

    public static SpannableString getEmphasizedSpannableString(String text, String[] emphasizeTexts,
                                                               int textSize, int color, int textStyle) {

        SpannableString spannableString = new SpannableString(text);
        String emphasizeText;
        int start = -1, end = 0;
        try {
            for (int i = 0; i < emphasizeTexts.length; i++) {
                emphasizeText = emphasizeTexts[i];
                if (!text.contains(emphasizeText)) {
                    break;
                }
                //从上一个的结束开始搜 否则如果有重复的就会有bug
                start = text.indexOf(emphasizeText, end);

                end = start + emphasizeText.length();

                if (start == -1) {
                    break;
                }
                //必须得每次都new一遍 否则只有一个起效
                if (color != 0) {
                    spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                if (textSize != 0) {
                    spannableString.setSpan(new AbsoluteSizeSpan(textSize, true), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                if (textStyle != 0) {
                    spannableString.setSpan(new StyleSpan(textStyle), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            return spannableString;
        } catch (Exception e) {
            return spannableString;
        }
    }
}
