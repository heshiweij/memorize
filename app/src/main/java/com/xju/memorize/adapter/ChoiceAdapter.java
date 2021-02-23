package com.xju.memorize.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.xju.memorize.R;
import com.xju.memorize.bean.DictionaryCategoryBean;

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

        ((ChoiceHolder) holder).tv_category.setText(bean.getName());
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

        public ChoiceHolder(View itemView) {
            super(itemView);
            tv_category = itemView.findViewById(R.id.tv_category);
        }
    }
}
