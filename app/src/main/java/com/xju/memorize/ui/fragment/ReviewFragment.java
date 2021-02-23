package com.xju.memorize.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.xju.memorize.R;
import com.xju.memorize.base.BaseFragment;
import com.xju.memorize.component.web.WebActivity;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class ReviewFragment extends BaseFragment {

    @BindView(R.id.ll_bing)
    LinearLayout ll_bing;

    /**
     * 当前单词
     */
    private String word = "hello";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_review;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @OnClick({R.id.ll_bing, R.id.ll_youdao, R.id.ll_collins, R.id.ll_jinshan, R.id.ll_cambridge})
    public void onClick(View view) {
        if (TextUtils.isEmpty(word)) {
            return;
        }

        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("word", word);

        switch (view.getId()) {
            case R.id.ll_bing:
                intent.putExtra("url", String.format("https://cn.bing.com/dict/search?q=%s", word));
                startActivity(intent);
                break;
            case R.id.ll_youdao:
                intent.putExtra("url", String.format("https://m.youdao.com/dict?le=eng&q=%s", word));
                startActivity(intent);
                break;
            case R.id.ll_collins:
                intent.putExtra("url", String.format("https://www.collinsdictionary.com/dictionary/english/%s", word));
                startActivity(intent);
                break;
            case R.id.ll_jinshan:
                intent.putExtra("url", String.format("http://m.iciba.com/%s", word));
                startActivity(intent);
                break;
            case R.id.ll_cambridge:
                intent.putExtra("url", String.format("https://dictionary.cambridge.org/dictionary/english-chinese-simplified/%s", word));
                startActivity(intent);
                break;
        }
    }
}
