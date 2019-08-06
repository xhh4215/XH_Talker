package com.xiaohei.talker.activities;

import android.content.Context;
import android.content.Intent;

import com.xiaohei.common.app.Activity;
import com.xiaohei.talker.R;
import com.xiaohei.talker.fragment.account.UpdateinfoFragment;

/***
 * @time 2019年8月1日
 * @author 栾桂明
 * @use 账户界面
 */

public class AccountActivity extends Activity {
    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    /****
     * 获取界面加载的布局文件的id
     * @return 布局文件id
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    /***
     * 初始化当前界面的控件
     */
    @Override
    protected void initWidget() {
        super.initWidget();
        getSupportFragmentManager().beginTransaction().add(R.id.lay_container, new UpdateinfoFragment())
                .commit();
    }
}
