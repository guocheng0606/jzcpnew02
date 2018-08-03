package com.jiuzhou.guanwang.jzcp.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.base.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.Random;

public class List3Activity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.ll_group)
    LinearLayout ll_group;
    @ViewInject(R.id.btn01)
    Button btn01;
    @ViewInject(R.id.btn02)
    Button btn02;
    @ViewInject(R.id.btn03)
    Button btn03;
    @ViewInject(R.id.btn04)
    Button btn04;
    @ViewInject(R.id.btn05)
    Button btn05;
    @ViewInject(R.id.btn06)
    Button btn06;
    @ViewInject(R.id.tv_num)
    TextView tv_num;
    private Animation animation;
    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list3);
        ViewUtils.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("机选号码");

        animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setRepeatCount(6);//动画的反复次数
        animation.setFillAfter(true);//设置为true，动画转化结束后被应用
        getData(1);
        btn01.setOnClickListener(this);
        btn02.setOnClickListener(this);
        btn03.setOnClickListener(this);
        btn04.setOnClickListener(this);
        btn05.setOnClickListener(this);
        btn06.setOnClickListener(this);
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn01:
                getData(1);
                break;
            case R.id.btn02:
                getData(5);
                break;
            case R.id.btn03:
                getData(10);
                break;
            case R.id.btn04:
                getData(20);
                break;
            case R.id.btn05:
                ll_group.removeAllViews();
                break;
            case R.id.btn06:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(str);
                Toast.makeText(this, "已成功复制号码到剪切板", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void getData(int count){
        str = "";
        tv_num.setText(count+"注");
        ll_group.removeAllViews();
        for (int m=0;m< count;m++){
            LinearLayout ll = new LinearLayout(this);
            for (int i = 0; i < 3; i++) {
                TextView ball = new TextView(this);
                int num = new Random().nextInt(10);
                str += num+" ";
                ball.setText(""+num);
                ball.setTextSize(15);
                ball.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(92, 92);
                layoutParams.setMargins(5,5,5,5);
                ball.setLayoutParams(layoutParams);
                //ball.setPadding(0, 0, 0, 4);
                ball.setTextColor(getResources().getColor(R.color.white));
                ball.setBackgroundResource(R.drawable.shape_circle_blue_bg);

                ball.startAnimation(animation);//開始动画

                ll.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ll.setLayoutParams(lp);
                ll.addView(ball);
            }
            ll_group.addView(ll);
        }

    }

}
