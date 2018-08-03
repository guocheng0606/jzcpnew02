package com.jiuzhou.guanwang.jzcp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.adapter.MyViewPagerAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 */
public class ThreeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @ViewInject(R.id.tabLayout)
    TabLayout tabLayout;
    @ViewInject(R.id.viewPager)
    ViewPager viewPager;

    public ThreeFragment() {
    }

    public static ThreeFragment newInstance(String param1, String param2) {
        ThreeFragment fragment = new ThreeFragment();
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
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupTabLayout();
    }

    private void setupTabLayout() {
        viewPager.setOffscreenPageLimit(4);
        //ViewPager关联适配器
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getChildFragmentManager());
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

}
