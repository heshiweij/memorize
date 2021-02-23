/*
 * Copyright (C) 2012 Brandon Tate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xju.memorize.component.web;

/**
 * Text Selection Listener Interface
 *
 * @author Brandon Tate
 */
public interface JavaScriptCallBackListener {
    //去某个页面
    void toPage(String activityName);

    //聚信立成功回调
    void routeSuccess();

    //聚信立失败回调
    void routeFail(String msg);

    //支付完成
    void payResult();

    //需要登录
    void goToLogin(String string);

    //微信分享
    void verifyResult();

    //提现
    void closeHtml();
}
