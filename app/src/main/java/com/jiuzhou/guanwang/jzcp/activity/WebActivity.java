package com.jiuzhou.guanwang.jzcp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.jiuzhou.guanwang.jzcp.R;


public class WebActivity extends AppCompatActivity {

    WebView mWebview;
    WebSettings mWebSettings;
    private String url;
    private LinearLayout loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        url = getIntent().getStringExtra("url");
        mWebview = (WebView) findViewById(R.id.web);
        loading = (LinearLayout) findViewById(R.id.ll_load);
        mWebSettings = mWebview.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);//允许js弹出窗口

        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        mWebSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        mWebSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        mWebSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        mWebSettings.setDisplayZoomControls(false); //隐藏webview缩放按钮
        mWebview.loadUrl(url);

        mWebview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        //设置不用系统浏览器打开,直接显示在当前Webview
//        mWebview.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url){
//                if(url.startsWith("http:") || url.startsWith("https:")){
//                    return false;
//                }
//                try{
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(intent);
//                }catch (Exception e){
//
//                }
//                return true;
//            }
//        });

        //设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient() {




            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {


            }


            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }


        });


        //设置WebViewClient类
        mWebview.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loading.setVisibility(View.VISIBLE);

            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                loading.setVisibility(View.GONE);


            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
//                    view.loadUrl(url);
                    return false;
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {

                }
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

                // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
                // super.onReceivedSslError(view, handler, error);

                // 接受所有网站的证书，忽略SSL错误，执行访问网页
                handler.proceed();
            }
        });

    }


    //销毁Webview
    @Override
    protected void onDestroy() {
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();

            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            // 返回上一页面
            mWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            mWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




}
