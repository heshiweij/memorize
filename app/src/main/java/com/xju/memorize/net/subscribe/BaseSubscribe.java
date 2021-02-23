package com.xju.memorize.net.subscribe;


import com.xju.memorize.net.tool.RetrofitFactory;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

public class BaseSubscribe {

    /* 登录 & 注册 */

    /**
     * 登录
     *
     * @param subscriber DisposableObserver
     */
    public static void login(Map<String, String> map, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = RetrofitFactory.getInstance().getHttpApi().login(map);
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

    /* 用户 */

    /* 单词 */

    /**
     * 词典分类列表
     *
     * @param subscriber DisposableObserver
     */
    public static void dictionaryCategory(DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = RetrofitFactory.getInstance().getHttpApi().dictionaryCategory();
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

}
