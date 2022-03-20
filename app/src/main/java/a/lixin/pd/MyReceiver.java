package a.lixin.pd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "BroadcastReceiver";



    @Override
    public void onReceive(Context context, Intent intent) {
        //end
        LogUtils.dTag(TAG,"onReceiver begin");
        //eventbus
        EventMsg msg = new EventMsg();
        msg.setMsg_what(123);
        msg.setMsg("test");
        //EventBus.getDefault().post(msg);
        EventBus.getDefault().post(msg);
        //end
        LogUtils.dTag(TAG,"onReceiver end");
    }
}