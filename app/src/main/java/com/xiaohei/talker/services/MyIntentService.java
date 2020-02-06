package com.xiaohei.talker.services;

import android.content.Context;

import com.example.factory.Factory;
import com.example.factory.data.helper.AccountHelper;
import com.example.factory.prisistence.Account;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

public class MyIntentService extends GTIntentService {
    public MyIntentService() {
    }

    @Override
    public void onReceiveServicePid(Context context, int i) {

    }
    // 接收 cid
    @Override
    public void onReceiveClientId(Context context, String s) {
        onClientInit(s);

    }
    // 处理透传消息
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {

    }
    // cid 离线上线通知

    @Override
    public void onReceiveOnlineState(Context context, boolean b) {

    }
    // 各种事件处理回执

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage message) {

    }
    // 通知到达，只有个推通道下发的通知会回调此方法

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {

    }
    // 通知点击，只有个推通道下发的通知会回调此方法

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    /**
     * 当Id初始化的试试
     *
     * @param cid 设备Id
     */
    private void onClientInit(String cid) {
        // 设置设备Id
        Account.setPushId(cid);
        if (Account.isLogin()) {
            // 账户登录状态，进行一次PushId绑定
            // 没有登录是不能绑定PushId的
            AccountHelper.bindPush(null);
        }
    }

    /**
     * 消息达到时
     *
     * @param message 新消息
     */
    private void onMessageArrived(GTTransmitMessage message) {
        // 交给Factory处理
        Factory.dispatchPush(message);
    }

}
