package com.jiuzhou.guanwang.jzcp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.bean.LotteryDetailsBean;
import java.util.List;

/**
 */

public class LotteryDetailAdapter extends RecyclerView.Adapter {

  private List<LotteryDetailsBean> data;
  private Context mtx;

  public static enum ITEM_TYPE {
    ITEM_TYPE_TITLE, ITEM_TYPE_DATA
  }

  public LotteryDetailAdapter(Context context, List<LotteryDetailsBean> bean) {
    this.mtx = context;
    this.data = bean;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == ITEM_TYPE.ITEM_TYPE_TITLE.ordinal()) {
      View view = LayoutInflater.from(mtx).inflate(R.layout.item_total_title, parent, false);
      return new TitleViewHolder(view);
    } else {
      View view = LayoutInflater.from(mtx).inflate(R.layout.item_total_data, parent, false);
      return new DataViewHolder(view);
    }
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    if (position != 0) {
      LotteryDetailsBean lotteryDetailsBean = data.get(position - 1);
      ((DataViewHolder) holder).jx.setText(lotteryDetailsBean.getWinName());
      ((DataViewHolder) holder).zs.setText(
          lotteryDetailsBean.getWinCount().contains(" ") ? "0" : lotteryDetailsBean.getWinCount());
      ((DataViewHolder) holder).jj.setText(
          lotteryDetailsBean.getWinMoney().contains(" ") ? "0" : lotteryDetailsBean.getWinMoney());
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0) {
      return ITEM_TYPE.ITEM_TYPE_TITLE.ordinal();
    } else {
      return ITEM_TYPE.ITEM_TYPE_DATA.ordinal();
    }
  }

  @Override
  public int getItemCount() {
    if (data != null) {
      return data.size() + 1;
    }
    return 0;
  }

  class DataViewHolder extends ViewHolder {

    TextView jx;
    TextView zs;
    TextView jj;

    public DataViewHolder(View itemView) {
      super(itemView);

      jx = itemView.findViewById(R.id.jx);
      zs = itemView.findViewById(R.id.zs);
      jj = itemView.findViewById(R.id.jj);
    }

  }

  class TitleViewHolder extends ViewHolder {

    public TitleViewHolder(View itemView) {
      super(itemView);

    }
  }

}
