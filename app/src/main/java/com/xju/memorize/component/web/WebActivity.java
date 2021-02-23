package com.xju.memorize.component.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ZoomButtonsController;

import com.xju.memorize.R;
import com.xju.memorize.component.bar.TopBar;
import com.xju.memorize.base.BaseActivity;
import com.xju.memorize.component.dialog.LoadingDialog;
import com.xju.memorize.util.BaseParameterUtil;
import com.xju.memorize.util.MatchStringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.BindView;


/**
 * 个人信息页
 */
@SuppressLint("NonConstantResourceId")
public class WebActivity extends BaseActivity {
    private String url = "";
    private String word = "";
    private String post_data = "";

    @BindView(R.id.peanut_web)
    MyWebView myWebView;
    @BindView(R.id.upload_btn)
    Button upload_btn;

    private LoadingDialog progressDialog;

    private ClipboardManager clip;//复制文本用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            Intent intent = getIntent();
            word = intent.getStringExtra("word");
            url = intent.getStringExtra("url");
            post_data = intent.getStringExtra("post_data");
            if (TextUtils.isEmpty(url)) {
                this.finish();
            }
            initUI();
            if (null == post_data || TextUtils.isEmpty(post_data)) {
                myWebView.loadUrl(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void initUI() {
        myWebView = (MyWebView) findViewById(R.id.peanut_web);
        baseTopBar.setTitle(word);
        progressDialog = new LoadingDialog(this, "正在加载...");
        progressDialog.show();

        // 去除缩放控件
        disableControl();

        myWebView.setOnPeanutWebViewListener(new MyWebView.OnPeanutWebViewListener() {
            @Override
            public void onReceivedTitle(String title) {
                progressDialog.dismiss();
            }
        });

        baseTopBar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onLeftClick() {
                if (myWebView != null && myWebView.canGoBack()
                        && !myWebView.getUrl().contains(url)) {
                    myWebView.goBack();
                } else {
                    onBackPressed();
                }
            }

            @Override
            public void onRightClick() {

            }

            @Override
            public void onTitleClick() {

            }

            @Override
            public void onLeftBackTextImageClick() {

            }
        });

        //给服务器端标记来源(用于区分App和浏览器)
        String ua = myWebView.getSettings().getUserAgentString();
        myWebView.getSettings().setUserAgentString(ua + "LOANKEY_ANDROID /" +
                BaseParameterUtil.getInstance().getAppVersionName(this));
    }

    //隐藏webview的缩放按钮 适用于3.0和以后
    public void setZoomControlGoneX(WebSettings view, Object[] args) {
        Class classType = view.getClass();
        try {
            Class[] argsClass = new Class[args.length];

            for (int i = 0, j = args.length; i < j; i++) {
                argsClass[i] = args[i].getClass();
            }
            Method[] ms = classType.getMethods();
            for (int i = 0; i < ms.length; i++) {
                if (ms[i].getName().equals("setDisplayZoomControls")) {
                    try {
                        ms[i].invoke(view, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //隐藏webview的缩放按钮 适用于3.0以前
    public void setZoomControlGone(View view) {
        Class classType;
        Field field;
        try {
            classType = WebView.class;
            field = classType.getDeclaredField("mZoomButtonsController");
            field.setAccessible(true);
            ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(view);
            mZoomButtonsController.getZoomControls().setVisibility(View.GONE);
            try {
                field.set(view, mZoomButtonsController);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 去除缩放控件
     */
    private void disableControl() {
        // 去除缩放控件
        myWebView.getSettings().setBuiltInZoomControls(false);
        myWebView.getSettings().setSupportZoom(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (myWebView != null && myWebView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK
                && !myWebView.getUrl().contains(url)) {
            myWebView.goBack();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return false;
    }

}
