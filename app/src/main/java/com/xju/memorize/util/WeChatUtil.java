package com.xju.memorize.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
//import com.tencent.mm.opensdk.modelmsg.WXImageObject;
//import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
//import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

/***********微信分享破解类************/
public class WeChatUtil {
    public static String APP_ID_WX;//自己在微信开发平台申请的APP ID
    public static String APP_PACKAGE;//自己APP的包名

    public static Context context = null;
    public static String[] weixin_package_name = {
            "com.UCMobile",//ucweb
            "com.tencent.mtt",//QQ浏览器
            "com.baidu.browser.apps",//百度浏览器
            "com.ijinshan.browser_fast",//猎豹浏览器
            "com.browser2345",//2345手机浏览器
            "sogou.mobile.explorer",//搜狗浏览器
            "com.qihoo.browser",//360浏览器
            "com.tencent.mobileqq",//QQ
            "com.oupeng.mini.android",//欧朋浏览器
            "com.roboo.explorer",//4G浏览器
            "com.baidu.BaiduMap",//百度地图
            "com.baidu.haokan",//好看视频
            "com.lianjia.beike",//贝壳找房
            "com.qihoo.cleandroid_cn",//360清理大师
            "com.qihoo.gameunion",//360游戏大厅
            "com.shuqi.controller",//书旗小说
            "com.shyz.toutiao",//清理大师
            "com.smile.gifmaker",//快手
            "com.tencent.qqlive",//腾讯视频
            "qsbk.app",//糗事百科
            "dopool.player",//手机电视
            "cn.wiz.note",//为知笔记
            "com.huajiao",//花椒
            "com.hunantv.imgo.activity",//芒果TV
            "com.sina.weibo",//微博
            "com.youku.phone",//优酷视频
            "com.baidu.searchbox.lite"//百度极速
    };

    public static String[] weixin_package_appid = {
            "wx020a535dccd46c11",//ucweb
            "wx64f9cf5b17af074d",//QQ浏览器
            "wx0116bf301a92506b",//百度浏览器
            "wxc2ff198ba4a63f06",//猎豹浏览器
            "wx66d367303dace3ad",//2345手机浏览器
            "wxf1d5d36b9ea492f8",//搜狗浏览器
            "wx60d9d5c44ca9386e",//360浏览器
            "wxf0a80d0ac2e82aa7",//QQ
            "wxbf664df065a3cb31",//欧朋浏览器
            "wx7db8d6a85cd5de7b",//4G浏览器
            "wx9a08a4f59ce91bf6",//百度地图
            "wx89e0af9d1faca777",//好看视频
            "wx82cc4b304cdf58c5",//贝壳找房
            "wxb275a8e29e1b678b",//360清理大师
            "wxd6a16633f973966f",//360游戏大厅
            "wx5afebbb7286b03f5",//书旗小说
            "wx0168f5886d461c4b",//清理大师
            "wxaadbab9d13edff20",//快手
            "wxca942bbff22e0e51",//腾讯视频
            "wxfa4ebf0331513c10",//糗事百科
            "wx903a16f5ed5f9510",//手机电视
            "wx14620681fe8ea00d",//为知笔记
            "wxe93e379fc8c9220f",//花椒
            "wxbbc6e0adf8944632",//芒果TV
            "wx299208e619de7026",//微博
            "wxa77232e51741dee3",//优酷视频
            "wx6d2ad6ce3e1cd86e"//百度极速
    };
    public static int random_index;

//    //Android判断是否安装某App
//    public static boolean isAvilible(Context context, String packageName){
//        final PackageManager packageManager = context.getPackageManager();//获取packagemanager
//        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息
//        List<String> pName = new ArrayList<String>();//用于存储所有已安装程序的包名
//        //从pinfo中将包名字逐一取出，压入pName list中
//        if(pinfo != null){
//            for(int i = 0; i < pinfo.size(); i++){
//                String pn = pinfo.get(i).packageName;
//                pName.add(pn);
//            }
//        }
//        return pName.contains(packageName);//判断pName中是否有目标程序的包名，有TRUE，没有FALSE
//    }

    public static List<String> installList = new ArrayList<String>();//用于存储所有已安装程序的包名

    //Android得到APP安装应用列表
    public static void getInstallList(Context context_params, String appid, String apppackage) {
        if (context == null) {
            context = context_params;
            APP_ID_WX = appid;
            APP_PACKAGE = apppackage;

            installList.clear();
            final PackageManager packageManager = context.getPackageManager();//获取packagemanager
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息
            //从pinfo中将包名字逐一取出，压入pName list中
            if (pinfo != null) {
                for (int i = 0; i < pinfo.size(); i++) {
                    String pn = pinfo.get(i).packageName;
                    installList.add(pn);
                }
            }
        }
    }

    //Android判断是否安装某App
    public static boolean isInstallApp(String packageName) {
        if (installList != null) {
            return installList.contains(packageName);//判断pName中是否有目标程序的包名，有TRUE，没有FALSE
        }
        return false;
    }


