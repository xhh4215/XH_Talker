package com.xiaohei.talker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xiaohei.common.app.Activity;
import com.xiaohei.common.widget.PortraitView;
import com.xiaohei.talker.R;
import com.xiaohei.talker.fragment.main.ActiveFragment;
import com.xiaohei.talker.fragment.main.ContactFragment;
import com.xiaohei.talker.fragment.main.GroupFragment;
import com.xiaohei.talker.helper.NavHelper;

import net.qiujuer.genius.ui.Ui;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener, NavHelper.OnTabChangedListener<Integer> {
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
    @BindView(R.id.im_search)
    ImageView imSearch;
    @BindView(R.id.btn_action)
    FloatingActionButton mAction;

    private NavHelper<Integer> mNavHelper;

    /***
     * MainActivity 显示的入口
     * @param context
     */
      public static void show(Context context){
          context.startActivity(new Intent(context,MainActivity.class));
      }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //初始化底部菜单的工具类
        mNavHelper = new NavHelper(this, R.id.lay_container, getSupportFragmentManager(), this);
        mNavHelper.add(R.id.action_home, new NavHelper.Tab<>(ActiveFragment.class, R.string.title_home)).add(R.id.action_group, new NavHelper.Tab<>(GroupFragment.class, R.string.title_group)).add(R.id.action_contact, new NavHelper.Tab<>(ContactFragment.class, R.string.title_contact));
        mNavigation.setOnNavigationItemSelectedListener(this);
        //Gilde图片加载框架
        Glide.with(this)
                //要加载的图片
                .load(R.drawable.bg_src_morning)
                //剪切的方式
                .centerCrop()
                //图片加载的目标
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
        // 从底部导航中接管我们的Menu
        Menu menu = mNavigation.getMenu();
        // 触发首次选中
        menu.performIdentifierAction(R.id.action_home, 0);


    }

    @OnClick(R.id.im_search)
    public void onSearchMenuClick() {
        Toast.makeText(this, "点击了查询按钮", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btn_action)
    public void onActionClick() {
        AccountActivity.show(MainActivity.this);
    }

    /***
     * 当底部的菜单按钮被选中的时候回调的方法
     * @param menuItem
     * @return 代表我们能够处理这个点击
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //转化底部菜单的处理逻辑到工具类中
        return mNavHelper.performClickMenu(menuItem.getItemId());
    }

    /***
     * NavHelper处理后的回调方法
     * @param newTab  新的Tab
     * @param oldTab  旧的Tab
     */
    @Override
    public void onTabChanged(NavHelper.Tab<Integer> newTab, NavHelper.Tab<Integer> oldTab) {
        //从额外字段中取出我们的title资源id
        mTxtTitle.setText(newTab.extra);
        //对浮动按钮的隐藏与显示的动画
        float transY = 0;
        float rotation = 0;
        if (Objects.equals(newTab.extra, R.string.title_home)) {
            //主界面时隐藏
            transY = Ui.dipToPx(getResources(), 76);
        } else {
            //设置默认显示
            if (Objects.equals(newTab.extra, R.string.title_group)) {
                mAction.setImageResource(R.drawable.ic_group_add);
                rotation = -360;

            } else {
                mAction.setImageResource(R.drawable.ic_contact_add);
                rotation = 360;

            }
        }
        mAction.animate().rotation(rotation).translationY(transY).setInterpolator(new AnticipateOvershootInterpolator(1)).setDuration(480).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
