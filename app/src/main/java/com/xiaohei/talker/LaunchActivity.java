package com.xiaohei.talker;
import com.xiaohei.common.app.Activity;
import com.xiaohei.talker.activities.MainActivity;
import com.xiaohei.talker.fragment.assist.PermissionsFragment;



public class LaunchActivity extends Activity {
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PermissionsFragment.haveAll(this,getSupportFragmentManager())){
            MainActivity.show(this);
            finish();
        }
    }
}
