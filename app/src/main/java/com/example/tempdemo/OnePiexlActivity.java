package com.example.tempdemo;

/**
 * OnePiexlActivity.java
 * <p>
 * 类的描述: 创建我们的1像素的Activity:
 * 创建时间: 2019/10/17 15:48
 * 修改备注:
 */

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.tempdemo.restart.RestartAPPTool;

/**
 * Created by Administrator on 2017/7/10.
 */

public class OnePiexlActivity extends Activity {

    private BroadcastReceiver endReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置1像素
        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.height = 1;
        params.width = 1;
        window.setAttributes(params);
//
//        //结束该页面的广播
//        endReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                finish();
//            }
//        };
//        registerReceiver(endReceiver, new IntentFilter("finish"));
//        //检查屏幕状态
//        checkScreen();
//        finish();
        if (getIntent() == null || (getIntent() != null  && !getIntent().getBooleanExtra("isRestart",false))) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    moveTaskToBack(true);
                }
            }, 500);
        }
    }

    boolean isFirst = true;

    @Override
    protected void onResume() {
        super.onResume();
//        checkScreen();
        if (isFirst) {
            isFirst = false;
        } else {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    /**
     * 检查屏幕状态  isScreenOn为true  屏幕“亮”结束该Activity
     */
    private void checkScreen() {
        PowerManager pm = (PowerManager) OnePiexlActivity.this.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
        if (isScreenOn) {
            finish();
        }
    }

//    @Override
//    protected void onDestroy() {
//        RestartAPPTool.restartAPP(this);
//        super.onDestroy();
//    }
}