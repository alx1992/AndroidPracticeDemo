package a.lixin.pd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import a.lixin.encryption.R;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * RxJavaActivity
 * @author lixin
 */
public class RxJavaActivity extends AppCompatActivity {
    private static final String TAG = "RxJavaActivity";
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRxJava(1);
                callRxJava(2);
                //callRxJava(3);
                //callRxJava(4);
                //callRxJava(5);
            }
        });
    }



    public void callRxJava(int i){
                        Observable.create((ObservableOnSubscribe<String>) emitter -> {
                            LogUtils.dTag(TAG,"threadName=" + Thread.currentThread().getName() +" is " + i);
                            emitter.onNext("test");
                        })
                        .subscribeOn(Schedulers.single())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull String s) {
                                LogUtils.dTag(TAG,"threadName=" + Thread.currentThread().getName() +" is " + i + " value=" + s);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {


                            }
                        });
    }
}