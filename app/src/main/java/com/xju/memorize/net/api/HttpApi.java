package com.xju.memorize.net.api;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * API
 */
public interface HttpApi {

    /**
     * 登录
     *
     * @param map Map
     * @return Observable
     */
    @POST("/users/login")
    Observable<ResponseBody> login(@Body Map<String, String> map);

    /**
     * 用户信息
     *
     * @return Observable
     */
    @GET("/users/userinfo")
    Observable<ResponseBody> userinfo();

    /**
     * 词典分类列表
     *
     * @return Observable
     */
    @GET("/dictionaries/categories")
    Observable<ResponseBody> dictionaryCategory();

    /**
     * 词典单词列表
     *
     * @return Observable
     */
    @GET("/dictionaries/vocabularies/{category_id}")
    Observable<ResponseBody> dictionaryVocabulary(@Path("category_id") String categoryId);

    /**
     * 获取用户单词列表
     *
     * @return Observable
     */
    @GET("/dictionaries/users/vocabularies")
    Observable<ResponseBody> userVocabulary();

    /**
     * 创建用户单词列表
     *
     * @return Observable
     */
    @POST("/dictionaries/users/vocabularies")
    Observable<ResponseBody> createUserVocabulary(@Body Map<String, String> map);

    /**
     * 获取用户错词列表
     *
     * @return Observable
     */
    @GET("/dictionaries/users/wrong_vocabularies")
    Observable<ResponseBody> userWrongVocabulary();

    /**
     * 创建用户错词列表
     *
     * @return Observable
     */
    @POST("/dictionaries/users/wrong_vocabularies")
    Observable<ResponseBody> createUserWrongVocabulary(@Body Map<String, String> map);
}
