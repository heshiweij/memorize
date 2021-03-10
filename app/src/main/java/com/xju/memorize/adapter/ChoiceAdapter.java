package com.xju.memorize.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.xju.memorize.R;
import com.xju.memorize.base.BaseApplication;
import com.xju.memorize.bean.DictionaryCategoryBean;
import com.xju.memorize.ui.activity.DictionaryActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

@RequiresApi(api = Build.VERSION_CODES.M)
public class ChoiceAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<DictionaryCategoryBean.ListBean> list = new ArrayList<>();

    public ChoiceAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_choice, parent, false);
        return new ChoiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ChoiceHolder choiceHolder = (ChoiceHolder) holder;
        DictionaryCategoryBean.ListBean bean = list.get(position);

        choiceHolder.tv_category.setText(bean.getName());
        choiceHolder.ll_container.removeAllViews();
        List<DictionaryCategoryBean.ListBean.ChildrenBean> children = bean.getChildren();
        for (DictionaryCategoryBean.ListBean.ChildrenBean child : children) {
            View item = LayoutInflater.from(BaseApplication.getApp()).inflate(R.layout.item_each_dictionary, null);
            TextView tv_dictionary = item.findViewById(R.id.tv_dictionary);
            tv_dictionary.setText(child.getName());
            tv_dictionary.setTag(child.getId());
            tv_dictionary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DictionaryActivity.class);
                    intent.putExtra("category_id", String.valueOf(child.getId()));
                    intent.putExtra("dictionary", child.getName());
                    mContext.startActivity(intent);
                }
            });
            choiceHolder.ll_container.addView(item);
        }

        choiceHolder.tv_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choiceHolder.ll_container.getVisibility() == View.GONE) {
                    choiceHolder.ll_container.setVisibility(View.VISIBLE);
                } else {
                    choiceHolder.ll_container.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<DictionaryCategoryBean.ListBean> list) {
        this.list = list;
    }

    public class ChoiceHolder extends RecyclerView.ViewHolder {
        TextView tv_category;
        LinearLayout ll_container;

        public ChoiceHolder(View itemView) {
            super(itemView);
            tv_category = itemView.findViewById(R.id.tv_category);
            ll_container = itemView.findViewById(R.id.ll_container);
        }
    }
}
