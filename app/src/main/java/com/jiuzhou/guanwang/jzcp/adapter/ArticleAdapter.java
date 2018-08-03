package com.jiuzhou.guanwang.jzcp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.bean.ArticleBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/7/24.
 */

public class ArticleAdapter extends BaseAdapter {

    private Context context;
    private List<ArticleBean.ItemsBean> list;

    public ArticleAdapter(Context context, List<ArticleBean.ItemsBean> list){
        this.context = context;
        this.list = list;
    }

    public List<ArticleBean.ItemsBean> getAllData(){
        return list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list == null ? null : list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_article_layout,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tv_title.setText(""+list.get(i).getTitle());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(list.get(i).getReleaseDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String str = format.format(date);
        holder.tv_time.setText(str+ "   阅读："+list.get(i).getReadTimes());
        return view;
    }

    class ViewHolder{

        @ViewInject(R.id.tv_title)
        TextView tv_title;
        @ViewInject(R.id.tv_time)
        TextView tv_time;

        public ViewHolder(View view){
            ViewUtils.inject(this,view);
        }
    }

}
