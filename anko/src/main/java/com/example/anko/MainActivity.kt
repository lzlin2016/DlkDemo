package com.example.anko

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.fm.openinstall.OpenInstall
import com.fm.openinstall.listener.AppInstallAdapter
import com.fm.openinstall.listener.AppWakeUpAdapter
import com.fm.openinstall.model.AppData
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast


/**
 *
 * <p>
 * 类的描述:
 * 创建时间: 2019/7/31 9:44
 * 修改备注: 在唤醒页面中如下调用相关代码，获取web端传过来的动态参数
 */

class MainActivity : AppCompatActivity() {
    lateinit var textView: TextView
    private var info: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this) // 绑定布局

        textView.onClick {
            toast("我是在activity 中绑定的点击事件 ${textView.text}")
            TestActivity.start(this@MainActivity)
            textView.text = info
        }

        //获取唤醒参数
        OpenInstall.getWakeUp(intent, wakeUpAdapter)
        //获取OpenInstall安装数据
        OpenInstall.getInstall(object : AppInstallAdapter() {
            override fun onInstall(appData: AppData) {
                //获取渠道数据
                val channelCode = appData.getChannel()
                //获取自定义数据
                val bindData = appData.getData()
                Log.e("OpenInstall", "getInstall : installData = $appData")
                info = "OpenInstall getWakeUp : wakeupData = $appData"
            }
        })
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // 此处要调用，否则App在后台运行时，会无法截获
        OpenInstall.getWakeUp(intent, wakeUpAdapter)
    }

    private var wakeUpAdapter: AppWakeUpAdapter? = object : AppWakeUpAdapter() {
        override fun onWakeUp(appData: AppData) {
            //获取渠道数据
            val channelCode = appData.getChannel()
            //获取绑定数据
            val bindData = appData.getData()
            Log.e("OpenInstall", "getWakeUp : wakeupData = $appData")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        wakeUpAdapter = null
    }
}
