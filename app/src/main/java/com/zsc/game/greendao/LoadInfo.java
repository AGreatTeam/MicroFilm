package com.zsc.game.greendao;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/20  18:53
 */

@Entity
public class LoadInfo implements Parcelable {

    @Id(autoincrement = true)
    private Long id;
    private String url; //URL
    private int length; //长度或结束位置
    private int start; //开始位置
    private int now;//当前进度
    @Generated(hash = 2034971325)
    public LoadInfo(Long id, String url, int length, int start, int now) {
        this.id = id;
        this.url = url;
        this.length = length;
        this.start = start;
        this.now = now;
    }
    @Generated(hash = 1882968534)
    public LoadInfo() {
    }

    protected LoadInfo(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        url = in.readString();
        length = in.readInt();
        start = in.readInt();
        now = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(url);
        dest.writeInt(length);
        dest.writeInt(start);
        dest.writeInt(now);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoadInfo> CREATOR = new Creator<LoadInfo>() {
        @Override
        public LoadInfo createFromParcel(Parcel in) {
            return new LoadInfo(in);
        }

        @Override
        public LoadInfo[] newArray(int size) {
            return new LoadInfo[size];
        }
    };

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getLength() {
        return this.length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public int getStart() {
        return this.start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getNow() {
        return this.now;
    }
    public void setNow(int now) {
        this.now = now;
    }

    

}
