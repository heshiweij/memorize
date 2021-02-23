package com.xju.memorize.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xju.memorize.R;
import com.xju.memorize.adapter.ChoiceAdapter;
import com.xju.memorize.base.BaseFragment;
import com.xju.memorize.bean.DictionaryCategoryBean;
import com.xju.memorize.net.subscribe.BaseSubscribe;
import com.xju.memorize.net.tool.OnSuccessAndFaultListener;
import com.xju.memorize.net.tool.OnSuccessAndFaultSub;
import com.xju.memorize.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
@RequiresApi(api = Build.VERSION_CODES.M)
public class ChoiceFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.refresh_layout)
    RefreshLayout refresh_layout;

    private ChoiceAdapter mAdapter;
    private List<DictionaryCategoryBean.ListBean> mList;

    /**
     * 页码
     */
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_choice;
    }

    @Override
    protected void initView() {
        super.initView();
        initRecyclerView();
        refresh_layout.setEnableRefresh(true);
        refresh_layout.setEnableLoadMore(false);
    }

    @Override
    protected void initData() {
        super.initData();

        // 调用 "获取选词列表接口"
        invokeChoiceList();
    }

    private void initRecyclerView() {
        mList = new ArrayList<>();

        // 定义一个线性布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);

        // 设置布局管理器
        recycler_view.setLayoutManager(manager);

        // 设置 adapter
        mAdapter = new ChoiceAdapter(getActivity());
        recycler_view.setAdapter(mAdapter);
        mAdapter.addData(mList);

        refresh_layout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;

                // 调用 "获取选词列表接口"
                invokeChoiceList();

                refreshLayout.finishLoadMore();
            }
        });
        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;

                // 调用 "获取选词列表接口"
                invokeChoiceList();

                refreshLayout.finishRefresh();
            }
        });
    }

    /**
     * 调用 "获取选词列表接口"
     */
    private void invokeChoiceList() {
        BaseSubscribe.dictionaryCategory(new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                DictionaryCategoryBean bean = GsonUtil.fromJson(result, DictionaryCategoryBean.class);
                if (page == 1) {
                    mList.clear();
                }
                if (bean != null && bean.getList() != null && bean.getList().size() > 0) {
                    mList.addAll(bean.getList());
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFault(String errorMsg) {
                baseToast.showToast(errorMsg);
            }
        }, getActivity(), true));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }
}
