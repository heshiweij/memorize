package com.xju.memorize.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.xju.memorize.R;

public class LoadingDialog extends Dialog {
    private String content;

    public LoadingDialog(Context context, String content) {
        super(context, R.style.CustomDialog);
        this.content = content;
        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.setWindowAnimations(R.style.NullAnimationDialog);

//        smoothScreen();

//        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setWindowAnimations(
                R.style.dialog_animation_fade);
    }

//    private void smoothScreen(){
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//            ViewGroup rootView = ((ViewGroup) this.findViewById(android.R.id.content));
//            // 视情况是否加入边界（app是否为全屏显示）
////            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
////            int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
//            rootView.setPadding(0, 0, 0, 0);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (LoadingDialog.this.isShowing()) {
                    LoadingDialog.this.dismiss();
                }
                break;
        }
        return true;
    }

    private void initView() {
        setContentView(R.layout.dialog_view);
        ((TextView) findViewById(R.id.tvcontent)).setText(content);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = 0.8f;
        getWindow().setAttributes(attributes);
        setCancelable(false);
    }
}


