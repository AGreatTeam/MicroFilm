package com.zsc.game.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.vr.sdk.widgets.common.VrWidgetView;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.zsc.game.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 1 on 2017/12/18.
 */

public class VRpickFragment extends Fragment{

    private VrPanoramaView viewById;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.vrpicturefragment,container,false);
        viewById = view.findViewById(R.id.vrpanorview);
        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //隐藏掉VR效果左下角的信息按钮显示
        viewById.setInfoButtonEnabled(false);
        //隐藏掉VR效果右下角全屏显示按钮
        viewById.setFullscreenButtonEnabled(false);
        //切换VR的模式   参数: VrWidgetView.DisplayMode.FULLSCREEN_STEREO设备模式(手机横着放试试)   VrWidgetView.DisplayMode.FULLSCREEN_MONO手机模式
        viewById.setDisplayMode(VrWidgetView.DisplayMode.FULLSCREEN_STEREO);

        //D.设置对VR运行状态的监听,如果VR运行出现错误,可以及时处了.
        viewById.setEventListener(new MyVREventListener());
        //开启执行全景
        MyAsyTaskPic myAsyTaskPic = new MyAsyTaskPic();
        myAsyTaskPic.execute();
    }



    private class MyAsyTaskPic extends AsyncTask<Void ,Void,Bitmap>{


        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                InputStream open = getActivity().getAssets().open("andes.jpg");
                Bitmap bitmap = BitmapFactory.decodeStream(open);
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //创建bVrPanoramaView.Options,去决定显示VR是普通效果,还是立体效果
            VrPanoramaView.Options options = new VrPanoramaView.Options();
            //TYPE_STEREO_OVER_UNDER立体效果:图片的上半部分放在左眼显示,下半部分放在右眼显示     TYPE_MONO:普通效果
            options.inputType=VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;
            //使用VR控件对象,显示效果  参数:1.Bitmap对象      2.VrPanoramaView.Options对象,决定显示的效果
            viewById.loadImageFromBitmap(bitmap, options);
            super.onPostExecute(bitmap);
        }
    }


    //VR运行状态监听类,自定义一个类继承VrPanoramaEventListener,复写里面的两个方法
    private class MyVREventListener extends VrPanoramaEventListener {
        //当VR视图加载成功的时候回调
        @Override
        public void onLoadSuccess() {
            super.onLoadSuccess();
            Toast.makeText(getActivity(), "加载成功,么么哒", Toast.LENGTH_SHORT).show();

        }
        //当VR视图加载失败的时候回调
        @Override
        public void onLoadError(String errorMessage) {
            super.onLoadError(errorMessage);
            Toast.makeText(getActivity(), "加载失败,不好意思,因为易大师太帅影响", Toast.LENGTH_SHORT).show();
        }
    }
}
