package com.jiuzhou.guanwang.jzcp.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.adapter.SjhAdapter;
import com.jiuzhou.guanwang.jzcp.base.BaseActivity;
import com.jiuzhou.guanwang.jzcp.bean.SjhBean;
import com.jiuzhou.guanwang.jzcp.widget.NoScrollListView;
import com.lany.state.StateLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.List;

import static com.jiuzhou.guanwang.jzcp.R.id.listView1;

public class List2Activity extends BaseActivity {

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.stateLayout)
    StateLayout stateLayout;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.tv_bill1)
    TextView tv_bill1;
    @ViewInject(R.id.tv_bill2)
    TextView tv_bill2;
    @ViewInject(R.id.tv_bill3)
    TextView tv_bill3;
    @ViewInject(R.id.tv_bill)
    TextView tv_bill;
    @ViewInject(R.id.listView)
    NoScrollListView listView;
    private SjhAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        ViewUtils.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("试机号");
        listView.setFocusable(false);
        initData();
    }

    private void initData() {
        OkGo.<String>get("http://www.zjt-cp.com/lot/getSjh.action?param={}")
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        stateLayout.showLoading();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        Gson gson = new Gson();
                        SjhBean sjhBean = gson.fromJson(result, SjhBean.class);
                        tv_title.setText("第"+sjhBean.getItems().get(0).getQiHao()+"期试机号");
                        String[] strs = sjhBean.getItems().get(0).getSjh().split(" ");
                        tv_bill1.setText(strs[0]);
                        tv_bill2.setText(strs[1]);
                        tv_bill3.setText(strs[2]);
                        tv_bill.setText("开奖号："+sjhBean.getItems().get(0).getLottery());

                        List<SjhBean.ItemsBean> list = sjhBean.getItems().subList(1,sjhBean.getItems().size());
                        adapter = new SjhAdapter(List2Activity.this,list);
                        listView.setAdapter(adapter);
                        stateLayout.showContent();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        stateLayout.showError();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
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
