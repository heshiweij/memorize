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
    @POST("/v1/login")
    Observable<ResponseBody> login(@Body Map<String, String> map);

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

}
