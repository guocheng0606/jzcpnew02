package com.jiuzhou.guanwang.jzcp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.bean.SjhBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Administrator on 2018/7/24.
 */

public class SjhAdapter extends BaseAdapter {

    private Context context;
    private List<SjhBean.ItemsBean> list;

    public SjhAdapter(Context context,List<SjhBean.ItemsBean> list){
        this.context = context;
        this.list = list;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_sjh_layout,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_01.setText(list.get(i).getQiHao());
        holder.tv_02.setText(list.get(i).getLotDate());
        holder.tv_03.setText(list.get(i).getSjh());
        holder.tv_04.setText(list.get(i).getLottery());
        return view;
    }

    class ViewHolder{

        @ViewInject(R.id.tv_01)
        TextView tv_01;
        @ViewInject(R.id.tv_02)
        TextView tv_02;
        @ViewInject(R.id.tv_03)
        TextView tv_03;
        @ViewInject(R.id.tv_04)
        TextView tv_04;

        public ViewHolder(View view){
            ViewUtils.inject(this,view);
        }
    }

}
