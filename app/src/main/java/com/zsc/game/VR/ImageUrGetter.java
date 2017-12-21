package com.zsc.game.VR;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2017/12/20.
 */

public class ImageUrGetter {
    private static final int  IO_BUFFER_SIZE = 2*1024;

    public static List<ImageItem> getImageItems() {
        List<ImageItem> items = new ArrayList<ImageItem>();
        items.add(new ImageItem("滕王阁", "http://media.qicdn.detu.com/pano177051472357986990056825/thumb/500_500/panofile.jpg", "http://media.qicdn.detu.com/@/13363707-8857-C248-3CE1-64F2F24291636/source/145049/o_1arbdk2apj37df16up16um196j7.mp3"));
        items.add(new ImageItem("巴山大峡谷-云海日出", "http://media.qicdn.detu.com/@/17596710-5661-0192-EDC8-81F89376806/source/142048/o_1aqd3brm71svb11gqh5la5bjj17.jpg", "http://media.qicdn.detu.com/@/17596710-5661-0192-EDC8-81F89376806/source/128321/o_1amb55jqq13ma8po16aogvdrjkc.mp3"));
        items.add(new ImageItem("厦大", "http://media.qicdn.detu.com/pano781791479224712452691293/thumb/500_500/panofile.jpg ", null));
        items.add(new ImageItem("西南大学经济管理学院", "http://media.qicdn.detu.com/pano573341478189386216286405/thumb/500_500/panofile.jpg ", null));
        items.add(new ImageItem("辽宁工业大学", "http://media.qicdn.detu.com/pano476831467201488386232805/thumb/500_500/panofile.jpg ", null));
        items.add(new ImageItem("西安海棠职业学院", "http://media.qicdn.detu.com/pano532201469338026348840893/thumb/500_500/panofile.jpg ", "http://media.qicdn.detu.com/@/18192570-5756-0D36-9533-2416F77090543/source/135547/o_1aodn4afsqclli11jm5tr22kg7.mp3"));
        return items;
    }


    public static Bitmap returnBitmap(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;

        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }
}
