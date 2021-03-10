package com.xju.memorize.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xju.memorize.R;
import com.xju.memorize.base.BaseApplication;
import com.xju.memorize.base.BaseFragment;
import com.xju.memorize.bean.LoginBean;
import com.xju.memorize.bean.UserVocabularyBean;
import com.xju.memorize.component.web.WebActivity;
import com.xju.memorize.constant.Constant;
import com.xju.memorize.net.subscribe.BaseSubscribe;
import com.xju.memorize.net.tool.OnSuccessAndFaultListener;
import com.xju.memorize.net.tool.OnSuccessAndFaultSub;
import com.xju.memorize.ui.activity.DictionaryActivity;
import com.xju.memorize.ui.activity.LoginActivity;
import com.xju.memorize.ui.activity.MainActivity;
import com.xju.memorize.util.GlideUtil;
import com.xju.memorize.util.GsonUtil;
import com.xju.memorize.util.SharedPreferencesUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.RequiresApi;
import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
@RequiresApi(api = Build.VERSION_CODES.M)
public class ReviewFragment extends BaseFragment {

    @BindView(R.id.ll_bing)
    LinearLayout ll_bing;

    @BindView(R.id.tv_word)
    TextView tv_word;

    @BindView(R.id.tv_pronunciation)
    TextView tv_pronunciation;

    @BindView(R.id.tv_translate)
    TextView tv_translate;

    @BindView(R.id.iv_picture)
    ImageView iv_picture;

    @BindView(R.id.tv_example_sentence)
    TextView tv_example_sentence;

    @BindView(R.id.rl_mask)
    RelativeLayout rl_mask;

    /**
     * 从网络加载的单词列表
     */
    private List<UserVocabularyBean.ListBean> vocabularies = new ArrayList<>();

    /**
     * 当前单词
     */
    private int index = 0;

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

        // 获取用户单词列表
        invokeUserVocabulary();
    }

    /**
     * 渲染单词、释义、音标等
     */
    private void applyWord() {
        if (vocabularies == null || vocabularies.size() <= 0) {
            return;
        }
        if (vocabularies.get(index) == null) {
            return;
        }

        tv_word.setText(vocabularies.get(index).getWord());
        tv_pronunciation.setText(vocabularies.get(index).getPronunciation());
        tv_translate.setText(vocabularies.get(index).getTranslate());
        Glide.with(BaseApplication.getApp()).load(vocabularies.get(index).getPicture()).into(iv_picture);

        StringBuilder exampleSentence = new StringBuilder();
        if (vocabularies.get(index).getExample_sentence() != null && vocabularies.get(index).getExample_sentence().size() > 0) {
            for (String s : vocabularies.get(index).getExample_sentence()) {
                exampleSentence.append(s);
                exampleSentence.append("\n\n");
            }
        }

        tv_example_sentence.setText(exampleSentence.toString());
    }

    @OnClick({R.id.ll_bing,
            R.id.ll_youdao,
            R.id.ll_collins,
            R.id.ll_jinshan,
            R.id.ll_cambridge,
            R.id.btn_known,
            R.id.btn_un_confirm,
            R.id.btn_unknown,
            R.id.rl_mask,
            R.id.rl_pronunciation,
            R.id.tv_word,
            R.id.ll_word
    })
    public void onClick(View view) {
        if (vocabularies == null || vocabularies.size() <= 0) {
            baseToast.showToast("加载数据异常,请稍后重试");
            return;
        }
        if (TextUtils.isEmpty(vocabularies.get(index).getWord())) {
            return;
        }

        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("word", vocabularies.get(index).getWord());

        switch (view.getId()) {
            case R.id.ll_bing:
                intent.putExtra("url", String.format("https://cn.bing.com/dict/search?q=%s", vocabularies.get(index).getWord()));
                startActivity(intent);
                break;
            case R.id.ll_youdao:
                intent.putExtra("url", String.format("https://m.youdao.com/dict?le=eng&q=%s", vocabularies.get(index).getWord()));
                startActivity(intent);
                break;
            case R.id.ll_collins:
                intent.putExtra("url", String.format("https://www.collinsdictionary.com/dictionary/english/%s", vocabularies.get(index).getWord()));
                startActivity(intent);
                break;
            case R.id.ll_jinshan:
                intent.putExtra("url", String.format("http://m.iciba.com/%s", vocabularies.get(index).getWord()));
                startActivity(intent);
                break;
            case R.id.ll_cambridge:
                intent.putExtra("url", String.format("https://dictionary.cambridge.org/dictionary/english-chinese-simplified/%s", vocabularies.get(index).getWord()));
                startActivity(intent);
                break;
            case R.id.btn_known:
            case R.id.btn_un_confirm:
                next();
                break;
            case R.id.btn_unknown:
                next();

                // 记录错词本
                invokeCreateUserWrongVocabulary();
                break;
            case R.id.rl_mask:
                rl_mask.setVisibility(View.GONE);
                makeSound();
                break;
            case R.id.rl_pronunciation:
            case R.id.tv_word:
            case R.id.ll_word:
                makeSound();
                break;
        }
    }

    private void next() {
        rl_mask.setVisibility(View.VISIBLE);

        index++;
        index = index % vocabularies.size();

        applyWord();
    }

    /**
     * 获取用户单词列表
     */
    private void invokeUserVocabulary() {
        BaseSubscribe.userVocabulary(new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
                UserVocabularyBean bean = GsonUtil.fromJson(result, UserVocabularyBean.class);
                if (bean != null && bean.getList() != null && bean.getList().size() > 0) {
                    vocabularies.addAll(bean.getList());
                    index = 0;

                    applyWord();
                    return;
                }

                baseToast.showToast("暂无单词，请先添加词典");

                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.activeChoicePage();
                }
            }

            @Override
            public void onFault(String errorMsg) {
                baseToast.showToast(errorMsg);
            }
        }, getActivity(), false));
    }

    /**
     * 调用 "创建用户单词列表" 接口
     */
    private void invokeCreateUserWrongVocabulary() {
        if (vocabularies == null || vocabularies.size() <= 0) {
            return;
        }

        HashMap<String, String> map = new HashMap<>();

        map.put("vocabulary", vocabularies.get(index).getWord());

        BaseSubscribe.createUserWrongVocabulary(map, new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(String result) {
            }

            @Override
            public void onFault(String errorMsg) {
                baseToast.showToast(errorMsg);
            }
        }, getActivity(), true));
    }

    private void makeSound() {
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(String.format("http://dict.youdao.com/dictvoice?audio=%s", vocabularies.get(index).getWord()));
            mp.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mp.start();
    }
}
