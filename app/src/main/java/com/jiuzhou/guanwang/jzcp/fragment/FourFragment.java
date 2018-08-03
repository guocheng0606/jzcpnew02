package com.jiuzhou.guanwang.jzcp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.activity.WebNewsActivity;
import com.jiuzhou.guanwang.jzcp.bean.MjBean;
import com.jiuzhou.guanwang.jzcp.viewholder.MjViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class FourFragment extends Fragment implements View.OnClickListener,RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @ViewInject(R.id.tv_tab1)
    TextView tv_tab1;
    @ViewInject(R.id.tv_tab2)
    TextView tv_tab2;
    @ViewInject(R.id.tv_tab3)
    TextView tv_tab3;
    @ViewInject(R.id.tv_tab4)
    TextView tv_tab4;
    @ViewInject(R.id.tv_tab5)
    TextView tv_tab5;
    @ViewInject(R.id.recyclerView)
    EasyRecyclerView recyclerView;

    private RecyclerArrayAdapter<MjBean> adapter;
    private List<MjBean> ssq_mj = new ArrayList<>();
    private List<MjBean> dlt_mj = new ArrayList<>();
    private List<MjBean> sd_mj = new ArrayList<>();
    private List<MjBean> syx5_mj = new ArrayList<>();
    private List<MjBean> k3_mj = new ArrayList<>();

    public FourFragment() {
    }

    public static FourFragment newInstance(String param1, String param2) {
        FourFragment fragment = new FourFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_tab1.setSelected(true);
        initView();
        initListener();
        initData();
        onRefresh();
    }

    private void initData() {
        //大乐透秘籍
        dlt_mj.add(new MjBean("大乐透技巧-拣号法投注五步骤", "file:///android_asset/dlt1.html"));
        dlt_mj.add(new MjBean("前区码短跨度缩号", "file:///android_asset/dlt2.html"));
        dlt_mj.add(new MjBean("前区尾号缩号法", "file:///android_asset/dlt3.html"));
        dlt_mj.add(new MjBean("巧用和值分析法", "file:///android_asset/dlt4.html"));
        dlt_mj.add(new MjBean("三分前区看尾连", "file:///android_asset/dlt5.html"));
        dlt_mj.add(new MjBean("三种常用的守号法", "file:///android_asset/dlt6.html"));
        dlt_mj.add(new MjBean("生肖乐最有效方法", "file:///android_asset/dlt7.html"));
        dlt_mj.add(new MjBean("同尾号同奏凯歌", "file:///android_asset/dlt8.html"));
        dlt_mj.add(new MjBean("用竖三连方法断区", "file:///android_asset/dlt9.html"));
        dlt_mj.add(new MjBean("定胆技巧", "file:///android_asset/dlt10.html"));
        dlt_mj.add(new MjBean("冷热号组合投注", "file:///android_asset/dlt11.html"));
        dlt_mj.add(new MjBean("冷号投注", "file:///android_asset/dlt12.html"));
        dlt_mj.add(new MjBean("杀号技巧", "file:///android_asset/dlt13.html"));
        dlt_mj.add(new MjBean("概率基础", "file:///android_asset/dlt14.html"));

        //双色球秘籍
        ssq_mj.add(new MjBean("减号加号测蓝法", "file:///android_asset/ssq1.html"));
        ssq_mj.add(new MjBean("精准估算和值合集", "file:///android_asset/ssq2.html"));
        ssq_mj.add(new MjBean("精准选定6加1", "file:///android_asset/ssq3.html"));
        ssq_mj.add(new MjBean("篮球选号指标", "file:///android_asset/ssq4.html"));
        ssq_mj.add(new MjBean("利用空区判断号码", "file:///android_asset/ssq5.html"));
        ssq_mj.add(new MjBean("利用热号投注法", "file:///android_asset/ssq6.html"));
        ssq_mj.add(new MjBean("灵活利用蓝球杀号", "file:///android_asset/ssq7.html"));
        ssq_mj.add(new MjBean("拍号组合有经验", "file:///android_asset/ssq8.html"));
        ssq_mj.add(new MjBean("选蓝码的小方法集合", "file:///android_asset/ssq9.html"));
        ssq_mj.add(new MjBean("组号技巧推荐", "file:///android_asset/ssq10.html"));
        ssq_mj.add(new MjBean("定胆技巧", "file:///android_asset/ssq11.html"));
        ssq_mj.add(new MjBean("冷号投注", "file:///android_asset/ssq12.html"));
        ssq_mj.add(new MjBean("热号投注", "file:///android_asset/ssq13.html"));
        ssq_mj.add(new MjBean("杀号技巧", "file:///android_asset/ssq14.html"));
        ssq_mj.add(new MjBean("概率基础", "file:///android_asset/ssq15.html"));

        //3D秘籍
        sd_mj.add(new MjBean("采用数字排序取中胆的方法", "file:///android_asset/fc3d1.html"));
        sd_mj.add(new MjBean("3D技巧-过滤缩水法", "file:///android_asset/fc3d2.html"));
        sd_mj.add(new MjBean("3D技巧-和值投注攻略", "file:///android_asset/fc3d3.html"));
        sd_mj.add(new MjBean("冷热码搭配选号法", "file:///android_asset/fc3d4.html"));
        sd_mj.add(new MjBean("巧妙运用冷态方法", "file:///android_asset/fc3d5.html"));
        sd_mj.add(new MjBean("四步缩号法投注", "file:///android_asset/fc3d6.html"));
        sd_mj.add(new MjBean("投注有方-玩赚3D", "file:///android_asset/fc3d7.html"));
        sd_mj.add(new MjBean("新方法玩组六-以小博大", "file:///android_asset/fc3d8.html"));
        sd_mj.add(new MjBean("用跨度杀号的新方法", "file:///android_asset/fc3d9.html"));
        sd_mj.add(new MjBean("六法解析", "file:///android_asset/fc3d10.html"));

        //11选5秘籍
        syx5_mj.add(new MjBean("前二玩法中如何保持高中奖率", "file:///android_asset/gp11x51.html"));
        syx5_mj.add(new MjBean("十兄弟谏言", "file:///android_asset/gp11x52.html"));
        syx5_mj.add(new MjBean("提高中奖率之灵活运用胆拖", "file:///android_asset/gp11x53.html"));
        syx5_mj.add(new MjBean("提高中奖率之逆向选号法", "file:///android_asset/gp11x54.html"));
        syx5_mj.add(new MjBean("投注技巧-心态决定一切", "file:///android_asset/gp11x55.html"));
        syx5_mj.add(new MjBean("玩法很丰富 理性投资很重要", "file:///android_asset/gp11x56.html"));
        syx5_mj.add(new MjBean("神级杀号技巧", "file:///android_asset/gp11x57.html"));
        syx5_mj.add(new MjBean("追号大思考", "file:///android_asset/gp11x58.html"));
        syx5_mj.add(new MjBean("组三选号心得", "file:///android_asset/gp11x59.html"));
        syx5_mj.add(new MjBean("六大选号法祝您中奖", "file:///android_asset/gp11x510.html"));
        syx5_mj.add(new MjBean("胆拖选胆的必要条件", "file:///android_asset/gp11x511.html"));
        syx5_mj.add(new MjBean("最合算的买法分析", "file:///android_asset/gp11x512.html"));
        syx5_mj.add(new MjBean("四大绝招提高中奖概率", "file:///android_asset/gp11x513.html"));
        syx5_mj.add(new MjBean("玩转任选五和任选八", "file:///android_asset/gp11x514.html"));
        syx5_mj.add(new MjBean("两种玩法教你玩转任选四", "file:///android_asset/gp11x515.html"));

        //快3秘籍
        k3_mj.add(new MjBean("充分利用奇偶遗漏", "file:///android_asset/k31.html"));
        k3_mj.add(new MjBean("充分利用跨度的遗漏", "file:///android_asset/k32.html"));
        k3_mj.add(new MjBean("合理利用胆码遗漏机会", "file:///android_asset/k33.html"));
        k3_mj.add(new MjBean("利用号码分布图", "file:///android_asset/k34.html"));
        k3_mj.add(new MjBean("利用全奇,全偶,全大,全小的遗漏及回补", "file:///android_asset/k35.html"));
        k3_mj.add(new MjBean("追二同号的回补", "file:///android_asset/k36.html"));
        k3_mj.add(new MjBean("追三不同的回补", "file:///android_asset/k37.html"));
        k3_mj.add(new MjBean("快三经验总结（一）", "file:///android_asset/k38.html"));
        k3_mj.add(new MjBean("快三十大经典玩法大揭秘", "file:///android_asset/k39.html"));
        k3_mj.add(new MjBean("玩快三一定要记住这几条秘诀", "file:///android_asset/k310.html"));
        k3_mj.add(new MjBean("玩快三最好的方法就是玩转和值", "file:///android_asset/k311.html"));
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerDecoration itemDecoration = new DividerDecoration(0xFFEDEDED, 1, 0, 0);
        itemDecoration.setDrawLastItem(true);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<MjBean>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new MjViewHolder(parent);
            }
        });
        adapter.setMore(R.layout.view_more, this);
        adapter.setNoMore(R.layout.view_nomore, new RecyclerArrayAdapter.OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {
                adapter.resumeMore();
            }

            @Override
            public void onNoMoreClick() {
                adapter.resumeMore();
            }
        });
        adapter.setError(R.layout.view_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });
        recyclerView.setRefreshListener(this);
    }

    private void initListener() {
        tv_tab1.setOnClickListener(this);
        tv_tab2.setOnClickListener(this);
        tv_tab3.setOnClickListener(this);
        tv_tab4.setOnClickListener(this);
        tv_tab5.setOnClickListener(this);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), WebNewsActivity.class);
                intent.putExtra("url",""+adapter.getAllData().get(position).getUrl());
                intent.putExtra("title",""+adapter.getAllData().get(position).getTitle());
                startActivity(intent);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_tab1:
                tv_tab1.setSelected(true);
                tv_tab2.setSelected(false);
                tv_tab3.setSelected(false);
                tv_tab4.setSelected(false);
                tv_tab5.setSelected(false);
                break;
            case R.id.tv_tab2:
                tv_tab1.setSelected(false);
                tv_tab2.setSelected(true);
                tv_tab3.setSelected(false);
                tv_tab4.setSelected(false);
                tv_tab5.setSelected(false);
                break;
            case R.id.tv_tab3:
                tv_tab1.setSelected(false);
                tv_tab2.setSelected(false);
                tv_tab3.setSelected(true);
                tv_tab4.setSelected(false);
                tv_tab5.setSelected(false);
                break;
            case R.id.tv_tab4:
                tv_tab1.setSelected(false);
                tv_tab2.setSelected(false);
                tv_tab3.setSelected(false);
                tv_tab4.setSelected(true);
                tv_tab5.setSelected(false);
                break;
            case R.id.tv_tab5:
                tv_tab1.setSelected(false);
                tv_tab2.setSelected(false);
                tv_tab3.setSelected(false);
                tv_tab4.setSelected(false);
                tv_tab5.setSelected(true);
                break;
        }
        onRefresh();
    }

    @Override
    public void onRefresh() {
        if (tv_tab1.isSelected()){
            adapter.clear();
            adapter.addAll(ssq_mj);
        }
        if (tv_tab2.isSelected()){
            adapter.clear();
            adapter.addAll(dlt_mj);
        }
        if (tv_tab3.isSelected()){
            adapter.clear();
            adapter.addAll(sd_mj);
        }
        if (tv_tab4.isSelected()){
            adapter.clear();
            adapter.addAll(syx5_mj);
        }
        if (tv_tab5.isSelected()){
            adapter.clear();
            adapter.addAll(k3_mj);
        }

    }

    @Override
    public void onLoadMore() {
        adapter.stopMore();
    }
}
