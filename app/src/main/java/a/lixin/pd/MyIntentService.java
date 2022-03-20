package a.lixin.pd;

import android.app.IntentService;
import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;

public class MyIntentService extends IntentService {

    private final String TAG="myIntentService";

    public MyIntentService() {
        super("myIntentService");
    }

    @Override
    protected void onHandleIntent( Intent intent) {
        //根据Intent的不同进行不同的事务处理
        String taskName = intent.getExtras().getString("taskName");
        switch (taskName) {
            case "task1":
               LogUtils.dTag(TAG, "do task1");
                break;
            case "task2":
               LogUtils.dTag(TAG, "do task2");
                break;
            default:
                break;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
       LogUtils.dTag(TAG,"OnCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       LogUtils.dTag(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       LogUtils.dTag(TAG,"onDestroy");
    }
}