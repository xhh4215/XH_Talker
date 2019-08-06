package com.xiaohei.common.app;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;


/**
 * @author 栾桂明
 * @time 2019年8月1日
 * @use 所有的activity集成的基本activity
 */

public abstract class Activity extends AppCompatActivity {
    /***
     * 首次执行的时候会调用的方法
     * @param savedInstanceState   保存数据的一个参数
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 在界面未初始化之前调用的初始化窗口
        initWidows();
        // getIntent()获取启动这个activity的intent对象
        // 获取添加到intent里边的所有数据
        if (initArgs(getIntent().getExtras())) {
            // 得到界面Id并设置到Activity界面中
            int layId = getContentLayoutId();
            //设置当前的activity的界面
            setContentView(layId);
            //初始化控件
            initWidget();
            //初始化数据
            initData();
        } else {
            finish();
        }
    }

    /**
     * 初始化窗口
     */
    protected void initWidows() {

    }
    /**
     * 初始化相关参数
     *
     * @param bundle 参数Bundle
     * @return 如果参数正确返回True，错误返回False
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }
    /**
     * 得到当前界面的资源文件Id
     *
     * @return 资源文件Id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget() {

        ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /***
     * 界面导航返回时执行的方法
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        // 当点击界面导航返回时，Finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    /***
     * backpress按键点击执行的方法
     */
    @Override
    public void onBackPressed() {
        // 得到当前Activity下的所有Fragment
        @SuppressLint("RestrictedApi")
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        // 判断fragments是不是空的 并且fragments个数大于0
        if (fragments != null && fragments.size() > 0) {
            //遍历fragments
            for (Fragment fragment : fragments) {
                // 判断是否为我们能够处理的Fragment类型
                if (fragment instanceof com.xiaohei.common.app.Fragment) {
                    // 判断是否拦截了返回按钮
                    if (((com.xiaohei.common.app.Fragment) fragment).onBackPressed()) {
                        // 如果有直接Return
                        return;
                    }
                }
            }
        }

        super.onBackPressed();
        finish();
    }
}
