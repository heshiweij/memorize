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
import com.xju.memorize.bean.DictionaryListBean;
import com.xju.memorize.bean.UserVocabularyBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

@RequiresApi(api = Build.VERSION_CODES.M)
public class WrongVocabularyAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<UserVocabularyBean.ListBean> list = new ArrayList<>();

    public WrongVocabularyAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_user_wrong, parent, false);
        return new UserVocabularyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        UserVocabularyBean.ListBean bean = list.get(position);

        UserVocabularyHolder userVocabularyHolder = (UserVocabularyHolder) holder;
        userVocabularyHolder.tv_vocabulary.setText(bean.getWord());
        userVocabularyHolder.tv_wrong_count.setText(String.format("%s 次", bean.getCount()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<UserVocabularyBean.ListBean> list) {
        this.list = list;
    }

    public class UserVocabularyHolder extends RecyclerView.ViewHolder {
        TextView tv_vocabulary;
        TextView tv_wrong_count;

        public UserVocabularyHolder(View itemView) {
            super(itemView);
            tv_vocabulary = itemView.findViewById(R.id.tv_vocabulary);
            tv_wrong_count = itemView.findViewById(R.id.tv_wrong_count);
        }
    }
}
