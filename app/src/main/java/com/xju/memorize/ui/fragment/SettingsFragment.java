package com.xju.memorize.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import com.xju.memorize.R;
import com.xju.memorize.base.BaseFragment;
import com.xju.memorize.ui.activity.ProfileActivity;

import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class SettingsFragment extends BaseFragment {

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
    }

    @OnClick({R.id.rl_userinfo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_userinfo:
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
                break;
        }
    }
}
