package com.jiuzhou.guanwang.jzcp.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.base.BaseActivity;
import com.jiuzhou.guanwang.jzcp.bean.ArticleBean;
import com.lany.state.StateLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

public class List1Activity extends BaseActivity {

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.stateLayout)
    StateLayout stateLayout;
    @ViewInject(R.id.tv_title)
    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list1);
        ViewUtils.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("智能推荐");

        initData();
    }

    private void initData() {
        String url = "http://www.zjt-cp.com/art/getArticleByPage?param={%22clientId%22:%22suma-tech.pc.zjt%22,%22pageSize%22:1,%22pageNum%22:1,%22categoryId%22:%226%22}";
        OkGo.<String>get(url)
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
                        ArticleBean articleBean = gson.fromJson(result,ArticleBean.class);
                        String title = articleBean.getItems().get(0).getTitle();
                        if( title.indexOf("第") != -1 && title.indexOf("期") != -1){
                            title = title.substring(title.indexOf("第")+1,title.indexOf("期"));
                            tv_title.setText("【福彩3D】第"+title+"期推荐");
                        }
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
