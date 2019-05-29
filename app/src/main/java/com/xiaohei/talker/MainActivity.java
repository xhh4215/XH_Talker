package com.xiaohei.talker;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xiaohei.common.app.Activity;
import com.xiaohei.common.widget.PortraitView;

import butterknife.BindView;

import butterknife.OnClick;

public class MainActivity extends Activity {
    @BindView(R.id.appbar)
    View mLayAppbar;
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.lay_container)
    FrameLayout mLayContainer;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        Glide.with(this)
                .load(R.drawable.bg_src_morning)
                .centerCrop()
                .into(new ViewTarget<View, GlideDrawable>(mLayAppbar) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(resource.getCurrent());
                    }
                });

    }

    @Override
    protected void initData() {
        super.initData();

    }

    @OnClick(R.id.im_search)
    public void onSearchMenuClick() {
        Toast.makeText(this, "点击了查询按钮", Toast.LENGTH_LONG).show();
    }
    @OnClick(R.id.btn_action)
    public void onActionClick() {
    }
}
