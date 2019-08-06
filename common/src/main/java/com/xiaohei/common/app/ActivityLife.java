package com.xiaohei.common.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/***
 * Activity存在的三种状态
 *  1 继续：此activity 位于屏幕的前台 并具有用户的焦点(有时候也称运行中)
 *
 *  2 暂停：另一个 Activity 位于屏幕前台并具有用户焦点，但此 Activity 仍可见。
 *         也就是说，另一个 Activity 显示在此 Activity 上方，并且该 Activity 部分透明或未覆盖整个屏幕。
 *         暂停的 Activity 处于完全活动状态（Activity 对象保留在内存中，它保留了所有状态和成员信息，
 *         并与窗口管理器保持连接），但在内存极度不足的情况下，可能会被系统终止。
 *  3 停止：该 Activity 被另一个 Activity 完全遮盖（该 Activity 目前位于“后台”）。 已停止的 Activity 同样仍处于活动状态
 *        （Activity 对象保留在内存中，它保留了所有状态和成员信息，但未与窗口管理器连接）。
 *         不过，它对用户不再可见，在他处需要内存时可能会被系统终止。
 * 如果 Activity 处于暂停或停止状态，系统可通过要求其结束（调用其 finish() 方法）或直接终止其进程，将其从内存中删除。
 * （将其结束或终止后）再次打开 Activity 时，必须重建。
 *  */
public class ActivityLife extends AppCompatActivity {


    // 您的 Activity 应在 onCreate() 中执行“全局”状态设置（例如定义布局），并释放 onDestroy() 中的所有其余资源
    /***
     * Activity必须实现的方法您必须实现此方法。系统会在创建您的 Activity 时调用此方法。您应该在实现内初始化 Activity 的必需组件。
     * 最重要的是，您必须在此方法内调用 setContentView()，以定义 Activity 用户界面的布局。
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全局的状态设置   Activity的整个声明周期
    }


     // 在onStart和onStop之间用户可以看到activity并与之交互

    /***
     * 在 Activity 即将对用户可见之前调用。
     */
    @Override
    protected void onStart() {
        super.onStart();
        //Activity的可见声明周期
    }
    /***
     * 当Activity由可见变为不可见的时候调用的方法  Activity被完全的遮挡住
     */
    @Override
    protected void onStop() {
        super.onStop();
        //Activity的可见声明周期

    }

    /**
     * 在 Activity 已停止并即将再次启动前调用。
     */
    @Override
    protected void onRestart() {
        super.onRestart();
    }
    // onResume和onPause之间是Activity的前台生命周期
   // 在这段时间，Activity 位于屏幕上的所有其他 Activity 之前，并具有用户输入焦点

    /***
     * 在 Activity 即将开始与用户进行交互之前调用。 此时，Activity 处于 Activity 堆栈的顶层，并具有用户输入焦点。
     */
    @Override
    protected void onResume() {
        super.onResume();

    }

    /***
     * 系统将此方法作为用户离开 Activity 的第一个信号（但并不总是意味着 Activity 会被销毁）进行调用。 您通常应该在此方法内确认在当前用
     * 户会话结束后仍然有效的任何更改（因为用户可能不会返回）。
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /***
     * Activity销毁的时候回调的方法
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放占用的资源   Activity的整个声明周期
    }


}
