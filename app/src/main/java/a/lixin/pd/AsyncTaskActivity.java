package a.lixin.pd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

import a.lixin.encryption.R;
/** 
 * @author lixin
 * @version 1.0
 */
public class AsyncTaskActivity extends AppCompatActivity {
    private static final String TAG = "ThreadActivity";
    private ProgressBar mProgressBar;
    private TextView  mTextView;
    private MyTask mMyTask;
    private Button execute;
    private Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);
        //
        initView();
        
    }

    private void initView() {

        execute =  findViewById(R.id.execute);
        execute.setOnClickListener(v -> {
            //注意每次需new一个实例,新建的任务只能执行一次,否则会出现异常
            mMyTask = new MyTask();
            mMyTask.execute("https://www.baidu.com");
            mProgressBar.setVisibility(View.VISIBLE);
            execute.setEnabled(false);
            cancel.setEnabled(true);
        });
        cancel =  findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> {
            //取消一个正在执行的任务,onCancelled方法将会被调用
            mMyTask.cancel(true);
            mProgressBar.setVisibility(View.GONE);
        });
        mProgressBar = findViewById(R.id.progressBar);
        mTextView = findViewById(R.id.textView);
        
}


    private class MyTask extends AsyncTask<String, Integer, String> {
        //onPreExecute方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            LogUtils.dTag(TAG, "onPreExecute() called");
            mTextView.setText("loading...");
        }

        //doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected String doInBackground(String... params) {
            LogUtils.dTag(TAG, "doInBackground(Params... params) called");
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "doInBackground is done ";
        }

        //onProgressUpdate方法用于更新进度信息
        @Override
        protected void onProgressUpdate(Integer... progresses) {
            LogUtils.dTag(TAG, "onProgressUpdate(Progress... progresses) called");
            mProgressBar.setProgress(progresses[0]);
            mTextView.setText("loading..." + progresses[0] + "%");
        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(String result) {
            LogUtils.dTag(TAG, "onPostExecute(Result result) called");
            mProgressBar.setVisibility(View.GONE);
            mTextView.setText(result);
            execute.setEnabled(true);
            cancel.setEnabled(false);
        }

        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled() {
            LogUtils.dTag(TAG, "onCancelled() called");
            mTextView.setText("cancelled");
            mProgressBar.setVisibility(View.GONE);
            mProgressBar.setProgress(0);
            mProgressBar.setVisibility(View.GONE);
            execute.setEnabled(true);
            cancel.setEnabled(false);
        }
    }

}