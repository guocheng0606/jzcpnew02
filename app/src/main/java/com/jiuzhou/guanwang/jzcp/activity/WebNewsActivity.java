package com.jiuzhou.guanwang.jzcp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.jiuzhou.guanwang.jzcp.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class WebNewsActivity extends AppCompatActivity {

    @ViewInject(R.id.llNetError)
    LinearLayout llNetError;
    @ViewInject(R.id.ll_load)
    LinearLayout ll_load;
    @ViewInject(R.id.web)
    WebView mWebview;
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    private WebSettings mWebSettings;
    private String title = "";
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_news);
        ViewUtils.inject(this);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        mWebSettings = mWebview.getSettings();
        //mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);//允许js弹出窗口
        //mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        mWebSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        mWebSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        mWebSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        mWebSettings.setDisplayZoomControls(false); //隐藏webview缩放按钮
        mWebview.loadUrl(url);

        //设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient() {
            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                // android 6.0 以下通过title获取
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    if (title.contains("404") || title.contains("500") || title.contains("Error")) {
                        view.setVisibility(View.GONE);
                    }
                }
            }

            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        //设置WebViewClient类
        mWebview.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                ll_load.setVisibility(View.VISIBLE);
            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                ll_load.setVisibility(View.GONE);

                /*//编写 javaScript方法
                String javascript =  "javascript:function hideOther() {" +
                        "document.getElementsById('adPlaceBottom').style.display='none';" +
                        "document.getElementsById('picture').style.display='none';" +
                        "}";

                //创建方法
                view.loadUrl(javascript);

                //加载方法
                view.loadUrl("javascript:hideOther();");*/
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
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

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);
                mWebview.setVisibility(View.GONE);
                llNetError.setVisibility(View.VISIBLE);
            }
        });
        mWebview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                        String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
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


}
