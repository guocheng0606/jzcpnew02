package com.jiuzhou.guanwang.jzcp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.jiuzhou.guanwang.jzcp.utils.CacheActivity;
import com.jiuzhou.guanwang.jzcp.utils.PrefUtils;


public class MyInstalledReceiver extends BroadcastReceiver {

    private String bagname;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            bagname = PrefUtils.getString(context, "package", "");
            if (packageName.equals("package:"+bagname)){
                Uri packageUri = Uri.parse("package:"+context.getPackageName());
                Intent i = new Intent(Intent.ACTION_DELETE,packageUri);
                context.startActivity(i);
                CacheActivity.finishActivity();
            }
        }
    }
}
