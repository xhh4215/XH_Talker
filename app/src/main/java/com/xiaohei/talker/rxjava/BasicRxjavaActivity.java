package com.xiaohei.talker.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.xiaohei.talker.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/****
 * Rxjava框架的分步实现
 * @author 栾桂明
 * @date 2019年8月9日
 */

public class BasicRxjavaActivity extends AppCompatActivity {
    private static final String TAG = "Rxjava";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_rxjava);
        //步骤一  创建观察者   & 生产事件
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            复写subscribe（）定义要发送的事件
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                //通过 ObservableEmitter类的对象产生事件并通知观察者
                    // a. 定义 事件发射器
                    // b. 作用 定义要发送的事件， 像观察者发送事件
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onComplete();
            }
        });


          //步骤二 创建观察者Observer 并 定义响应事件的行为
        Observer<Integer> observer = new Observer<Integer>() {
            //定义Disposable变量
            private Disposable mDisposable;
            //通过复写对应的方法  响应被观察者
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
                // 初始化Disposable变量
                mDisposable = d;

            }
            // 默认最先调用复写的 onSubscribe（）

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "对Next事件"+ value +"作出响应"  );
                //使用dispose（） 实现断开观察者和被观察者的连接
                mDisposable.dispose();

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");

            }
        };
//         步骤三 通过订阅实现 观察者和被观察者的连接
        /****
         *     public final Disposable subscribe() {}
         *     // 表示观察者不对被观察者发送的事件作出任何响应（但被观察者还是可以继续发送事件）
         *
         *     public final Disposable subscribe(Consumer<? super T> onNext) {}
         *     // 表示观察者只对被观察者发送的Next事件作出响应
         *     public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {}
         *     // 表示观察者只对被观察者发送的Next事件 & Error事件作出响应
         *
         *     public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete) {}
         *     // 表示观察者只对被观察者发送的Next事件、Error事件 & Complete事件作出响应
         *
         *     public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete, Consumer<? super Disposable> onSubscribe) {}
         *     // 表示观察者只对被观察者发送的Next事件、Error事件 、Complete事件 & onSubscribe事件作出响应
         *
         *     public final void subscribe(Observer<? super T> observer) {}
         *     // 表示观察者对被观察者发送的任何事件都作出响应
         */
        observable.subscribe(observer);



        // Rxjava 的流式实现
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(1);
                e.onNext(1);
                e.onNext(1);
                e.onNext(1);
                e.onNext(1);
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            // 默认最先调用复写的 onSubscribe（）

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
