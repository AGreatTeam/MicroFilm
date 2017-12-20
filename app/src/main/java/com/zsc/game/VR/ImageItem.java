package com.zsc.game.VR;

import java.io.Serializable;

/**
 * Created by 1 on 2017/12/20.
 */

public class ImageItem implements Serializable {

    private String name;
    private String picAddress;
    private String musicddress;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicAddress() {
        return picAddress;
    }

    public void setPicAddress(String picAddress) {
        this.picAddress = picAddress;
    }

    public String getMusicddress() {
        return musicddress;
    }

    public void setMusicddress(String musicddress) {
        this.musicddress = musicddress;
    }

    public ImageItem(String name, String picAddress, String musicddress) {
        this.name = name;
        this.picAddress = picAddress;
        this.musicddress = musicddress;
    }

    @Override
    public String toString() {
        return "ImageItem{" +
                "name='" + name + '\'' +
                ", picAddress='" + picAddress + '\'' +
                ", musicddress='" + musicddress + '\'' +
                '}';
    }
}
