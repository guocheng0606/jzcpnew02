package com.jiuzhou.guanwang.jzcp.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.bean.ArticleBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/7/4.
 */

public class ArticleViewHolder extends BaseViewHolder<ArticleBean.ItemsBean> {

    private TextView tv_title;
    private TextView tv_time;

    public ArticleViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_article_layout);
        tv_title = $(R.id.tv_title);
        tv_time = $(R.id.tv_time);
    }

    @Override
    public void setData(ArticleBean.ItemsBean data) {
        super.setData(data);
        tv_title.setText(""+data.getTitle());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(data.getReleaseDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String str = format.format(date);
        tv_time.setText(str+ "   阅读："+data.getReadTimes() +"   来源："+data.getOrigin());

    }

}
