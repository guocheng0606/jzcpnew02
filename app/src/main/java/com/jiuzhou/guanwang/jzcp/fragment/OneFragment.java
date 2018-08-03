package com.jiuzhou.guanwang.jzcp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.activity.List1Activity;
import com.jiuzhou.guanwang.jzcp.activity.List2Activity;
import com.jiuzhou.guanwang.jzcp.activity.List3Activity;
import com.jiuzhou.guanwang.jzcp.activity.List4Activity;
import com.jiuzhou.guanwang.jzcp.activity.List5Activity;
import com.jiuzhou.guanwang.jzcp.activity.List6Activity;
import com.jiuzhou.guanwang.jzcp.activity.WebNewsActivity;
import com.jiuzhou.guanwang.jzcp.adapter.ArticleAdapter;
import com.jiuzhou.guanwang.jzcp.bean.ArticleBean;
import com.jiuzhou.guanwang.jzcp.loader.GlideImageLoader;
import com.jiuzhou.guanwang.jzcp.widget.NoScrollListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class OneFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @ViewInject(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.banner)
    Banner mBanner;
    @ViewInject(R.id.ll_01)
    LinearLayout ll_01;
    @ViewInject(R.id.ll_02)
    LinearLayout ll_02;
    @ViewInject(R.id.ll_03)
    LinearLayout ll_03;
    @ViewInject(R.id.ll_04)
    LinearLayout ll_04;
    @ViewInject(R.id.ll_05)
    LinearLayout ll_05;
    @ViewInject(R.id.ll_06)
    LinearLayout ll_06;
    @ViewInject(R.id.listView1)
    NoScrollListView listView1;

    private ArticleAdapter adapter1;

    private String mParam1;
    private String mParam2;

    public OneFragment() {
    }

    public static OneFragment newInstance(String param1, String param2) {
        OneFragment fragment = new OneFragment();
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
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        ViewUtils.inject(this,view);
        setBannerData();

        View foot = LayoutInflater.from(getActivity()).inflate(R.layout.view_click_more, null);
        LinearLayout ll = foot.findViewById(R.id.ll_more);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), List4Activity.class);
                intent.putExtra("title_info","开奖资讯");
                intent.putExtra("type_info",1);
                startActivity(intent);
            }
        });
        listView1.setFocusable(false);
        listView1.addFooterView(foot);
        setLotteryData();
        initListener();
        return view;
    }

    private void initListener() {
        ll_01.setOnClickListener(this);
        ll_02.setOnClickListener(this);
        ll_03.setOnClickListener(this);
        ll_04.setOnClickListener(this);
        ll_05.setOnClickListener(this);
        ll_06.setOnClickListener(this);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i < adapter1.getAllData().size()) {
                    Intent intent = new Intent(getActivity(), WebNewsActivity.class);
                    intent.putExtra("url",""+adapter1.getAllData().get(i).getUrl());
                    intent.putExtra("title",""+adapter1.getAllData().get(i).getTitle());
                    startActivity(intent);
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setLotteryData();
            }
        });
    }

    private void setLotteryData() {
        OkGo.<String>get("http://www.zjt-cp.com/art/getArticleByPage?param={\"clientId\":\"suma-tech.pc.zjt\",\"pageSize\":5,\"pageNum\":1,\"categoryId\":\"7\"}")
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        Gson gson = new Gson();
                        ArticleBean articleBean = gson.fromJson(result,ArticleBean.class);
                        List<ArticleBean.ItemsBean> list = articleBean.getItems();
                        adapter1 = new ArticleAdapter(getActivity(),list);
                        listView1.setAdapter(adapter1);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    private void setBannerData() {
        ArrayList<Integer> BannerList = new ArrayList<>();
        BannerList.add(R.drawable.banner1);
        BannerList.add(R.drawable.banner2);
        BannerList.add(R.drawable.banner3);
        BannerList.add(R.drawable.banner4);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(3000);
        mBanner.setImages(BannerList)
                .setImageLoader(new GlideImageLoader())
                .start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_01:
                startActivity(new Intent(getActivity(), List1Activity.class));
                break;
            case R.id.ll_02:
                startActivity(new Intent(getActivity(), List2Activity.class));
                break;
            case R.id.ll_03:
                startActivity(new Intent(getActivity(), List3Activity.class));
                break;
            case R.id.ll_04:
                Intent intent = new Intent(getActivity(), List4Activity.class);
                intent.putExtra("title_info","专业预测");
                intent.putExtra("type_info",0);
                startActivity(intent);
                break;
            case R.id.ll_05:
                startActivity(new Intent(getActivity(), List5Activity.class));
                break;
            case R.id.ll_06:
                startActivity(new Intent(getActivity(), List6Activity.class));
                break;
        }
    }
}
