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
public class LoginActivity extends BaseActivity {

    @BindView(R.id.cell_phone_number_et)
    EditText cell_phone_number_et;
    @BindView(R.id.password_et)
    EditText password_et;
    @BindView(R.id.login_btn)
    Button login_btn;

    /**
     * 手机号码
     */
    private CharSequence mPhoneStr;

    /**
     * 密码
     */
    private CharSequence mPasswordStr;

    @Override
    protected void initView() {
        super.initView();
        baseTopBar.setTitle("手机号登录");
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.login_btn, R.id.password_et})
    void buttonClick(View view) {
        String phone;
        switch (view.getId()) {
            case R.id.login_btn:
                phone = cell_phone_number_et.getText().toString().trim();
                String password = password_et.getText().toString().trim();

                if (TextUtils.isEmpty(phone)) {
                    baseToast.showToast("手机号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    baseToast.showToast("密码不能为空");
                    return;
                }

                // 手机号码登录
//                invokeLoginPhone(phone, password);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 手机号码号码
     *
     * @param phone    手机号码
     * @param password 密码
     */
    private void invokeLoginPhone(String phone, String password) {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);

        BaseSubscribe.login(map, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                LoginBean loginBean = GsonUtil.fromJson(result, LoginBean.class);
                SharedPreferencesUtil.setSharedPreferencesData(BaseApplication.getApp(), Constant.DEVICE_TOKEN_KEY,
                        loginBean.getToken());

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFault(String errorMsg) {
                baseToast.showToast(errorMsg);
            }
        }, LoginActivity.this, true));
    }
}
