package com.xiaohei.talker.activities;

import android.content.Context;
import android.content.Intent;

import com.xiaohei.common.app.Activity;
import com.xiaohei.talker.R;
import com.xiaohei.talker.fragment.account.UpdateinfoFragment;

public class AccountActivity extends Activity {

    /***
     * 账户activity显示的入口
     * @param context
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        getSupportFragmentManager().beginTransaction().add(R.id.lay_container, new UpdateinfoFragment())
                .commit();
    }
}
