package com.xju.memorize.util;

import android.annotation.SuppressLint;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.xju.memorize.R;

public class GlideUtil {

    /**
     * 圆角 + 头像
     *
     * @return RequestOptions
     */
    @SuppressLint("CheckResult")
    public static RequestOptions portraitOptions() {
        new RequestOptions()
                .error(R.mipmap.ic_default_portrait)
                .placeholder(R.mipmap.ic_default_portrait)
                .fallback(R.mipmap.ic_default_portrait);
        return RequestOptions
                .bitmapTransform(new CircleCrop());
    }

    /**
     * 非圆角 + 新闻
     *
     * @return RequestOptions
     */
    @SuppressLint("CheckResult")
    public static RequestOptions newsOptions() {
        return new RequestOptions()
                .error(R.mipmap.ic_default)
                .placeholder(R.mipmap.ic_default)
                .fallback(R.mipmap.ic_default);
    }
}
