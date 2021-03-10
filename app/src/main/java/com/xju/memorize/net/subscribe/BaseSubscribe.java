package com.xju.memorize.net.subscribe;


import com.xju.memorize.net.tool.RetrofitFactory;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

public class BaseSubscribe {

    /**
     * 登录
     *
     * @param subscriber DisposableObserver
     */
    public static void login(Map<String, String> map, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = RetrofitFactory.getInstance().getHttpApi().login(map);
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 用户信息
     *
     * @param subscriber DisposableObserver
     */
    public static void userinfo(DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = RetrofitFactory.getInstance().getHttpApi().userinfo();
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 词典分类列表
     *
     * @param subscriber DisposableObserver
     */
    public static void dictionaryCategory(DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = RetrofitFactory.getInstance().getHttpApi().dictionaryCategory();
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 词典单词列表
     *
     * @param subscriber DisposableObserver
     */
    public static void dictionaryVocabulary(String categoryId, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = RetrofitFactory.getInstance().getHttpApi().dictionaryVocabulary(categoryId);
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 获取用户单词列表
     *
     * @param subscriber DisposableObserver
     */
    public static void userVocabulary(DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = RetrofitFactory.getInstance().getHttpApi().userVocabulary();
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 创建用户单词列表
     *
     * @param subscriber DisposableObserver
     */
    public static void createUserVocabulary(Map<String, String> map, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = RetrofitFactory.getInstance().getHttpApi().createUserVocabulary(map);
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 获取用户错词列表
     *
     * @param subscriber DisposableObserver
     */
    public static void userWrongVocabulary(DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = RetrofitFactory.getInstance().getHttpApi().userWrongVocabulary();
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

    /**
     * 创建用户错词列表
     *
     * @param subscriber DisposableObserver
     */
    public static void createUserWrongVocabulary(Map<String, String> map, DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = RetrofitFactory.getInstance().getHttpApi().createUserWrongVocabulary(map);
        RetrofitFactory.getInstance().toSubscribe(observable, subscriber);
    }

}
