package com.jiuzhou.guanwang.jzcp.viewholder;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.bean.LotteryBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Administrator on 2018/7/4.
 */

public class LotteryViewHolder extends BaseViewHolder<LotteryBean.DataBean.NumberListBean> {
    private TextView tv_name;
    private TextView tv_qs;
    private TextView tv_time;
    private LinearLayout ll_redball;
    private LinearLayout ll_blueball;
    private LinearLayout ll_show;
    private boolean isRoot = true;//是否是根开奖

    public LotteryViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_lottery);
        tv_name = $(R.id.tv_name);
        tv_qs = $(R.id.tv_qs);
        tv_time = $(R.id.tv_time);
        ll_redball = $(R.id.ll_redball);
        ll_blueball = $(R.id.ll_blueball);
        ll_show = $(R.id.ll_show);
    }

    @Override
    public void setData(LotteryBean.DataBean.NumberListBean data) {
        super.setData(data);
        tv_name.setText(data.getName());
        tv_qs.setText("   第" + data.getIssueNum() + "期  |  ");
        tv_time.setText(data.getBonusTime().substring(0, 16));
        ll_redball.removeAllViews();
        ll_blueball.removeAllViews();

        if(!TextUtils.isEmpty(data.getBaseCode())){
            String[] red_ball = data.getBaseCode().split(",");
            for (int i = 0; i < red_ball.length; i++) {
                TextView ball = new TextView(getContext());
                ball.setText(red_ball[i]);
                ball.setTextSize(12);
                ball.setGravity(Gravity.CENTER);
                LayoutParams layoutParams = new LayoutParams(85, 85);
                layoutParams.setMarginStart(12);
                ball.setLayoutParams(layoutParams);
                ball.setPadding(0, 0, 0, 4);
                ball.setTextColor(getContext().getResources().getColor(R.color.white));
                ball.setBackgroundResource(R.drawable.ball_bg_red);
                ll_redball.addView(ball);
            }
        }
        if (!TextUtils.isEmpty(data.getSpecCode())) {
            String[] blue_ball = data.getSpecCode().split(",");
            for (int i = 0; i < blue_ball.length; i++) {
                TextView ball = new TextView(getContext());
                ball.setText(blue_ball[i]);
                ball.setTextSize(12);
                ball.setGravity(Gravity.CENTER);
                LayoutParams layoutParams = new LayoutParams(85, 85);
                layoutParams.setMarginStart(12);
                ball.setLayoutParams(layoutParams);
                ball.setPadding(0, 0, 0, 4);
                ball.setTextColor(getContext().getResources().getColor(R.color.white));
                ball.setBackgroundResource(R.drawable.ball_bg_blue);
                ll_blueball.addView(ball);
            }
        }
        if (isRoot) {
            if (TextUtils.isEmpty(data.getWinName())) {
                ll_show.setVisibility(View.GONE);
            } else {
                ll_show.setVisibility(View.VISIBLE);
            }
        }

    }

}
