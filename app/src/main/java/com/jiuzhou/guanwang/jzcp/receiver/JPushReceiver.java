package com.jiuzhou.guanwang.jzcp.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Bundle bundle = intent.getExtras();
			Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
				//send the Registration Id to your server...

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
				Log.d(TAG, "---------[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
				CC(context,bundle.getString(JPushInterface.EXTRA_MESSAGE));
			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				Log.d(TAG, "[MyReceiver] 接收到推送下来的通知"+bundle.getString("url"));
				int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
				Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

				String title=bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
				Log.d("title",title);
				String text=bundle.getString(JPushInterface.EXTRA_ALERT);//获取通知内容
				Log.d("通知内容",text);
				//JPushInterface.EXTRA_EXTRA);//获取附加字段 是一个数据


			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
				Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
				intent = new Intent(context, MainActivity.class);
				intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..


			} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
			} else {
				Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			}
		} catch (Exception e){

		}

	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

	/** NotificationCompat 构造器*/
	Builder mBuilder;
	/** Notification管理 */
	public NotificationManager mNotificationManager;
	/** 通知栏按钮广播 */
	public void CC(Context context, String content){
         //设置显示通知时的默认的发声、震动、Light效果


        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		RemoteViews view_custom = new RemoteViews(context.getPackageName(), R.layout.view_custom);
		view_custom.setTextViewText(R.id.tv_custom_content, content);
		mBuilder = new Builder(context);
		mBuilder.setContent(view_custom)
				.setContentIntent(getDefalutIntent(context, Notification.FLAG_AUTO_CANCEL))
				.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
				.setTicker("")
				.setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
				.setOngoing(false)//不是正在进行的   true为正在进行  效果和.flag一样
				.setAutoCancel(true) //点击关闭
				.setSmallIcon(R.drawable.appicon);
		Intent intent =new Intent (context,MainActivity.class);
		PendingIntent pi = PendingIntent.getActivities(context, 0, new Intent[]{intent}, PendingIntent.FLAG_CANCEL_CURRENT);
		mBuilder.setContentIntent(pi);
		Notification notify = mBuilder.build();
		notify.contentView = view_custom;
        notify.defaults = Notification.DEFAULT_VIBRATE;
        notify.flags = Notification.FLAG_ONGOING_EVENT;//FLAG_ONGOING_EVENT 在顶部常驻，可以调用下面的清除方法去除  FLAG_AUTO_CANCEL  点击和清理可以去调

        //如果系统版本 >= Android 4.1，设置大视图 RemoteViews
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.view_custom);
            view.setTextViewText(R.id.tv_custom_content,content);
            //textview marquee property is useless for bigContentView
            notify.bigContentView = view;
        }
		mNotificationManager.notify(101, notify);

	}
	/**
	 * @获取默认的pendingIntent,为了防止2.3及以下版本报错
	 * @flags属性:
	 * 在顶部常驻:Notification.FLAG_ONGOING_EVENT
	 * 点击去除： Notification.FLAG_AUTO_CANCEL
	 */
	public PendingIntent getDefalutIntent(Context context, int flags){
		PendingIntent pendingIntent= PendingIntent.getActivity(context, 1, new Intent(), flags);
		return pendingIntent;
	}
}
