package com.example.tempdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.tempdemo.recycler.RecyclerViewActivity;
import com.example.tempdemo.restart.RestartAPPTool;

public class MainActivity extends AppCompatActivity {
    private ComponentName defaultComponent;
    private ComponentName testComponent;
    private PackageManager packageManager;
    private final static int UPDATE_SUCCESS = -1, MODE0_NORMAL = 0, MODE1_DOUBLE11 = 1;
    private int mode = UPDATE_SUCCESS;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_SUCCESS:
                    mode = UPDATE_SUCCESS;
                    break;
                case MODE0_NORMAL:
                    sendEmptyMessageDelayed(UPDATE_SUCCESS, 10 * 1000);
                    break;
                case MODE1_DOUBLE11:
                    sendEmptyMessageDelayed(UPDATE_SUCCESS, 10 * 1000);
                    break;
            }
        }
    };

    @Override
    public void finish() {
        super.finish();
        if (mHandler != null) {
            mHandler.removeMessages(UPDATE_SUCCESS);
            mHandler.removeMessages(MODE0_NORMAL);
            mHandler.removeMessages(MODE1_DOUBLE11);
            mHandler = null;
        }
        switch (mode) {
            case MODE0_NORMAL:
                disableComponent(defaultComponent);
                enableComponent(testComponent, false);
                enableComponent(defaultComponent, true);
                disableComponent(testComponent);
                break;
            case MODE1_DOUBLE11:
                enableComponent(defaultComponent, false);
                disableComponent(testComponent);
                disableComponent(defaultComponent);
                enableComponent(testComponent, true);
                break;
            default:
                Log.i("lzzz", "MainActivity类 -> onDestroy -> 不需要或者已改变图标");
                break;
        }
//        RestartAPPTool.restartAPP(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWeb();
        initIcon();
    }

    private void initIcon() {
        //拿到当前activity注册的组件名称MerchantApplication.javaMerchantApplication.java
        ComponentName componentName = getComponentName(); // 这个不要去更改, 否则会出现很多问题

        //拿到我们注册的MainActivity组件
        defaultComponent = new ComponentName(getBaseContext(), "com.example.tempdemo.default");  //拿到默认的组件
        //拿到我注册的别名test组件
        testComponent = new ComponentName(getBaseContext(), "com.example.tempdemo.test");

        packageManager = getApplicationContext().getPackageManager();

        findViewById(R.id.btn_normal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = MODE0_NORMAL;
                enableComponent(defaultComponent, false);
                disableComponent(testComponent);
            }
        });
        findViewById(R.id.btn_d11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = MODE1_DOUBLE11;
                disableComponent(defaultComponent);
                enableComponent(testComponent, false);
            }
        });
    }

    /**
     * 启用组件
     *
     * @param componentName
     */
    private void enableComponent(ComponentName componentName, boolean finish) {
        int state = packageManager.getComponentEnabledSetting(componentName);
        if (state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            //已经启用
            return;
        }
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                finish ? 0 : PackageManager.DONT_KILL_APP);
        // setComponentEnabledSetting方法的flag描述如下：可选的动作为
        // DONT_KILL_APP —— 不关闭APP
        // 0 —— 关闭APP刷新
    }

    /**
     * 禁用组件
     *
     * @param componentName
     */
    private void disableComponent(ComponentName componentName) {
        int state = packageManager.getComponentEnabledSetting(componentName);
        if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
            //已经禁用
            return;
        }
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    private void initWeb() {
        WebView web = findViewById(R.id.web);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
        web.setWebChromeClient(new WebChromeClient());
//        //开启调试模式
//        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WebView.setWebContentsDebuggingEnabled(true);
//        }
//
        WebSettings setting = web.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setDomStorageEnabled(true);
//        setting.setPluginState(WebSettings.PluginState.ON);
//        setting.setAppCacheEnabled(true);
//        setting.setBlockNetworkImage(false);
        web.loadUrl("https://static.banjoy.cn/html/3_0/studyPlanDetail.html?planId=11&stateType=0");
//        web.loadUrl("https://static.banjoy.cn/html/plan/20190109/11_9510.html");
    }

    public void recyclerDemo(View view) {
        RecyclerViewActivity.luanch(this);
    }
}
