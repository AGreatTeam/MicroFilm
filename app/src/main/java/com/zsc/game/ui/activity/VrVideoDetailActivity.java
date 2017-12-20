package com.zsc.game.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import com.google.vr.sdk.widgets.video.VrVideoView;
import com.zsc.game.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VrVideoDetailActivity extends AppCompatActivity {

    @BindView(R.id.vr_videodetail)
    VrVideoView vr_video;
    @BindView(R.id.seek_bar)
    SeekBar seek_bar;
    @BindView(R.id.tv_progress)
    TextView tv_progress;
    private String vr;
    private VideoLoadTask mVideoLoadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr_video_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        vr = intent.getStringExtra("idg");
        Log.i("yyy",vr);
        //A.进行控件的初始化
        //隐藏VR效果左下角的信息按钮显示
        vr_video.setInfoButtonEnabled(false);
        //切换VR的模式   参数:VrVideoView.DisplayMode.FULLSCREEN_STEREO:设备模式(手机横着放试试)      VrVideoView.DisplayMode..FULLSCREEN_MONO手机模式
        vr_video.setDisplayMode(VrVideoView.DisplayMode.FULLSCREEN_STEREO);
        //D.对VR视频进行事件监听
        vr_video.setEventListener(new MyEventListener() );
        mVideoLoadTask = new VideoLoadTask();
    }
    public class VideoLoadTask extends AsyncTask<String ,Void ,Void>{

        @Override
        protected Void doInBackground(String... strings) {
            //创建VrVideoView.Options对象,决定VR是普通的效果,还是立体效果
            VrVideoView.Options options = new VrVideoView.Options();
            //立体模式
            options.inputType = VrVideoView.Options.TYPE_STEREO_OVER_UNDER;
            //处理加载的视频格式
            //FORMAT_DEFAULT:默认格式(SD卡或assets)
            //FORMAT_HLS:流媒体数据格式(直播)
            options.inputFormat = VrVideoView.Options.FORMAT_DEFAULT;
            try {
                vr_video.loadVideo(Uri.parse(vr), options);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    //C.因为VR很占用内存,所以当界面进入OnPause状态,暂停VR视图显示,进入OnResume状态,继续VR视图显示,进入OnDestroy状态,杀死VR,关闭异步任务


    //当失去焦点时,回到
    @Override
    protected void onPause() {
        super.onPause();
        //暂停渲染和显示
        vr_video.pauseRendering();
    }
    //当获取焦点时,回调
    @Override
    protected void onResume() {
        super.onResume();
        //继续渲染和显示
        vr_video.resumeRendering();
    }
    //当Activity销毁时,回调
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭渲染视图,回调
        vr_video.shutdown();
        //在退出Activity时,如果异步任务没有取消,则取消
        if (mVideoLoadTask != null) {
            if (!mVideoLoadTask.isCancelled()) {
                mVideoLoadTask.cancel(true);
            }
        }
    }

    //VR运行状态监听类,自定义一个类继承VrVideoEventListener,复写里面需要的方法.
    private class MyEventListener extends VrVideoEventListener {
        //当VR视图加载成功的时候的回调,此时还未开始播放
        @Override
        public void onLoadSuccess() {
            super.onLoadSuccess();
            //获取视频的长度
            long max = vr_video.getDuration();
            //设置seekbar的进度最大值
            seek_bar.setMax((int)max);

        }

        //当VR视图加载失败的时候回调的方法
        @Override
        public void onLoadError(String errorMessage) {
            super.onLoadError(errorMessage);
            Toast.makeText(VrVideoDetailActivity.this, "播放失败", Toast.LENGTH_SHORT).show();
        }

        //当视频开始播放,每次进入下一帧的时候,回调这个方法(就是播放时,会不停的回调该方法)
        @Override
        public void onNewFrame() {
            super.onNewFrame();
            //获取当前视频的播放时间位置
            int currentPosition = (int) vr_video.getCurrentPosition();
            //设置seekBar的进度条
            seek_bar.setProgress(currentPosition);
            //显示播放的进度数字
            tv_progress.setText("播放进度:"+ String.format("%.2f",currentPosition/1000.f)+String.format("%.2f",vr_video.getDuration()/1000f));

        }

        //当视频播放结束后的回调
        @Override
        public void onCompletion() {
            super.onCompletion();
            //让视频回到0点
            vr_video.seekTo(0);
            //视频停止
            vr_video.pauseVideo();
            //让进度条也设置到0点
            seek_bar.setProgress(0);

            //播放完成后,重新设置标签,标签true代表着视频处于暂停的状态.
            isPaused = false ;
        }

        //设置一个视频播放状态的标签
        private boolean isPaused  = true;

        //重写点击视图的方法,是视频被点击时,播放或者暂停
        @Override
        public void onClick() {
            super.onClick();
            //根据标签,判断当前视频的状态,做对应的逻辑处理
            //false是不是代表视频正处于暂停状态,
            if(isPaused){
                //视频暂停
                vr_video.pauseVideo();
            }
            //true是不是代表视频正在播放的状态.
            else{
                //视频播放
                vr_video.playVideo();
            }
            //对标签进行一次操作后,取反
            isPaused =!isPaused;
        }

    }
}
