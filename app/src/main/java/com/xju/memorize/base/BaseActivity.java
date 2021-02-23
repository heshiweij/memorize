package com.xju.memorize.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.xju.memorize.R;
import com.xju.memorize.component.bar.TopBar;
import com.xju.memorize.constant.Constant;
import com.xju.memorize.util.EasyToast;
import com.xju.memorize.util.SharedPreferencesUtil;
import com.xju.memorize.util.StatusBarUtil;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {
    protected TopBar baseTopBar;
    protected EasyToast baseToast;

    protected boolean isDestroy;
    //防止重复点击设置的标志，涉及到点击打开其他Activity时，将该标志设置为false，在onResume事件中设置为true
    private boolean clickable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isDestroy = false;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        addActivity(this);

        initStatusBar();
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        // 初始化基础视图
        setContentView(R.layout.activity_base);

        // 初始化 TopBar
        baseTopBar = findViewById(R.id.base_topbar);
        initTopBar();

        // 绑定子视图
        ViewGroup containerView = findViewById(R.id.base_container);
        LayoutInflater.from(this).inflate(getLayoutId(), containerView);

        // ButterKnife 组件绑定
        ButterKnife.bind(this);

        baseToast = new EasyToast(this);

        // 自定义: 初始化视图
        initView();

        // 自定义: 初始化数据
        initData();
    }

    /**
     * 压 Activity 栈
     *
     * @param activity Activity
     */
    protected void addActivity(Activity activity) {
        if (!BaseApplication.sActivities.contains(activity)) {
            BaseApplication.sActivities.add(activity);
        }
    }

    /**
     * 清空 Activity 栈
     */
    protected void clearActivities() {
        for (int i = BaseApplication.sActivities.size() - 1; i >= 0; i--) {
            if (BaseApplication.sActivities.get(i) != null) {
                BaseApplication.sActivities.get(i).finish();
            }
        }

        BaseApplication.sActivities.clear();
    }

    /**
     * 初始化视图
     */
    protected void initView() {
        // Empty
    }

    /**
     * 初始化数据
     */
    protected void initData() {
        // Empty
    }

    @Override
    protected void onResume() {
        super.onResume();

        //每次返回界面时，将点击标志设置为可点击
        clickable = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 当前是否可以点击
     *
     * @return boolean
     */
    protected boolean isClickable() {
        return clickable;
    }

    /**
     * 锁定点击
     */
    protected void lockClick() {
        clickable = false;
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        if (isClickable()) {
            lockClick();
            super.startActivityForResult(intent, requestCode, options);
        }
    }

    /**
     * 是否已登录
     *
     * @return boolean
     */
    protected boolean isLogin() {
        String token = SharedPreferencesUtil.getSharedPreferencesData(this, Constant.DEVICE_TOKEN_KEY);

        return !TextUtils.isEmpty(token);
    }

    /**
     * 初始化 TopBar
     */
    private void initTopBar() {
        // 设置 TopBar 点击事件
        baseTopBar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onLeftClick() {
                // TopBar 右侧按钮点击
                topBarOnLeftClick();
            }

            @Override
            public void onRightClick() {
                // TopBar 右侧按钮点击
                topBarOnRightClick();
            }

            @Override
            public void onTitleClick() {
                // TopBar 标题点击
                topBarOnTitleClick();
            }

            @Override
            public void onLeftBackTextImageClick() {
                // TopBar 左侧图片点击
                topBarLeftTextImageClick();
            }
        });
    }

    /**
     * 初始化状态栏
     */
    private void initStatusBar() {
//        放到activity里面单独的修改状态栏颜色
//        StatusBarUtil.setGradientColor(FavoriteDetailsActivity.this, bar_color_ll);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isUseFullScreenMode()) {
                StatusBarUtil.transparencyBar(this);
            } else {
                StatusBarUtil.setStatusBarColor(this, getStatusBarColor());
            }

            if (isUseBlackFontWithStatusBar()) {
                StatusBarUtil.setLightStatusBar(this, false, isUseFullScreenMode());
            }
        }
    }

    /**
     * 获取视图资源 ID
     *
     * @return int
     */
    protected abstract int getLayoutId();

    /**
     * 隐藏 TopBar
     */
    protected void hideBaseTopBar() {
        baseTopBar.setVisibility(View.GONE);
    }

    /**
     * TopBar 左侧图片点击
     */
    protected void topBarLeftTextImageClick() {
        finish();
    }

    /**
     * TopBar 右侧按钮点击
     */
    protected void topBarOnLeftClick() {
        finish();
    }

    /**
     * TopBar 右侧按钮点击
     */
    protected void topBarOnRightClick() {
        // Empty
    }

    /**
     * TopBar 标题点击
     */
    protected void topBarOnTitleClick() {
        // Empty
    }

    /**
     * 是否设置成透明状态栏，即就是全屏模式
     *
     * @return boolean
     */
    protected boolean isUseFullScreenMode() {
        return false;
    }

    /**
     * 更改状态栏颜色，只有非全屏模式下有效
     *
     * @return int
     */
    protected int getStatusBarColor() {
        return R.color.colorPrimaryDark;
    }

    /**
     * 是否改变状态栏文字颜色为黑色，默认为黑色
     *
     * @return boolean
     */
    protected boolean isUseBlackFontWithStatusBar() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        isDestroy = true;

        BaseApplication.sActivities.remove(this);
    }
}
