package com.xiaohei.talker;


import com.example.factory.Factory;
import com.igexin.sdk.PushManager;
import com.xiaohei.common.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Factory.setup();
        PushManager.getInstance().initialize(this);
    }
}
