package com.jiuzhou.guanwang.jzcp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.activity.LotteryDetailActivity;
import com.jiuzhou.guanwang.jzcp.bean.LotteryBean;
import com.jiuzhou.guanwang.jzcp.utils.LogUtils;
import com.jiuzhou.guanwang.jzcp.viewholder.LotteryViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.simple.SimpleCallback;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.util.List;

/**
 *
 */
public class LotteryChildFragment extends Fragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static String baseUrl = "http://issue.wozhongla.com/bonus/getBonusList.vhtml";

    @ViewInject(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<LotteryBean.DataBean.NumberListBean> adapter;
    private int currentPage = 1;

    public LotteryChildFragment() {
    }

    public static LotteryChildFragment newInstance(String param1, String param2) {
        LotteryChildFragment fragment = new LotteryChildFragment();
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
        View view = inflater.inflate(R.layout.fragment_lottery_child, container, false);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerDecoration itemDecoration = new DividerDecoration(0xFFEDEDED, 1, 0, 0);
        itemDecoration.setDrawLastItem(true);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<LotteryBean.DataBean.NumberListBean>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new LotteryViewHolder(parent);
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
        setHttpData();
        initListener();
    }

    private void initListener() {
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (!TextUtils.isEmpty(adapter.getAllData().get(position).getWinName())) {
                    //当期开奖详情
                    Intent intent = new Intent(getActivity(), LotteryDetailActivity.class);
                    Gson gson = new Gson();
                    intent.putExtra("data", gson.toJson(adapter.getAllData().get(position)));
                    intent.putExtra("title", "开奖实况");
                    startActivity(intent);
                }
            }
        });
    }

    private void setHttpData() {
        Kalle.get(baseUrl)
                .param("pageNo",currentPage)
                .param("pageNum",20)
                .param("lotId",mParam1)
                .perform(new SimpleCallback<String>() {

                    @Override
                    public void onResponse(SimpleResponse<String, String> simpleResponse) {
                        if(simpleResponse.isSucceed()){ // Http成功，业务也成功。
                            String result = simpleResponse.succeed();
                            LogUtils.LogShow(result);
                            Gson gson = new Gson();
                            LotteryBean lotteryBean = gson.fromJson(result,LotteryBean.class);
                            if("01001".equals(lotteryBean.getData().getResult())){
                                List<LotteryBean.DataBean.NumberListBean> numberList = lotteryBean.getData().getNumberList();
                                if(numberList.size() > 0){
                                    for (LotteryBean.DataBean.NumberListBean bean : numberList) {
                                        doParse(bean);
                                    }
                                    if(currentPage == 1)
                                        adapter.clear();
                                    adapter.addAll(numberList);
                                }else{
                                    if (currentPage == 1) {
                                        adapter.clear();
                                    } else {
                                        adapter.stopMore();
                                    }
                                }

                            }

                        } else {
                            //LogUtils.LogShow(simpleResponse.failed());
                        }
                    }
                });
    }

    private void doParse(LotteryBean.DataBean.NumberListBean bean) {
        switch (bean.getLotteryId()) {
            case "001":
                bean.setName("双色球");
                break;
            case "002":
                bean.setName("福彩3D");
                break;
            case "003":
                bean.setName("七乐彩");
                break;
            case "113":
                bean.setName("大乐透");
                break;
            case "110":
                bean.setName("七星彩");
                break;
            case "108":
                bean.setName("排列三");
                break;
            case "109":
                bean.setName("排列五");
                break;
            case "100"://浙江，上海，新疆，广东，黑龙江，江西，山东，天津，甘肃，四川，山西，辽宁，吉林，贵州，安徽，福建，江苏，陕西，湖北，广西
                bean.setName("浙江11选5");
                break;
            case "101":
                bean.setName("上海11选5");
                break;
            case "102":
                bean.setName("新疆11选5");
                break;
            case "104":
                bean.setName("广东11选5");
                break;
            case "105":
                bean.setName("黑龙江11选5");
                break;
            case "106":
                bean.setName("江西11选5");
                break;
            case "107":
                bean.setName("山东11选5");
                break;
            case "085":
                bean.setName("天津11选5");
                break;
            case "086":
                bean.setName("甘肃11选5");
                break;
            case "088":
                bean.setName("四川11选5");
                break;
            case "089":
                bean.setName("山西11选5");
                break;
            case "090":
                bean.setName("辽宁11选5");
                break;
            case "091":
                bean.setName("吉林11选5");
                break;
            case "092":
                bean.setName("贵州11选5");
                break;
            case "093":
                bean.setName("安徽11选5");
                break;
            case "094":
                bean.setName("福建11选5");
                break;
            case "095":
                bean.setName("江苏11选5");
                break;
            case "096":
                bean.setName("陕西11选5");
                break;
            case "097":
                bean.setName("湖北11选5");
                break;
            case "098":
                bean.setName("广西11选5");
                break;
            case "099":
                bean.setName("河南11选5");
                break;
            case "022"://湖北，吉林，安徽，江苏，广西，北京，河南，福建，甘肃，河北，上海，青海，贵州，江西，内蒙
                bean.setName("吉林快3");
                break;
            case "023":
                bean.setName("安徽快3");
                break;
            case "024":
                bean.setName("江苏快3");
                break;
            case "027":
                bean.setName("广西快3");
                break;
            case "028":
                bean.setName("北京快3");
                break;
            case "029":
                bean.setName("河南快3");
                break;
            case "030":
                bean.setName("福建快3");
                break;
            case "031":
                bean.setName("甘肃快3");
                break;
            case "032":
                bean.setName("河北快3");
                break;
            case "033":
                bean.setName("上海快3");
                break;
            case "034":
                bean.setName("青海快3");
                break;
            case "035":
                bean.setName("贵州快3");
                break;
            case "036":
                bean.setName("江西快3");
                break;
            case "037":
                bean.setName("内蒙快3");
                break;
        }
    }


    @Override
    public void onLoadMore() {
        currentPage++;
        setHttpData();
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        setHttpData();
    }
}
