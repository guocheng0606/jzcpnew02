package com.jiuzhou.guanwang.jzcp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.fragment.FiveFragment;
import com.jiuzhou.guanwang.jzcp.fragment.FourFragment;
import com.jiuzhou.guanwang.jzcp.fragment.OneFragment;
import com.jiuzhou.guanwang.jzcp.fragment.ThreeFragment;
import com.jiuzhou.guanwang.jzcp.fragment.TwoFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class HomeActivity extends AppCompatActivity {

    @ViewInject(R.id.tab)
    PageNavigationView tab;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;
    private FourFragment fourFragment;
    private FiveFragment fiveFragment;
    private NavigationController navigationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ViewUtils.inject(this);
        fragmentManager = getSupportFragmentManager();
        setTabSelection(0);
        initBottomNavigation();
    }

    private void initBottomNavigation() {
        navigationController = tab.custom()
                .addItem(newItem(R.drawable.mh_buy_normal,R.drawable.mh_buy_pressed,"首页"))
                .addItem(newItem(R.drawable.mh_user_normal,R.drawable.mh_user_pressed,"名师"))
                .addItem(newItem(R.drawable.mh_dslt_normal,R.drawable.mh_dslt_pressed,"开奖"))
                .addItem(newItem(R.drawable.mh_find_normal,R.drawable.mh_find_pressed,"技巧"))
                .addItem(newItem(R.drawable.mh_score_normal,R.drawable.mh_score_pressed,"时讯"))
                .build();
        //navigationController.setSelect(0);
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                //选中时触发
                setTabSelection(index);
            }

            @Override
            public void onRepeat(int index) {
                //System.out.println(index);
                //setTabSelection(index);
            }
        });
    }

    private void setTabSelection(int i) {
        fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        switch (i){
            case 0:
                if(oneFragment == null){
                    oneFragment = new OneFragment();
                    fragmentTransaction.add(R.id.fl_layout,oneFragment);
                }else{
                    fragmentTransaction.show(oneFragment);
                }
                break;
            case 1:
                if(twoFragment == null){
                    twoFragment = new TwoFragment();
                    fragmentTransaction.add(R.id.fl_layout,twoFragment);
                }else{
                    fragmentTransaction.show(twoFragment);
                }
                break;
            case 2:
                if(threeFragment == null){
                    threeFragment = new ThreeFragment();
                    fragmentTransaction.add(R.id.fl_layout,threeFragment);
                }else{
                    fragmentTransaction.show(threeFragment);
                }
                break;
            case 3:
                if(fourFragment == null){
                    fourFragment = new FourFragment();
                    fragmentTransaction.add(R.id.fl_layout,fourFragment);
                }else{
                    fragmentTransaction.show(fourFragment);
                }
                break;
            case 4:
                if(fiveFragment == null){
                    fiveFragment = new FiveFragment();
                    fragmentTransaction.add(R.id.fl_layout,fiveFragment);
                }else{
                    fragmentTransaction.show(fiveFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if(oneFragment != null) fragmentTransaction.hide(oneFragment);
        if(twoFragment != null) fragmentTransaction.hide(twoFragment);
        if(threeFragment != null) fragmentTransaction.hide(threeFragment);
        if(fourFragment != null) fragmentTransaction.hide(fourFragment);
        if(fiveFragment != null) fragmentTransaction.hide(fiveFragment);
    }

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(0xFFE23A3A);
        return normalItemView;
    }



    long defTime = 0;
    @Override
    public void onBackPressed() {
        if (defTime==0||(System.currentTimeMillis() - defTime)>2000 ){
            Toast.makeText(this,"再按一次离开",Toast.LENGTH_SHORT).show();
            defTime = System.currentTimeMillis();
        }else {
            super.onBackPressed();
        }
    }
}
