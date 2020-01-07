package com.xiaohei.talker.activities;


import android.content.Intent;

import com.xiaohei.common.app.Activity;
import com.xiaohei.common.app.Fragment;
import com.xiaohei.talker.R;
import com.xiaohei.talker.fragment.user.UpdateinfoFragment;

public class UserActivity extends Activity {
    private Fragment mCurrentFragment;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_user;
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
        super.onActivityResult(requestCode,resultCode,data);
        mCurrentFragment.onActivityResult(requestCode, resultCode, data);
    }

}
