package com.xju.memorize.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xju.memorize.R;
import com.xju.memorize.base.BaseApplication;
import com.xju.memorize.bean.DictionaryCategoryBean;
import com.xju.memorize.bean.DictionaryListBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

@RequiresApi(api = Build.VERSION_CODES.M)
public class DictionaryAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<DictionaryListBean.ListBean> list = new ArrayList<>();

    public DictionaryAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_dictionary, parent, false);
        return new DictionaryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        DictionaryHolder dictionaryHolder = (DictionaryHolder) holder;

        DictionaryListBean.ListBean bean = list.get(position);
        dictionaryHolder.tv_vocabulary.setText(bean.getName());

        dictionaryHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCurrentChecked = !dictionaryHolder.cb_item.isChecked();
                dictionaryHolder.cb_item.setChecked(isCurrentChecked);
                bean.setChecked(isCurrentChecked);
                System.out.println("fs");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<DictionaryListBean.ListBean> list) {
        this.list = list;
    }

    public class DictionaryHolder extends RecyclerView.ViewHolder {
        TextView tv_vocabulary;
        CheckBox cb_item;
        LinearLayout ll_item;

        public DictionaryHolder(View itemView) {
            super(itemView);
            tv_vocabulary = itemView.findViewById(R.id.tv_vocabulary);
            ll_item = itemView.findViewById(R.id.ll_item);
            cb_item = itemView.findViewById(R.id.cb_item);
        }
    }
}
