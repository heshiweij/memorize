package com.xju.memorize.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xju.memorize.R;
import com.xju.memorize.base.BaseActivity;
import com.xju.memorize.base.BaseApplication;
import com.xju.memorize.bean.LoginBean;
import com.xju.memorize.constant.Constant;
import com.xju.memorize.net.subscribe.BaseSubscribe;
import com.xju.memorize.net.tool.OnSuccessAndFaultListener;
import com.xju.memorize.net.tool.OnSuccessAndFaultSub;
import com.xju.memorize.simple.SimpleTextChangedAdapter;
import com.xju.memorize.util.GsonUtil;
import com.xju.memorize.util.SharedPreferencesUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint({"NonConstantResourceId", "HandlerLeak"})
public class ProfileActivity extends BaseActivity {

    @Override
    protected void initView() {
        super.initView();
        baseTopBar.setTitle("我的资料");
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }

}
