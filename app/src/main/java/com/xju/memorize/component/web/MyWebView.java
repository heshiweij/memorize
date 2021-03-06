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

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * WebView subclass that hijacks web content selection.
 *
 * @author Brandon Tate
 */
public class MyWebView extends WebView implements View.OnLongClickListener{
    /**
     * Context.
     */
    protected Context mContext;
    private OnPeanutWebViewListener onPeanutWebViewListener;
    private OnWebViewLongClickListener onWebViewLongClickListener;
    private OnUrlChangeListener onUrlChangeListener;

    public MyWebView(Context context) {
        super(context);
        mContext = context;
        init(context);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init(context);

    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }


    /**
     * Setups up the web view.
     *
     * @param context
     */
    protected void init(Context context) {
        setOnLongClickListener(this);
        // Webview init
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //????????????
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccess(true);// ??????????????????????????????
        webSettings.setBuiltInZoomControls(true);// ?????????
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);


        buildDrawingCache(true);
        setDrawingCacheEnabled(true);


        // Webview client.
        setWebViewClient(new WebViewClient() {
            // This is how it is supposed to work, so I'll leave it in, but this
            // doesn't get called on pinch
            // So for now I have to use deprecated getScale method.
            @Override
            public void onScaleChanged(WebView view, float oldScale,
                                       float newScale) {
                //?????????????????????
                super.onScaleChanged(view, oldScale, newScale);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                try {
                    // Otherwise allow the OS to handle things like tel, mailto, etc.
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    mContext.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (null != onUrlChangeListener) {
                    onUrlChangeListener.onUrlChange(url);
                }
                // ??????????????????????????????,?????????????????????????????????????????????
                super.onPageFinished(view, url);
            }
        });
        setWebChromeClient(new MyWebChromeClient());


//        setOnKeyListener(new OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if ((keyCode == KeyEvent.KEYCODE_BACK) && canGoBack()) {
//                    goBack(); // goBack()????????????WebView???????????????
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });
    }

    public void setOnPeanutWebViewListener(OnPeanutWebViewListener onPeanutWebViewListener) {
        this.onPeanutWebViewListener = onPeanutWebViewListener;
    }

    @Override
    public boolean onLongClick(View v) {
        HitTestResult result = ((WebView) v).getHitTestResult();
        int type = result.getType();
        if (type == HitTestResult.IMAGE_TYPE) {
            onWebViewLongClickListener.onImgLongClick(result.getExtra());
            return true;
        }
        return false;
    }

    public void setOnWebViewLongClickListener(OnWebViewLongClickListener onWebViewLongClickListener) {
        this.onWebViewLongClickListener = onWebViewLongClickListener;
    }

    public void setOnUrlChangeListener(OnUrlChangeListener onUrlChangeListener) {
        this.onUrlChangeListener = onUrlChangeListener;
    }

    /***
     * webChromeClient????????????????????????????????????????????????????????????????????????
     * ????????????????????????javascript????????????????????????????????????????????????????????????
     * ???javascript????????????????????????android????????????
     * ????????????????????????onJsAlert???????????????????????????????????????alert???????????????
     * ??????????????????????????????????????????Toast????????????????????????
     */
    class MyWebChromeClient extends WebChromeClient {

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // ??????????????????
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (null != onPeanutWebViewListener) {
                onPeanutWebViewListener.onReceivedTitle(title);
            }
        }
    }

    public interface OnPeanutWebViewListener {
        void onReceivedTitle(String title);
    }
    public interface OnWebViewLongClickListener {
        void onImgLongClick(String imgUrl);
    }
}
