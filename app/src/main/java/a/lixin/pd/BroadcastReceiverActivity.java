package a.lixin.pd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import a.lixin.encryption.R;

/**
 * @author LiXin
 */
public class BroadcastReceiverActivity extends AppCompatActivity {
    private static final String TAG = "BroadcastReceiverActivity";
    MyReceiver myReceiver ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);
        Button button = findViewById(R.id.btn_send);
        EventBus.getDefault().register(this);
        registerReceiver();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //对应BroadcastReceiver中intentFilter的action
                intent.setAction("test");
                //发送广播
                sendBroadcast(intent);
            }
        });

    }

    private void registerReceiver(){
        // 1. 实例化BroadcastReceiver子类 &  IntentFilter
        MyReceiver myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();

        // 2. 设置接收广播的类型
        intentFilter.addAction("test");

        // 3. 动态注册：调用Context的registerReceiver（）方法
        registerReceiver(myReceiver, intentFilter);
    }

    /**
     * 事件响应方法
     * 接收消息
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMsg event) {
        try {
            test1(event.getMsg());
            Thread.sleep(1000);
            test2(event.getMsg());
            test3(event.getMsg_what());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void test3(int event) {
        LogUtils.dTag(TAG,"test3 method is execute event " + event);
    }

    private void unRegisterReceiver(){
        if (myReceiver != null) {
            unregisterReceiver(myReceiver);
        }
    }

    @Override
    protected void onDestroy() {
        unRegisterReceiver();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * broadcastReceiver is called
     */
    public void test1(String event){
        LogUtils.dTag(TAG,"test1 method is execute event " + event);
    }
    public void test2(String event){
        LogUtils.dTag(TAG,"test2 method is execute event " + event);
    }
}