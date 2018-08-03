package com.jiuzhou.guanwang.jzcp.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.adapter.MasterDetailAdapter;
import com.jiuzhou.guanwang.jzcp.base.BaseActivity;
import com.jiuzhou.guanwang.jzcp.bean.BullBean;
import com.jiuzhou.guanwang.jzcp.utils.LocalJsonResolutionUtils;
import com.jiuzhou.guanwang.jzcp.widget.AutoExpandListView;
import com.lany.state.StateLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MasterDetailActivity extends BaseActivity {

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.stateLayout)
    StateLayout stateLayout;
    @ViewInject(R.id.civMe)
    CircleImageView civMe;
    @ViewInject(R.id.tvName)
    TextView tvName;
    @ViewInject(R.id.tvFans)
    TextView tvFans;
    @ViewInject(R.id.tvWatch)
    TextView tvWatch;
    @ViewInject(R.id.tvMoneyMine)
    TextView tvMoneyMine;
    @ViewInject(R.id.tvMoneyOther)
    TextView tvMoneyOther;
    @ViewInject(R.id.tvRate30)
    TextView tvRate30;
    @ViewInject(R.id.tvRate07)
    TextView tvRate07;
    @ViewInject(R.id.tvHitRate)
    TextView tvHitRate;
    @ViewInject(R.id.ivResultDay01)
    ImageView ivResultDay01;
    @ViewInject(R.id.ivResultDay02)
    ImageView ivResultDay02;
    @ViewInject(R.id.ivResultDay03)
    ImageView ivResultDay03;
    @ViewInject(R.id.ivResultDay04)
    ImageView ivResultDay04;
    @ViewInject(R.id.ivResultDay05)
    ImageView ivResultDay05;
    @ViewInject(R.id.lvBullDetail)
    AutoExpandListView lvBullDetail;

    private String uid;
    private String time;

    public static final SimpleDateFormat DEFAULT_FULL = new SimpleDateFormat("yyyyMMddHHmmss",
            Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);
        ViewUtils.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("个人战绩");

        uid = getIntent().getStringExtra("uid");
        time = getFullTime();
        lvBullDetail.setFocusable(false);
        stateLayout.showLoading();
        getBullsData();
    }

    private void getBullsData() {
        String strJson = LocalJsonResolutionUtils.getJson(MasterDetailActivity.this, "bull_detail.json");
        List<BullBean> list = LocalJsonResolutionUtils.jsonToArrayList(strJson, BullBean.class);
        BullBean bullBean = null;
        for (BullBean bean : list) {
            if (uid.equals(bean.getCnickid())) {
                bullBean = bean;
                break;
            }
        }
        if (!"".equals(bullBean.getImageUrl())) {
            Glide.with(MasterDetailActivity.this)
                    .load("https://smapi.159cai.com/" + bullBean.getImageUrl())
                    .into(civMe);
        }
        tvName.setText(bullBean.getCnickname());
        tvFans.setText(bullBean.getIfoucsnum());
        tvWatch.setText(bullBean.getIhitnum());
        tvRate07.setText(bullBean.getProfit() + "%");
        tvRate30.setText(bullBean.getZprofit_m() + "%");
        tvHitRate.setText(bullBean.getAllnum() + "中" + bullBean.getHitnum());
        tvMoneyMine.setText(""+bullBean.getIcopyaward().substring(0,5));
        tvMoneyOther.setText(""+bullBean.getIaward().substring(0,4));
        stateLayout.showContent();

        lvBullDetail.setAdapter(new MasterDetailAdapter(MasterDetailActivity.this, bullBean.getResults()));

    }

    /**
     * 获取当前完整格式化时间
     */
    public String getFullTime() {
        return getFull(System.currentTimeMillis());
    }

    /**
     * 获取完整格式化时间
     */
    public static String getFull(long millis) {
        return getTime(millis, DEFAULT_FULL);
    }

    /**
     * 获取格式化时间
     */
    public static String getTime(long millis, SimpleDateFormat simpleDateFormat) {
        return simpleDateFormat.format(new Date(millis));
    }

    private void setResultImage(ImageView v, String str) {
        if ("1".equals(str)) {
            v.setImageResource(R.drawable.ic_bull_zhong);
        } else {
            v.setImageResource(R.drawable.ic_bull_wei);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
