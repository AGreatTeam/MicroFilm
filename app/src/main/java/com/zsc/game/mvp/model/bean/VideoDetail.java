package com.zsc.game.mvp.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 苏照亮 2017/12/18.
 */

public class VideoDetail implements Parcelable {
    public String title;
    public String pic;
    public String dataId;
    public String score;
    public String airTime;
    public String moreURL;
    public String loadType;

    public VideoDetail() {

    }

    public VideoDetail(String title, String pic, String dataId, String score, String airTime, String moreURL, String loadType) {
        this.title = title;
        this.pic = pic;
        this.dataId = dataId;
        this.score = score;
        this.airTime = airTime;
        this.moreURL = moreURL;
        this.loadType = loadType;
    }

    public VideoDetail(Parcel in) {
        title = in.readString();
        pic = in.readString();
        dataId = in.readString();
        score = in.readString();
        airTime = in.readString();
        moreURL = in.readString();
        loadType = in.readString();
    }

    public static final Creator<VideoDetail> CREATOR = new Creator<VideoDetail>() {
        @Override
        public VideoDetail createFromParcel(Parcel in) {
            return new VideoDetail(in);
        }

        @Override
        public VideoDetail[] newArray(int size) {
            return new VideoDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(pic);
        dest.writeString(dataId);
        dest.writeString(score);
        dest.writeString(airTime);
        dest.writeString(moreURL);
        dest.writeString(loadType);
    }
}
