package a.lixin.pd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import a.lixin.encryption.R;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxjavaTimerActivity extends AppCompatActivity {
    private static final String TAG = "RxjavaTimerActivity";
    private  long countTime = 0 ;
    private Timer timer = new Timer();
    private boolean isStop = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_timer);

        //rxjava 定时
        Button button = findViewById(R.id.btn_press);
        button.setOnClickListener(v -> startTime());
        Button close = findViewById(R.id.btn_up);
        close.setOnClickListener(v -> closeTimer(true));

        //timer
        Button t = findViewById(R.id.btn_timer);
        t.setOnClickListener(v ->{
            timer.schedule(new MyTimerTask(new TimerListener() {
                @Override
                public void doSomething(Long value) {
                    LogUtils.dTag(TAG,"current thread name :" + Thread.currentThread().getName() + ",value:" + value);
                }
            }),0,1000);
        });
        Button c = findViewById(R.id.btn_close_timer);
        c.setOnClickListener(v -> {
            stopTimer();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.dTag(TAG,"onPause");
        closeTimer(true);
        stopTimer();
    }

    /**
     * 停止定时器
     */
    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * timer
     */
    public  class MyTimerTask extends TimerTask {
        private TimerListener timerListener;
        public MyTimerTask(TimerListener tl){
            timerListener = tl;
        }
        public void run() {
            LogUtils.dTag(TAG,"time");
            countTime ++;
            timerListener.doSomething(countTime);
            if (countTime == 10) {
                LogUtils.dTag(TAG,"定时器停止了");
                stopTimer();
            }
        }
    }

    /**
     * 监听
     */
    public interface TimerListener{
        void doSomething(Long value);
    }


    private Disposable mDisposable;
    /**
     * 启动定时器
     */
    public void startTime() {
        LogUtils.dTag(TAG,"startTime");
        int count_time = 10; //总时间
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count_time+1)//设置总共发送的次数
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        //aLong从0开始
                        long temp = count_time-aLong;
                        LogUtils.dTag(TAG,"apply current thread name："+Thread.currentThread().getName() + "，value：" + temp);
                        if (isStop){
                            return null;
                        }
                        else {
                            return count_time-aLong;
                        }

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.dTag(TAG,"onSubscribe");
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Long value) {
                        LogUtils.dTag(TAG,"Observer current thread name："+Thread.currentThread().getName() + "，value：" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.dTag(TAG,"onError");
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.dTag(TAG,"onComplete");
                        closeTimer(true);
                    }
                });
    }

    /**
     * 关闭定时器
     */
    public void closeTimer(boolean bStop){
        LogUtils.dTag(TAG,"closeTimer");
        isStop = bStop;
        if (mDisposable != null) {
            LogUtils.dTag(TAG,"mDisposable is not null");
            mDisposable.dispose();
        }
    }


}