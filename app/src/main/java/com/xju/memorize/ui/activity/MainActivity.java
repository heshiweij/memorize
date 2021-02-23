package com.xju.memorize.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.xju.memorize.R;
import com.xju.memorize.base.BaseActivity;
import com.xju.memorize.constant.Constant;
import com.xju.memorize.event.BackHomeEvent;
import com.xju.memorize.ui.fragment.ChoiceFragment;
import com.xju.memorize.ui.fragment.ReviewFragment;
import com.xju.memorize.ui.fragment.SettingsFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint({"NonConstantResourceId", "LongLogTag"})
public class MainActivity extends BaseActivity {

    /**
     * "复习" 按钮
     */
    @BindView(R.id.review_ll)
    LinearLayout review_ll;

    /**
     * "选词" 按钮
     */
    @BindView(R.id.choice_ll)
    LinearLayout choice_ll;

    /**
     * "设置" 按钮
     */
    @BindView(R.id.settings_ll)
    LinearLayout settings_ll;

    /**
     * 主内容
     */
    @BindView(R.id.ly_content)
    FrameLayout ly_content;

    // 复习
    private ReviewFragment reviewFragment;
    // 选词
    private ChoiceFragment choiceFragment;
    // 设置
    private SettingsFragment settingsFragment;

    /**
     * 复习 key
     */
    private static final String REVIEW_FRAGMENT_KEY = "REVIEW_FRAGMENT_KEY";

    /**
     * 选词 key
     */
    private static final String CHOICE_FRAGMENT_KEY = "CHOICE_FRAGMENT_KEY";

    /**
     * 设置 key
     */
    private static final String SETTINGS_FRAGMENT_KEY = "SETTINGS_FRAGMENT_KEY";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void initView() {
        super.initView();

        // 隐藏 TopBar
        hideBaseTopBar();

        // 激活首页
        activeHomePage();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    /**
     * 激活 "首页"
     */
    public void activeHomePage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                review_ll.performClick();
            }
        }, Constant.JUMP_PAGE_DELAY);
    }

    @OnClick({R.id.review_ll, R.id.choice_ll, R.id.settings_ll})
    void buttonClick(View view) {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);
        switch (view.getId()) {
            case R.id.review_ll:
                // 切换 "复习"
                unSelected();
                review_ll.setSelected(true);
                if (reviewFragment == null) {
                    reviewFragment = new ReviewFragment();
                    fTransaction.add(R.id.ly_content, reviewFragment);
                } else {
                    fTransaction.show(reviewFragment);
                }
                break;
            case R.id.choice_ll:
                // 切换 "选词"
                unSelected();
                choice_ll.setSelected(true);
                if (choiceFragment == null) {
                    choiceFragment = new ChoiceFragment();
                    fTransaction.add(R.id.ly_content, choiceFragment);
                } else {
                    fTransaction.show(choiceFragment);
                }
                break;
            case R.id.settings_ll:
                // 切换 "设置"
                unSelected();
                settings_ll.setSelected(true);
                if (settingsFragment == null) {
                    settingsFragment = new SettingsFragment();
                    fTransaction.add(R.id.ly_content, settingsFragment);
                } else {
                    fTransaction.show(settingsFragment);
                }
                break;
        }
        fTransaction.commitAllowingStateLoss();
    }

    /**
     * 重置所有文本的选中状态
     */
    private void unSelected() {
        review_ll.setSelected(false);
        choice_ll.setSelected(false);
        settings_ll.setSelected(false);
    }

    /**
     * 隐藏所有Fragment
     *
     * @param fragmentTransaction FragmentTransaction
     */
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (reviewFragment != null) fragmentTransaction.hide(reviewFragment);
        if (choiceFragment != null) fragmentTransaction.hide(choiceFragment);
        if (settingsFragment != null) fragmentTransaction.hide(settingsFragment);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        if (reviewFragment != null) {
            getSupportFragmentManager().putFragment(bundle, REVIEW_FRAGMENT_KEY, reviewFragment);
        }
        if (choiceFragment != null) {
            getSupportFragmentManager().putFragment(bundle, CHOICE_FRAGMENT_KEY, choiceFragment);
        }
        if (settingsFragment != null) {
            getSupportFragmentManager().putFragment(bundle, SETTINGS_FRAGMENT_KEY, settingsFragment);
        }
        super.onSaveInstanceState(bundle);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BackHomeEvent e) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                review_ll.performClick();
            }
        }, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
