package com.example.anko

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import com.fm.openinstall.OpenInstall


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (isMainProcess()) {
            OpenInstall.init(this)
        }
    }

    /**
     * 当应用存在多个进程时，确保只在主进程进行初始化
     */
    fun isMainProcess(): Boolean {
        val pid = android.os.Process.myPid()
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (appProcess in activityManager.runningAppProcesses) {
            if (appProcess.pid == pid) {
                return applicationInfo.packageName == appProcess.processName
            }
        }
        return false
    }
}