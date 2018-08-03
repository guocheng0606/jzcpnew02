package com.jiuzhou.guanwang.jzcp.viewholder;

import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.bean.NewsBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Administrator on 2018/7/4.
 */

public class NewsViewHolder extends BaseViewHolder<NewsBean> {

    private ImageView image;
    private TextView title;
    private TextView pubdate;
    private TextView shorttitle;

    public NewsViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_news_number);
        image = $(R.id.image);
        title = $(R.id.title);
        pubdate = $(R.id.pubdate);
        shorttitle = $(R.id.shorttitle);
    }

    @Override
    public void setData(NewsBean data) {
        super.setData(data);
        Glide.with(getContext()).load(data.getImage()).into(image);
        title.setText(data.getTitle());
        pubdate.setText(data.getPubdate());
        shorttitle.setText(
                !TextUtils.isEmpty(data.getShorttitle()) ? data.getShorttitle().split(" ")[1] : "");
    }

}
