package com.jiuzhou.guanwang.jzcp.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.bean.MasterBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/7/4.
 */

public class MasterViewHolder extends BaseViewHolder<MasterBean> {

    private CircleImageView civMe;
    private TextView tvName;
    private TextView tvMonth;
    private TextView tvGet;
    private TextView tvHit;
    private TextView tvExplain;

    public MasterViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_master_layout);
        civMe = $(R.id.civMe);
        tvName = $(R.id.tvName);
        tvMonth = $(R.id.tvMonth);
        tvGet = $(R.id.tvGet);
        tvHit = $(R.id.tvHit);
        tvExplain = $(R.id.tvExplain);
    }

    @Override
    public void setData(MasterBean data) {
        super.setData(data);
        tvName.setText(data.getNickname());
        tvMonth.setText(data.getMonth() + "月");
        tvGet.setText(data.getAvgreturnrate() + "%");
        tvHit.setText(data.getHitrate() + "%");
        tvExplain.setText(data.getDesc());
        if (!"".equals(data.getImageUrl())) {
            Glide.with(getContext()).load("https://smapi.159cai.com/" + data.getImageUrl())
                    .into(civMe);
        }

    }

}
