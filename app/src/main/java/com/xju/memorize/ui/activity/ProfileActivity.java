package com.xju.memorize.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xju.memorize.R;
import com.xju.memorize.base.BaseActivity;
import com.xju.memorize.base.BaseApplication;
import com.xju.memorize.bean.LoginBean;
import com.xju.memorize.bean.UserInfoBean;
import com.xju.memorize.constant.Constant;
import com.xju.memorize.net.subscribe.BaseSubscribe;
import com.xju.memorize.net.tool.OnSuccessAndFaultListener;
import com.xju.memorize.net.tool.OnSuccessAndFaultSub;
import com.xju.memorize.simple.SimpleTextChangedAdapter;
import com.xju.memorize.util.GlideUtil;
import com.xju.memorize.util.GsonUtil;
import com.xju.memorize.util.SharedPreferencesUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint({"NonConstantResourceId", "HandlerLeak"})
public class ProfileActivity extends BaseActivity {

    @BindView(R.id.ll_logout)
    LinearLayout ll_logout;
    @BindView(R.id.iv_profile)
    ImageView iv_profile;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_signature)
    TextView tv_signature;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_gender)
    TextView tv_gender;

    @Override
    protected void initView() {
        super.initView();
        baseTopBar.setTitle("我的资料");
    }

    @Override
    protected void initData() {
        super.initData();

        // 调用 "用户信息" 接口
        invokeUserInfo();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }

    @OnClick({R.id.ll_logout})
    void onClick(View view) {
        clearActivities();

        // 清空 Token
        SharedPreferencesUtil.setSharedPreferencesData(BaseApplication.getApp(), Constant.DEVICE_TOKEN_KEY, "");
        // 跳转登录
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 调用 "用户信息" 接口
     */
    private void invokeUserInfo() {
        BaseSubscribe.userinfo(new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                UserInfoBean bean = GsonUtil.fromJson(result, UserInfoBean.class);

                Glide.with(BaseApplication.getApp()).load(bean.getPortrait()).apply(GlideUtil.portraitOptions()).into(iv_profile);
                tv_nickname.setText(bean.getNickname());
                tv_signature.setText(bean.getSignature());
                tv_email.setText(bean.getEmail());
                tv_phone.setText(bean.getPhone());
                tv_gender.setText(bean.getGender());
            }

            @Override
            public void onFault(String errorMsg) {
                baseToast.showToast(errorMsg);
            }
        }, this, true));
    }

}
