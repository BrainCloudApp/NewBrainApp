package com.lmq.common;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.util.NIMUtil;

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }
    /**
     * 注意：每个进程都会创建自己的Application 然后调用onCreate() 方法，
     * 如果用户有自己的逻辑需要写在Application#onCreate()（还有Application的其他方法）中，一定要注意判断进程，不能把业务逻辑写在core进程，
     * 理论上，core进程的Application#onCreate()（还有Application的其他方法）只能做与im sdk 相关的工作
     */
    public void onCreate() {
        // ... your codes
        super.onCreate();
        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
        NIMClient.init(this, null, null);

        // ... your codes
        if (NIMUtil.isMainProcess(this)) {
            // 注意：以下操作必须在主进程中进行
            // 1、UI相关初始化操作
            NimUIKit.init(this);
            // 2、相关Service调用
        }
    }

}
