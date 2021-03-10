package com.xju.memorize.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xju.memorize.R;
import com.xju.memorize.base.BaseApplication;
import com.xju.memorize.base.BaseFragment;
import com.xju.memorize.bean.LoginBean;
import com.xju.memorize.bean.UserInfoBean;
import com.xju.memorize.constant.Constant;
import com.xju.memorize.net.subscribe.BaseSubscribe;
import com.xju.memorize.net.tool.OnSuccessAndFaultListener;
import com.xju.memorize.net.tool.OnSuccessAndFaultSub;
import com.xju.memorize.ui.activity.LoginActivity;
import com.xju.memorize.ui.activity.MainActivity;
import com.xju.memorize.ui.activity.ProfileActivity;
import com.xju.memorize.ui.activity.WrongVocabularyActivity;
import com.xju.memorize.util.GlideUtil;
import com.xju.memorize.util.GsonUtil;
import com.xju.memorize.util.SharedPreferencesUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class SettingsFragment extends BaseFragment {

    @BindView(R.id.iv_profile)
    ImageView iv_profile;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();

        // 调用 "用户信息" 接口
        invokeUserInfo();
    }

    @OnClick({R.id.rl_userinfo, R.id.tv_wrong_book})
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()) {
            case R.id.rl_userinfo:
                intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_wrong_book:
                intent = new Intent(getActivity(), WrongVocabularyActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 调用 "用户信息" 接口
     */
    private void invokeUserInfo() {
        BaseSubscribe.userinfo(new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                UserInfoBean userInfoBean = GsonUtil.fromJson(result, UserInfoBean.class);
                Glide.with(BaseApplication.getApp()).load(userInfoBean.getPortrait()).apply(GlideUtil.portraitOptions()).into(iv_profile);
            }

            @Override
            public void onFault(String errorMsg) {
                baseToast.showToast(errorMsg);
            }
        }, getActivity(), true));
    }
}
