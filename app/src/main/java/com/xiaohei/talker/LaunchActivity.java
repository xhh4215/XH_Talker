package com.xiaohei.talker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Property;
import android.view.View;

import com.example.factory.prisistence.Account;
import com.xiaohei.common.app.Activity;
import com.xiaohei.talker.activities.MainActivity;
import com.xiaohei.talker.fragment.assist.PermissionsFragment;

import net.qiujuer.genius.res.Resource;
import net.qiujuer.genius.ui.compat.UiCompat;


public class LaunchActivity extends Activity {
    private ColorDrawable mBgDrawable;

    @Override
    protected void initWidget() {
        super.initWidget();
        View root = findViewById(R.id.activity_launch);
        int color = UiCompat.getColor(getResources(),R.color.colorAccent);
        ColorDrawable colorDrawable = new ColorDrawable(color);
        root.setBackground(colorDrawable);
        mBgDrawable = colorDrawable;


    }

    @Override
    protected void initData() {
        super.initData();
        startAnim(0.5f, new Runnable() {
            @Override
            public void run() {
                awaitPushReceiverId();
            }
        });
    }

    private void awaitPushReceiverId(){
      if (!TextUtils.isEmpty(Account.getPushId())){
          skip();
          return;
      }
      getWindow().getDecorView().postDelayed(new Runnable() {
          @Override
          public void run() {
              awaitPushReceiverId();
          }
      },500);
    }
   private void skip(){
        startAnim(1f, new Runnable() {
            @Override
            public void run() {
                readllySkip();
            }
        });
   }

   public void readllySkip(){
       if (PermissionsFragment.haveAll(this,getSupportFragmentManager())){
           MainActivity.show(this);
           finish();
       }
   }
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void startAnim(float endProgress,final Runnable endCallback){
          int finalColor = Resource.Color.WHITE;
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
          int endColor = (int)argbEvaluator.evaluate(endProgress,mBgDrawable.getColor(),finalColor);
          ValueAnimator valueAnimator = ObjectAnimator.ofObject(this,property,argbEvaluator,endColor);
          valueAnimator.setDuration(1500);
          valueAnimator.setIntValues(mBgDrawable.getColor(),endColor);
          valueAnimator.addListener(new AnimatorListenerAdapter() {
              @Override
              public void onAnimationEnd(Animator animation) {
                  super.onAnimationCancel(animation);
                  endCallback.run();
              }
          });
          valueAnimator.start();

    }


    private  final Property<LaunchActivity,Object> property = new Property<LaunchActivity, Object>(Object.class,"color") {
        @Override
        public Object get(LaunchActivity object) {
            return  object.mBgDrawable.getColor();
        }

        @Override
        public void set(LaunchActivity object, Object value) {
       object.mBgDrawable.setColor((Integer) value);
        }
    };
}
