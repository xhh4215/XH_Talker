package com.xiaohei.talker.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.xiaohei.common.app.Activity;
import com.xiaohei.common.app.Fragment;
import com.xiaohei.talker.R;
import com.xiaohei.talker.fragment.account.UpdateinfoFragment;
import com.yalantis.ucrop.UCrop;

/***
 * @time 2019年8月1日
 * @author 栾桂明
 * @use 账户界面
 */

public class AccountActivity extends Activity {
    private Fragment mCurrentFragment;

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
        mCurrentFragment = new UpdateinfoFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.lay_container, mCurrentFragment)
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCurrentFragment.onActivityResult(requestCode, resultCode, data);
    }

}
