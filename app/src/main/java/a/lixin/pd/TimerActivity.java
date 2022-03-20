package a.lixin.pd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;

import java.util.concurrent.TimeUnit;

import a.lixin.encryption.R;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class TimerActivity extends AppCompatActivity {
    private static final String TAG = "TimerActivity";
    private Disposable mDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCount();
            }
        });
    }

    /**
     * 开启计时器
     */
    public void startCount(){
        Observable.timer(10 * 1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        LogUtils.dTag(TAG,  "-->onSubscribe");
                        mDisposable = disposable;
                    }

                    @Override
                    public void onNext(Long number) {
                        LogUtils.dTag(TAG,  "-->onSubscribe=" + number);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        LogUtils.dTag(TAG, "-->onError-->" + throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.dTag(TAG, "-->onComplete");
                    }
                });
    }
}