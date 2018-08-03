package com.jiuzhou.guanwang.jzcp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.adapter.LotteryDetailAdapter;
import com.jiuzhou.guanwang.jzcp.bean.LotteryBean;
import com.jiuzhou.guanwang.jzcp.bean.LotteryDetailsBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class LotteryDetailActivity extends AppCompatActivity {

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.tv_name)
    TextView tv_name;
    @ViewInject(R.id.tv_qs)
    TextView tv_qs;
    @ViewInject(R.id.tv_time)
    TextView tv_time;
    @ViewInject(R.id.ll_redball)
    LinearLayout ll_redball;
    @ViewInject(R.id.ll_blueball)
    LinearLayout ll_blueball;
    @ViewInject(R.id.tv_sales)
    TextView tv_sales;
    @ViewInject(R.id.tv_pool)
    TextView tv_pool;
    @ViewInject(R.id.rv_result)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_detail);
        ViewUtils.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        setData();
    }

    private void setData() {
        String data = getIntent().getStringExtra("data");
        Gson gson = new Gson();
        LotteryBean.DataBean.NumberListBean bean = gson.fromJson(data, LotteryBean.DataBean.NumberListBean.class);
        tv_name.setText(bean.getName());
        tv_qs.setText("  第" + bean.getIssueNum() + "期  |  ");
        tv_time.setText(bean.getBonusTime().substring(0, 16));
        ll_blueball.removeAllViews();
        ll_redball.removeAllViews();

        if(!TextUtils.isEmpty(bean.getBaseCode())){
            String[] red_ball = bean.getBaseCode().split(",");
            for (int i = 0; i < red_ball.length; i++) {
                TextView ball = new TextView(this);
                ball.setText(red_ball[i]);
                ball.setTextSize(12);
                ball.setGravity(Gravity.CENTER);
                LayoutParams layoutParams = new LayoutParams(85, 85);
                layoutParams.setMarginStart(12);
                ball.setLayoutParams(layoutParams);
                ball.setPadding(0, 0, 0, 4);
                ball.setTextColor(getResources().getColor(R.color.white));
                ball.setBackgroundResource(R.drawable.ball_bg_red);
                ll_redball.addView(ball);
            }
        }
        if (!TextUtils.isEmpty(bean.getSpecCode())) {
            String[] blue_ball = bean.getSpecCode().split(",");
            for (int i = 0; i < blue_ball.length; i++) {
                TextView ball = new TextView(this);
                ball.setText(blue_ball[i]);
                ball.setTextSize(12);
                ball.setGravity(Gravity.CENTER);
                LayoutParams layoutParams = new LayoutParams(85, 85);
                layoutParams.setMarginStart(12);
                ball.setPadding(0, 0, 0, 4);
                ball.setLayoutParams(layoutParams);
                ball.setTextColor(getResources().getColor(R.color.white));
                ball.setBackgroundResource(R.drawable.ball_bg_blue);
                ll_blueball.addView(ball);
            }
        }
        //本期销量
        tv_sales.setText(TextUtils.isEmpty(bean.getSaleTotal()) ? "0" : bean.getSaleTotal());
        //奖池累计
        tv_pool.setText(TextUtils.isEmpty(bean.getBonusBlance()) ? "0" : bean.getBonusBlance());
        //中奖表格
        List<LotteryDetailsBean> list = new ArrayList<>();
        String[] winName = bean.getWinName().split(",");
        String[] winMoney = bean.getWinMoney().split(",");
        String[] winCount = bean.getWinCount().split(",");
        for (int i = 0; i < winName.length; i++) {
            LotteryDetailsBean lotteryDetailsBean = new LotteryDetailsBean();
            lotteryDetailsBean.setWinName(winName[i]);
            lotteryDetailsBean.setWinMoney(winMoney[i]);
            lotteryDetailsBean.setWinCount(winCount[i]);
            list.add(lotteryDetailsBean);
        }
        LotteryDetailAdapter lotteryDetailAdapter =
                new LotteryDetailAdapter(LotteryDetailActivity.this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(LotteryDetailActivity.this));
        recyclerView.setAdapter(lotteryDetailAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
