package com.xju.memorize.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xju.memorize.R;
import com.xju.memorize.adapter.DictionaryAdapter;
import com.xju.memorize.base.BaseActivity;
import com.xju.memorize.base.BaseApplication;
import com.xju.memorize.bean.DictionaryCategoryBean;
import com.xju.memorize.bean.DictionaryListBean;
import com.xju.memorize.bean.LoginBean;
import com.xju.memorize.bean.UserInfoBean;
import com.xju.memorize.bean.UserVocabularyBean;
import com.xju.memorize.constant.Constant;
import com.xju.memorize.net.subscribe.BaseSubscribe;
import com.xju.memorize.net.tool.OnSuccessAndFaultListener;
import com.xju.memorize.net.tool.OnSuccessAndFaultSub;
import com.xju.memorize.util.GlideUtil;
import com.xju.memorize.util.GsonUtil;
import com.xju.memorize.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint({"NonConstantResourceId", "HandlerLeak"})
@RequiresApi(api = Build.VERSION_CODES.M)
public class DictionaryActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private DictionaryAdapter mAdapter;
    private List<DictionaryListBean.ListBean> mList;

    private String dictionary = "单词列表";
    private String categoryId;

    @Override
    protected void initView() {
        super.initView();

        dictionary = getIntent().getStringExtra("dictionary");
        baseTopBar.setTitle(dictionary);

        initRecyclerView();
    }

    @Override
    protected void initData() {
        super.initData();

        categoryId = getIntent().getStringExtra("category_id");

        // 调用 "用户信息" 接口
        invokeDictionaryVocabulary(categoryId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dictionary;
    }

    private void initRecyclerView() {
        mList = new ArrayList<>();

        // 定义一个线性布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);

        // 设置布局管理器
        recycler_view.setLayoutManager(manager);

        // 设置 adapter
        mAdapter = new DictionaryAdapter(this);
        recycler_view.setAdapter(mAdapter);
        mAdapter.addData(mList);
    }

    /**
     * 调用 "词典单词列表" 接口
     */
    private void invokeDictionaryVocabulary(String categoryId) {
        BaseSubscribe.dictionaryVocabulary(categoryId, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                DictionaryListBean bean = GsonUtil.fromJson(result, DictionaryListBean.class);

                if (bean != null && bean.getList() != null && bean.getList().size() > 0) {
                    mList.addAll(bean.getList());
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFault(String errorMsg) {
                baseToast.showToast(errorMsg);
            }
        }, this, true));
    }

    @OnClick({R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                List<String> vocabularies = new ArrayList<>();

                for (DictionaryListBean.ListBean bean : mList) {
                    if (bean.isChecked()) {
                        vocabularies.add(bean.getName());
                    }
                }

                // 调用 "创建用户单词列表" 接口
                invokeCreateUserVocabulary(vocabularies);
                break;
        }
    }

    /**
     * 调用 "创建用户单词列表" 接口
     */
    private void invokeCreateUserVocabulary(List<String> list) {
        HashMap<String, String> map = new HashMap<>();
        // 字符串转换处理
        String vocabularies = "";

        StringBuilder sb = new StringBuilder();
        for (String vocabulary : list) {
            sb.append(vocabulary);
            sb.append(",");
        }

        vocabularies = sb.toString();

        map.put("vocabularies", vocabularies);

        BaseSubscribe.createUserVocabulary(map, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                baseToast.showToast("提交成功");
                finish();
            }

            @Override
            public void onFault(String errorMsg) {
                baseToast.showToast(errorMsg);
            }
        }, DictionaryActivity.this, true));
    }

}
