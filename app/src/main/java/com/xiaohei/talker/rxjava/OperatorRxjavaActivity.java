package com.xiaohei.talker.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.xiaohei.talker.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class OperatorRxjavaActivity extends AppCompatActivity {
    private static final String TAG = "OperatorRxjavaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_rxjava);
/**
 *   1 通过create() 创建被观察者 Observable对象
 */
        Observable.create(new ObservableOnSubscribe<String>() {
            //传入参数 OnSubscribe 对象
            //当Observable 被订阅的时候 OnSubscribe的call()方法会自动被调用 ，即事件序列会按照设定的次序被触发
            //即观察者会依次调用对应的事件的复写方法从而响应事件
            //从而实现由被观察者向观察者的事件的传递  被调用者调用了观察者的方法 ，即观察者模式

            /***
             * 2 在复写的subscribe()定义需要发送的事件
             *
             */
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                // 通过 ObservableEmitter类对象 产生 & 发送事件
                // ObservableEmitter类介绍
                // a. 定义：事件发射器
                // b. 作用：定义需要发送的事件 & 向观察者发送事件
                // 注：建议发送事件前检查观察者的isUnsubscribed状态，以便在没有观察者时，让Observable停止发射数据
                emitter.onNext("栾");
                emitter.onNext("桂");
                emitter.onNext("明");
                emitter.onComplete();

            }
        }).subscribe(new Observer<String>() {
            /***
             * 在下边定义对应的响应事件
             */
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

/***
 *  1  - 通过just() 快速创建一个被观察者和发送事件
 *     - 应用场景 快速创建被观察者对象同时发送10个一下的事件
 */
        // 创建的时候传入1，2，3，4，5，6，7
        // 在创建之后发送这些对象 相当于执行了7次onNext()
        // 至此，一个Observable对象创建完毕，以下步骤仅为展示一个完整demo，可以忽略
        // 2. 通过通过订阅（subscribe）连接观察者和被观察者
        // 3. 创建观察者 & 定义响应事件的行为
        Observable.just(1, 2, 3, 4, 5, 6, 7).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");

            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "接收到了事件" + value);

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");

            }
        });
/***
 *  1  - 通过fromArray() 快速创建一个被观察者 创建的特点是直接发送传入的数组数据
 *     - 应用场景 快速创建被观察者对象同时发送10个一下的事件
 */
        // 设置需要的数组数据
        Integer[] items = {1, 2, 3, 4, 5, 6};
        //创建被观察这对象Observable 时传入数组 在创建之后会将数组转化为Observable对象 发送其中数据
        Observable.fromArray(items)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });
        // 注：
        // 可发送10个以上参数
        // 若直接传递一个list集合进去，否则会直接把list当做一个数据元素发送
/***
 *  1  - fromIterable() 快速创建一个被观察者 创建的特点是直接发送传入的List集合数据
 *     - 应用场景 1 快速创建被观察者对象同时发送10个以上的事件
 *               2 遍历集合的元素
 */
        //设置发送的集合
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(13);
        list.add(14);
        list.add(15);
        Observable.fromIterable(list).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
/***
 * // 下列方法一般用于测试使用
 *
 * <-- empty()  -->
 * // 该方法创建的被观察者对象发送事件的特点：仅发送Complete事件，直接通知完成
 * Observable observable1=Observable.empty();
 * // 即观察者接收后会直接调用onCompleted（）
 *
 * <-- error()  -->
 * // 该方法创建的被观察者对象发送事件的特点：仅发送Error事件，直接通知异常
 * // 可自定义异常
 * Observable observable2=Observable.error(new RuntimeException())
 * // 即观察者接收后会直接调用onError（）
 *
 * <-- never()  -->
 * // 该方法创建的被观察者对象发送事件的特点：不发送任何事件
 * Observable observable3=Observable.never();
 * // 即观察者接收后什么都不调用
 */
    }
}
