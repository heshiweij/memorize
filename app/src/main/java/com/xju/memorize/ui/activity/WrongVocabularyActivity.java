package com.xju.memorize.ui.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;

import com.xju.memorize.R;
import com.xju.memorize.adapter.DictionaryAdapter;
import com.xju.memorize.adapter.WrongVocabularyAdapter;
import com.xju.memorize.base.BaseActivity;
import com.xju.memorize.bean.DictionaryListBean;
import com.xju.memorize.bean.UserVocabularyBean;
import com.xju.memorize.net.subscribe.BaseSubscribe;
import com.xju.memorize.net.tool.OnSuccessAndFaultListener;
import com.xju.memorize.net.tool.OnSuccessAndFaultSub;
import com.xju.memorize.util.GsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint({"NonConstantResourceId", "HandlerLeak"})
@RequiresApi(api = Build.VERSION_CODES.M)
public class WrongVocabularyActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private WrongVocabularyAdapter mAdapter;
    private List<UserVocabularyBean.ListBean> mList;

    private String dictionary = "错词列表";

    @Override
    protected void initView() {
        super.initView();
        baseTopBar.setTitle(dictionary);

        initRecyclerView();
    }

    @Override
    protected void initData() {
        super.initData();

        // 调用 "获取错词语列表" 接口
        invokeWrongVocabulary();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wrong_vocabulary;
    }

    private void initRecyclerView() {
        mList = new ArrayList<>();

        // 定义一个线性布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);

        // 设置布局管理器
        recycler_view.setLayoutManager(manager);

        // 设置 adapter
        mAdapter = new WrongVocabularyAdapter(this);
        recycler_view.setAdapter(mAdapter);
        mAdapter.addData(mList);
    }

    /**
     * 调用 "获取错词语列表" 接口
     */
    private void invokeWrongVocabulary() {
        BaseSubscribe.userWrongVocabulary(new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                UserVocabularyBean bean = GsonUtil.fromJson(result, UserVocabularyBean.class);

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
}
