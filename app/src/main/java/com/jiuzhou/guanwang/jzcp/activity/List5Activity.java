package com.jiuzhou.guanwang.jzcp.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.adapter.MyViewPagerAdapter;
import com.jiuzhou.guanwang.jzcp.base.BaseActivity;
import com.jiuzhou.guanwang.jzcp.fragment.LotteryChildFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class List5Activity extends BaseActivity {

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.tabLayout)
    TabLayout tabLayout;
    @ViewInject(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list5);
        ViewUtils.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("开奖大厅");
        setupTabLayout();
    }

    private void setupTabLayout() {
        viewPager.setOffscreenPageLimit(4);
        //ViewPager关联适配器
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(LotteryChildFragment.newInstance("001",null),"双色球");
        adapter.addFragment(LotteryChildFragment.newInstance("113",null),"大乐透");
        adapter.addFragment(LotteryChildFragment.newInstance("002",null),"福彩3D");
        adapter.addFragment(LotteryChildFragment.newInstance("110",null),"七星彩");
        adapter.addFragment(LotteryChildFragment.newInstance("003",null),"七乐彩");
        viewPager.setAdapter(adapter);
        //ViewPager和TabLayout关联
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
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
