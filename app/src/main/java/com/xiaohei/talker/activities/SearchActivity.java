package com.xiaohei.talker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xiaohei.common.app.Activity;
import com.xiaohei.talker.R;

public class SearchActivity extends Activity {
    public static final String EXTRA_TYPE = "EXTRA_TYPE";
    public static final int TYPE_USER =1;
    public static final int TYPE_GROUP =2;
    private int type;

    /***
     * 显示搜索界面
     * @param context  上下文
     * @param type  搜索类型
     */
    public static void show(Context context, int type){
        Intent intent = new Intent(context,SearchActivity.class);
        intent.putExtra(EXTRA_TYPE,type);
        context.startActivity(intent);

    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        type = bundle.getInt(EXTRA_TYPE);
        return type==TYPE_GROUP|| type== TYPE_USER;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_search;
    }
}
