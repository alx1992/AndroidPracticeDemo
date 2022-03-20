package a.lixin.pd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import a.lixin.encryption.R;

public class IntentServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);

        findViewById(R.id.start).setOnClickListener(v->{
            //同一个Service只会启动一个工作线程
            //所有的耗时任务都将在onHandleIntent中处理
            Intent i=new Intent(this,MyIntentService.class);
            Bundle bundle=new Bundle();
            bundle.putString("taskName","task1");
            i.putExtras(bundle);
            startService(i);

            Intent i2=new Intent(this,MyIntentService.class);
            Bundle bundle2=new Bundle();
            bundle2.putString("taskName","task2");
            i2.putExtras(bundle2);
            startService(i2);

            startService(i);
        });
    }
}