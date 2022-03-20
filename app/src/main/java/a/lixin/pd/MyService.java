package a.lixin.pd;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
/**
 * @author alx1992
 */
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyService extends Service {
    private static final String TAG = "MyService";
    @Override
    public void onCreate() {
        super.onCreate();
        startTime();
        LogUtils.dTag(TAG,"onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.dTag(TAG,"onStartCommand");
        startTime();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closeTimer();
        LogUtils.dTag(TAG,"onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
                        LogUtils.dTag(TAG,"apply current thread name："+Thread.currentThread().getName() + "，value：" + aLong);
                        return count_time-aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Long value) {
                        //Log.d("Timer",""+value);
                        LogUtils.dTag(TAG,"Observer current thread name："+Thread.currentThread().getName() + "，value：" + value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        // TODO:2017/12/1
                        closeTimer();
                    }
                });
    }

    /**
     * 关闭定时器
     */
    public void closeTimer(){
        LogUtils.dTag(TAG,"closeTimer");
        if (mDisposable != null) {
            LogUtils.dTag(TAG,"mDisposable is not null");
            mDisposable.dispose();
        }
    }
}