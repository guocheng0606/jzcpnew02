package com.jiuzhou.guanwang.jzcp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.bean.BullBean;

import java.util.List;

/**
 * 描述：
 * 作者：mu
 * 日期：2018/5/16 17:17
 */
public class MasterDetailAdapter extends BaseAdapter {

  private final Object mLock = new Object();
  private Context context;
  private List<BullBean.ResultsBean> dataList;
  private LayoutInflater inflater;

  public MasterDetailAdapter(Context context, List<BullBean.ResultsBean> dataList) {
    this.context = context;
    this.dataList = dataList;
    this.inflater = LayoutInflater.from(context);
  }

  @Override
  public int getCount() {
    return dataList.size();
  }

  @Override
  public Object getItem(int position) {
    return dataList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Holder holder;
    if (null == convertView) {
      convertView = inflater.inflate(R.layout.item_bull_detail, parent, false);
      holder = new Holder();
      holder.ivResult = (ImageView) convertView.findViewById(R.id.ivResult);
      holder.tvLabel = (TextView) convertView.findViewById(R.id.tvLabel);
      holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
      holder.tvReturn = (TextView) convertView.findViewById(R.id.tvReturnMoney);
      holder.tvOther = (TextView) convertView.findViewById(R.id.tvOtherNum);
      holder.tvNote = (TextView) convertView.findViewById(R.id.tvNote);
      convertView.setTag(holder);
    } else {
      holder = (Holder) convertView.getTag();
    }
    BullBean.ResultsBean awardInfo = dataList.get(position);
    holder.tvLabel.setText(awardInfo.getLeague().replace(",", " - "));
    holder.tvDate.setText(awardInfo.getCendtime().substring(5, 16) + "截止");
    holder.tvNote.setText(awardInfo.getNote());
    holder.tvReturn.setText(awardInfo.getIreturnrate() + "倍");
    holder.tvOther.setText(awardInfo.getCanCopyNum() + "人");
    if ("未开奖".equals(awardInfo.getResult())) {
      holder.ivResult.setImageResource(R.drawable.ic_not_award);
    } else if ("已中奖".equals(awardInfo.getResult())) {
      holder.ivResult.setImageResource(R.drawable.ic_have_award);
    } else {
      holder.ivResult.setImageResource(R.drawable.ic_no_award);
    }
    return convertView;
  }

  static class Holder {

    TextView tvLabel, tvDate, tvReturn, tvOther, tvNote;
    ImageView ivResult;

  }

  public void notify(List<BullBean.ResultsBean> dataList) {
    synchronized (mLock) {
      this.dataList = dataList;
    }
    notifyDataSetChanged();
  }
}

