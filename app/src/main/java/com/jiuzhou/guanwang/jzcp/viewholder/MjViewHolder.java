package com.jiuzhou.guanwang.jzcp.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.bean.MjBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Administrator on 2018/7/4.
 */

public class MjViewHolder extends BaseViewHolder<MjBean> {

    private TextView title;

    public MjViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_mj);
        title = $(R.id.title);
    }

    @Override
    public void setData(MjBean data) {
        super.setData(data);
        title.setText(""+data.getTitle());
    }

}