    //微信分享漏洞破解
    public static void shareWxOrCircle(SendMessageToWX.Req paramReq) {
        try {
            String wxAppId = APP_ID_WX;//如果全部没有用自己的
            String appPackage = APP_PACKAGE;//如果全部没有用自己的
            List<String> index_list = new ArrayList<String>();
            for (int i = 0; i < weixin_package_name.length; i++) {
                String package_name = weixin_package_name[i];
                boolean isinstall = isInstallApp(package_name);
                if (isinstall) {
                    index_list.add(i + "");
                }
            }

            if (index_list.size() > 0) {
                random_index = getRandomNext(index_list.size());
                wxAppId = weixin_package_appid[Integer.parseInt(index_list.get(random_index))];
                appPackage = weixin_package_name[Integer.parseInt(index_list.get(random_index))];
            }

            Bundle bunlde = new Bundle();
            paramReq.toBundle(bunlde);
            String str2 = "weixin://sendreq?appid=" + wxAppId;
            Intent intent = new Intent().setClassName("com.tencent.mm", "com.tencent.mm.plugin.base.stub.WXEntryActivity");
            intent.putExtras(bunlde);

            intent.putExtra("_mmessage_sdkVersion", 570490883);
            intent.putExtra("_mmessage_appPackage", appPackage);
            intent.putExtra("_mmessage_content", str2);
            String buffer = str2 +
                    570490883 +
                    appPackage +
                    "mMcShCsTr";
            intent.putExtra("_mmessage_checksum", e(buffer.substring(1, 9).getBytes()).getBytes());

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final String e(byte abyte0[]) throws Exception {
        char ac[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'
        };
        char ac1[];
        MessageDigest messagedigest;
        (messagedigest = MessageDigest.getInstance("MD5")).update(abyte0);
        int i;
        ac1 = new char[(i = (abyte0 = messagedigest.digest()).length) * 2];
        int j = 0;
        for (int k = 0; k < i; k++) {
            byte byte0 = abyte0[k];
            ac1[j++] = ac[byte0 >>> 4 & 0xf];
            ac1[j++] = ac[byte0 & 0xf];
        }

        return new String(ac1);
    }

    /**
     * 生成一个0 到 endNum 之间的随机数
     *
     * @param endNum
     * @return
     */
    public static int getRandomNext(int endNum) {
        if (endNum > 0) {
            Random random = new Random();
            return Math.abs(random.nextInt(endNum));
        }
        return 0;
    }


    /********分享链接操作方法
     url：分享链接
     flag:1代表分享微信好友，2代表分享微信朋友圈
     title：标题
     content:摘要
     bitmap：分享icon
     *******/
    public static void shareAppURL(final String url, final String flag, final String title, final String content, String icon) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(icon);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(fis);

        if (flag.equals("2")) {//微信好友
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = url;


            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = title;
            msg.description = content;


            msg.setThumbImage(bitmap);
            req.message = msg;
            req.scene = SendMessageToWX.Req.WXSceneSession;//微信好友
            WeChatUtil.shareWxOrCircle(req);
        } else if (flag.equals("1")) {//微信朋友圈
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = url;


            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = title;
            msg.description = content;

            msg.setThumbImage(bitmap);
            req.message = msg;
            req.scene = SendMessageToWX.Req.WXSceneTimeline;//微信朋友圈
            WeChatUtil.shareWxOrCircle(req);
        }


    }

    /********分享链接操作方法
     url：分享链接
     flag:1代表分享微信好友，2代表分享微信朋友圈
     title：标题
     content:摘要
     bitmap：分享icon
     *******/
    public static void shareAppURL(final String url, final String flag, final String title, final String content, Bitmap iconBitmap) {
        if (flag.equals("2")) {//微信好友
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = url;


            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = title;
            msg.description = content;


            msg.setThumbImage(iconBitmap);
            req.message = msg;
            req.scene = SendMessageToWX.Req.WXSceneSession;//微信好友
            WeChatUtil.shareWxOrCircle(req);
        } else if (flag.equals("1")) {//微信朋友圈
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = url;


            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = title;
            msg.description = content;

            msg.setThumbImage(iconBitmap);
            req.message = msg;
            req.scene = SendMessageToWX.Req.WXSceneTimeline;//微信朋友圈
            WeChatUtil.shareWxOrCircle(req);
        }


    }


    /*********分享图片操作方法
     flag:1代表分享微信好友，2代表分享微信朋友圈
     bitmap：分享图bitmap
     ************/
    public static void shareAppPic(final String flag, String icon) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(icon);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(fis);

        if (flag.equals("2")) {
            WXImageObject imgObj = new WXImageObject(bitmap);
            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = imgObj;
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = SendMessageToWX.Req.WXSceneSession;//微信好友
            shareWxOrCircle(req);

        } else if (flag.equals("1")) {
            WXImageObject imgObj = new WXImageObject(bitmap);
            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = imgObj;
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = SendMessageToWX.Req.WXSceneTimeline;//微信朋友圈
            shareWxOrCircle(req);
        }
    }


}
