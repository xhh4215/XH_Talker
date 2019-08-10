package com.xiaohei.talker.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xiaohei.talker.R;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MoreActivity extends AppCompatActivity {
    private Button button;
    private Button interval;
    private Integer i = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        button = findViewById(R.id.buttonid);
        interval = findViewById(R.id.button2id);
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {

                return Observable.just(i);
            }
        });
        i = 18;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observable.subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Toast.makeText(MoreActivity.this, "当前的i的值" + integer, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }


        });

        Toast.makeText(MoreActivity.this, "interval使用", Toast.LENGTH_SHORT).show();
        /***
         * 参数说明
         * 参数一  第一次的延迟时间
         * 参数二  间隔时间数字
         * 参数三  时间单位
         */
        Observable.interval(3, 1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("Rxjava", "开始采用subscribe连接");

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d("Rxjava", "当前数值= "+aLong );

                        Toast.makeText(MoreActivity.this, "数值" + aLong, Toast.LENGTH_LONG).show();
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
