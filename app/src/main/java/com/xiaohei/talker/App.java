package com.xiaohei.talker;


import com.example.factory.Factory;
import com.igexin.sdk.PushManager;
import com.xiaohei.common.app.Application;
import com.xiaohei.talker.services.MyIntentService;
import com.xiaohei.talker.services.MyPushService;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Factory.setup();
        PushManager.getInstance().initialize(this, MyPushService.class);
        PushManager.getInstance().registerPushIntentService(this, MyIntentService.class);
    }
}
