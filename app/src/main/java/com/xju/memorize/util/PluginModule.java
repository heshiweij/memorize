package com.xju.memorize.util;

import android.content.Context;

import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;

public class PluginModule extends WXSDKEngine.DestroyableModule {
    @Override
    public void destroy() {

    }

    @JSMethod(uiThread = true)
    public void shareAppURL(Context context, String appid, String packageName, String url, String flag, String title, String content, String icon, JSCallback callback)
    {
        //            String id = object.getString("appid");
//            String packageName=object.getString("apppackage");
//            String url = object.getString("url");
//            String flag = object.getString("flag");
//            String title = object.getString("title");
//            String content = object.getString("content");
//            String icon = object.getString("icon");
        WeChatUtil.getInstallList(context,appid,packageName);
        WeChatUtil.shareAppURL(url, flag, title, content, icon);

    }
//    @JSMethod(uiThread = true)
//    public void shareAppPic(JSONObject object, JSCallback callback)
//    {
//        String id=object.getString("appid");
//        String packageName=object.getString("apppackage");
//        String flag = object.getString("flag");
//        String icon = object.getString("icon");
//        WeChatUtil.getInstallList(mWXSDKInstance.getContext(),id,packageName);
//        WeChatUtil.shareAppPic(flag,icon);
//    }
}
