package com.jiuzhou.guanwang.jzcp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.base.BaseActivity;
import com.jiuzhou.guanwang.jzcp.bean.ArticleBean;
import com.jiuzhou.guanwang.jzcp.utils.ToastUtils;
import com.jiuzhou.guanwang.jzcp.viewholder.ArticleViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.List;

public class List4Activity extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<ArticleBean.ItemsBean> adapter;
    private int currentPage = 1;

    private String title = "";
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list4);
        ViewUtils.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title = getIntent().getStringExtra("title_info");
        type = getIntent().getIntExtra("type_info",0);
        getSupportActionBar().setTitle(title);
        initView();
        onRefresh();
        initListener();
    }

    private void initListener() {
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(List4Activity.this, WebNewsActivity.class);
                intent.putExtra("url",""+adapter.getAllData().get(position).getUrl());
                intent.putExtra("title",""+adapter.getAllData().get(position).getTitle());
                startActivity(intent);
            }
        });
    }

    private void getData(){
        String url = "";
        if (type == 1)
            url = "http://www.zjt-cp.com/art/getArticleByPage?param={\"clientId\":\"suma-tech.pc.zjt\",\"pageSize\":5,\"pageNum\":"+currentPage+",\"categoryId\":\"7\"}";
         else
            url = "http://www.zjt-cp.com/art/getArticleByPage?param={\"clientId\":\"suma-tech.pc.zjt\",\"pageSize\":20,\"pageNum\":"+currentPage+",\"categoryId\":\"6\"}";
        OkGo.<String>get(url)
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
                        if(list.size() > 0){
                            if(currentPage == 1)
                                adapter.clear();
                            adapter.addAll(list);
                        }else{
                            if (currentPage == 1) {
                                adapter.clear();
                            } else {
                                adapter.stopMore();
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (currentPage == 1) {
                            ToastUtils.showShortToast(List4Activity.this, "连接服务器异常");
                        } else {
                            adapter.pauseMore();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration itemDecoration = new DividerDecoration(0xFFEDEDED, 1, 0, 0);
        itemDecoration.setDrawLastItem(true);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<ArticleBean.ItemsBean>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new ArticleViewHolder(parent);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        getData();
    }

    @Override
    public void onLoadMore() {
        currentPage++;
        getData();
    }
}
