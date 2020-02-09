package com.xiaohei.common.app;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.xiaohei.common.R;

public  abstract class ToolBarActivity extends Activity {
    private Toolbar mToolbar;
    @Override
    protected void initWidget() {
        super.initWidget();
        initToolbar((Toolbar) findViewById(R.id.toolbar));
    }

    public void initToolbar(Toolbar toolbar){
        mToolbar = toolbar;
        if (toolbar!=null){
            setSupportActionBar(toolbar);
        }
        initTitleNeedBack();
    }

    protected void initTitleNeedBack(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
}
