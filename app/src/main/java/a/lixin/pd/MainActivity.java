package a.lixin.pd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import com.blankj.utilcode.util.LogUtils;
import java.io.File;

import a.lixin.encryption.R;

/**
 *
 * @author LiXin
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    VideoView play_vv;

    private static Context sContext;
    static TextView mTextView;
    MyHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initView();
        sContext = this;

        mTextView = new TextView(sContext);
        mHandler = new MyHandler();
        mHandler.sendEmptyMessage(1);
    }

    public static class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            LogUtils.dTag(TAG,"msg=" + msg.what);
            Context context;
            MainActivity.mTextView.setText("msg.obj");
        }
    }
    private void initView() {
        Button button = findViewById(R.id.button);
        play_vv = findViewById(R.id.vv_play);
        playMp4(button);
    }
    /**
     * 播放mp4
     * @param button
     */
    private void playMp4(Button button) {
        PermissionUtils.checkAndRequestMorePermissions(
                this,
                permissions,
                10000,
                () ->
                        button.setOnClickListener(v -> {
                            File file=new File(Environment.getExternalStorageDirectory() + "/abc.mp4");
                            startPlayVideo(file.getAbsolutePath());
                }));
    }




    private void startPlayVideo(String path){
        play_vv.setVideoPath(path);
        // 为VideoView指定MediaController
        play_vv.setMediaController(new MediaController(this));
        // 为MediaController指定控制的VideoView
        /**
         * 视频准备完成时回调
         */
        play_vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                LogUtils.dTag(TAG,"--------------视频准备完毕,可以进行播放.......");
            }
        });
        play_vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                LogUtils.dTag(TAG,"--------------视频进行播放.......");

            }
        });
        play_vv.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                LogUtils.dTag(TAG,"--------------视频进行播放错误.......");
                return false;
            }
        });
        play_vv.start();
    }

}