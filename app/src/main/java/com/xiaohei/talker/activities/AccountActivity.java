package com.xiaohei.talker.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.core.graphics.drawable.DrawableCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.xiaohei.common.app.Activity;
import com.xiaohei.common.app.Fragment;
import com.xiaohei.talker.R;
import com.xiaohei.talker.fragment.account.AccountTrigger;
import com.xiaohei.talker.fragment.account.LoginFragment;
import com.xiaohei.talker.fragment.account.RegisterFragment;

import net.qiujuer.genius.res.Resource;
import net.qiujuer.genius.ui.compat.UiCompat;

import butterknife.BindView;

/***
 * @time 2019年8月1日
 * @author 栾桂明
 * @use 账户界面
 */

public class AccountActivity extends Activity implements AccountTrigger {
    private Fragment mCurrentFragment;
    private Fragment mLoginFragment;
    private Fragment mReisterFragment;
    @BindView(R.id.im_bg)
    ImageView mBg;

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
        mCurrentFragment = mLoginFragment = new LoginFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.lay_container, mCurrentFragment)
                .commit();
        Glide.with(this).load(R.drawable.bg_src_tianjin).centerCrop().into(new ViewTarget<ImageView, GlideDrawable>(mBg) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                 Drawable drawable = resource.getCurrent();
                 drawable = DrawableCompat.wrap(drawable);
                 drawable.setColorFilter(UiCompat.getColor(getResources(),R.color.colorAccent), PorterDuff.Mode.SCREEN);
                 this.view.setImageDrawable(drawable);
            }
        });
    }


    @Override
    public void triggerView() {
        Fragment fragment;
        if (mCurrentFragment==mLoginFragment){
            if (mReisterFragment==null){
                mReisterFragment = new RegisterFragment();
            }
            fragment = mReisterFragment;

        }else{
            fragment = mLoginFragment;
        }
        mCurrentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.lay_container,fragment).commit();
    }
}
