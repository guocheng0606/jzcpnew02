package com.jiuzhou.guanwang.jzcp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自动延展ListView，用于嵌套入ScrollView时避免高度计算错误导致列表显示不全的问题出现
 *
 * @author hp
 */
public class AutoExpandListView extends ListView {

  public AutoExpandListView(Context context) {
    super(context);
    // TODO Auto-generated constructor stub
  }

  public AutoExpandListView(Context context, AttributeSet attrs) {
    super(context, attrs);
    // TODO Auto-generated constructor stub
  }

  public AutoExpandListView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    // TODO Auto-generated constructor stub
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
        MeasureSpec.AT_MOST);
    super.onMeasure(widthMeasureSpec, expandSpec);
  }

}
